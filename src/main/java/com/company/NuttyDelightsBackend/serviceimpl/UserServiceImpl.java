package com.company.NuttyDelightsBackend.serviceimpl;

import com.company.NuttyDelightsBackend.config.JwtProvider;
import com.company.NuttyDelightsBackend.dto.request.*;
import com.company.NuttyDelightsBackend.dto.response.AuthResponse;
import com.company.NuttyDelightsBackend.dto.response.OtpGenerationResponse;
import com.company.NuttyDelightsBackend.dto.response.OtpVerificationResponse;
import com.company.NuttyDelightsBackend.exception.ProductException;
import com.company.NuttyDelightsBackend.exception.UserException;
import com.company.NuttyDelightsBackend.model.Cart;
import com.company.NuttyDelightsBackend.model.ConfirmationToken;
import com.company.NuttyDelightsBackend.model.User;
import com.company.NuttyDelightsBackend.repository.CartRepository;
import com.company.NuttyDelightsBackend.repository.ConfirmationTokenRepository;
import com.company.NuttyDelightsBackend.repository.UserRepository;
import com.company.NuttyDelightsBackend.service.CartService;
import com.company.NuttyDelightsBackend.service.EmailService;
import com.company.NuttyDelightsBackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static com.company.NuttyDelightsBackend.domain.UserRole.ROLE_USER;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CustomUserServiceImpl customUserService;

    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private EmailService emailService;

    @Autowired
    private  CartService cartService;

    @Override
    public User findUserById(String userId) throws UserException {
        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent())
            return user.get();

        throw  new UserException("User Not Find with id:" + userId);

    }

    @Override
    public User findUserProfileByJwtToken(String jwtToken) throws UserException {
        String email = jwtProvider.getEmailFromToken(jwtToken);
        User user = userRepository.findByEmail(email);
        if(user == null)
            throw new UserException("User not found with email:" + email);

        return user;
    }

    @Override
    public AuthResponse createUserHandler(UserRegisteration userRegisteration) throws UserException, ProductException {
        User user = userRegisteration.getUser();
        String email = user.getEmail();
        String password = user.getPassword();
        String firstName = user.getFirstName();
        String lastName = user.getLastName();


        User isUserExist = userRepository.findByEmail(email);
        if(isUserExist != null)
            throw new UserException("Account Already Exist");
        User isUserMobExist = userRepository.findByMobileNumber(user.getMobileNumber());
        if(isUserExist != null)
            throw new UserException("Mobile number associated with another account");

        User createdUser = new User();
        createdUser.setEmail(email);
        createdUser.setPassword(passwordEncoder.encode(password));
        createdUser.setFirstName(firstName);
        if(lastName.equals("null"));

        else{
            createdUser.setLastName(lastName);
        }
        createdUser.setMobileNumber(user.getMobileNumber());
        createdUser.setCreatedAt(LocalDateTime.now());
        createdUser.setRole(ROLE_USER);
        createdUser.setIsEnabled(false);

        User savedUser = userRepository.save(createdUser);
        Authentication authentication = new UsernamePasswordAuthenticationToken(savedUser.getEmail() , password);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtProvider.generateToken(authentication);
        AuthResponse authResponse = new AuthResponse(token , "User Registered Successfully");
        ConfirmationToken confirmationToken = new ConfirmationToken(savedUser.getUserId());

        confirmationTokenRepository.save(confirmationToken);
        Cart newCart = cartService.createCart(savedUser);
        if(!userRegisteration.getLocalCartItems().isEmpty()) {
            for (AddItemRequest addItemRequest : userRegisteration.getLocalCartItems()) {
                cartService.addCartItem(savedUser.getUserId(), addItemRequest);
            }
        }

//        cartRepository.save(newCart);





        System.out.println("Confirmation Token: " + confirmationToken.getConfirmationToken());
        return authResponse;
    }


    @Override


    public OtpGenerationResponse generateEmailCode(OtpGenerationRequest otpGenerationRequest, String jwt) throws UserException {

        System.out.println("Otp request" + otpGenerationRequest);
//        String jwt = otpRequest.getJwt();
        String emailProvided = otpGenerationRequest.getEmail();
        User user = findUserProfileByJwtToken(jwt);
        System.out.println("User , " + user);
        if(user == null)
            return new OtpGenerationResponse(null , true , "user not found");

        ConfirmationToken isExistingToken = confirmationTokenRepository.findByUserId(user.getUserId());
        if( isExistingToken != null){
            confirmationTokenRepository.deleteByUserId((isExistingToken.getUserId()));
        }
        ConfirmationToken confirmationToken = new ConfirmationToken(user.getUserId());

        confirmationTokenRepository.save(confirmationToken);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        
        mailMessage.setFrom("shop@nuttydelights.co.in");
        mailMessage.setTo(emailProvided);
        mailMessage.setSubject("Email Verification");
        mailMessage.setText("Your One Time Password (OTP) to verify your account is "+confirmationToken.getConfirmationToken() + ".\nPlease use it within 5 minutes and do not share it with anyone.");
        emailService.sendEmail(mailMessage);

        System.out.println("Confirmation Token: " + confirmationToken.getConfirmationToken());

        OtpGenerationResponse otpGenerationResponse = new OtpGenerationResponse();
        otpGenerationResponse.setOtp(confirmationToken.getConfirmationToken());
        otpGenerationResponse.setError(false);
        otpGenerationResponse.setMessage("Otp sent Successfully");
        return otpGenerationResponse;
    }

    @Override
    public OtpVerificationResponse verifyEmailCode(OtpVerificationRequest otpVerificationRequest, String jwt) throws UserException {
        User user = userRepository.findByEmail(otpVerificationRequest.getEmail());

        ConfirmationToken confirmationToken =  confirmationTokenRepository.findByUserId(user.getUserId());

        long duration = new Date().getTime() - confirmationToken.getCreatedData().getTime();
        long diffInSeconds = TimeUnit.MILLISECONDS.toSeconds(duration);
        long diffInMinutes = TimeUnit.MILLISECONDS.toMinutes(duration);
        System.out.println("duration between verification and generation of otp:" + diffInSeconds + " in mins:" + diffInMinutes);

        if(diffInSeconds > 300)
            return new OtpVerificationResponse("Code Expired, please Regenerate the code.", false);
        if(confirmationToken.getConfirmationToken().equals(otpVerificationRequest.getOtp())){
            user.setIsEnabled(true);
            userRepository.save(user);
            return new OtpVerificationResponse("Email successfully verified" , true);
        }

        return new OtpVerificationResponse("Incorrect code, please try again.", false);
    }

    @Override
    public AuthResponse loginUserHandler(LoginRequest loginRequest) throws ProductException {
           String userName = loginRequest.getEmail();
           String password = loginRequest.getPassword();
           List<AddItemRequest> list = loginRequest.getLocalCartItem();
           System.out.println(userName + password);
           Authentication authentication  = authenticate(userName , password);
           System.out.println("authentication:"+ authentication);
           SecurityContextHolder.getContext().setAuthentication(authentication);
           String token = jwtProvider.generateToken(authentication);
           if(!list.isEmpty()) {
               User user = userRepository.findByEmail(userName);
               Cart cart = cartService.findUserCart(user.getUserId());
               for(AddItemRequest addItemRequest : list )
               {
                   cartService.addCartItem(user.getUserId() , addItemRequest);
               }

           }
           AuthResponse authResponse = new AuthResponse(token , "User Logged in successfully");
           return authResponse;

    }

    @Override
    public User getUserProfileHandler(String jwt) throws UserException {
        User user = findUserProfileByJwtToken(jwt);
        if(user == null)
            throw new UserException("User details not found");
        return user;
    }



    private  Authentication authenticate (String username , String password){

        UserDetails userDetails = customUserService.loadUserByUsername(username);

        if(userDetails == null)
            try {
                throw  new UserException("Invalid Username");
            } catch (UserException e) {
                throw new RuntimeException(e);
            }
        if(!passwordEncoder.matches(password , userDetails.getPassword())){
            System.out.println("Invalid password");
            return  null;
        }

//        User user = userRepository.findByEmail(username);
//        System.out.println("Is enabled:" + user.getIsEnabled());
//        if(!user.getIsEnabled())
//            try {
//                throw  new UserException("Account Not Verified");
//            } catch (UserException e) {
//                throw new RuntimeException(e);
//            }

        System.out.println("User details" + userDetails);

        return  new UsernamePasswordAuthenticationToken(userDetails , null , userDetails.getAuthorities());
    }
}

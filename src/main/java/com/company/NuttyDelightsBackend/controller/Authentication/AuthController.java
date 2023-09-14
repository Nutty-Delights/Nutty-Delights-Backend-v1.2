package com.company.NuttyDelightsBackend.controller.Authentication;

import com.company.NuttyDelightsBackend.dto.request.LoginRequest;
import com.company.NuttyDelightsBackend.dto.request.OtpGenerationRequest;
import com.company.NuttyDelightsBackend.dto.request.OtpVerificationRequest;
import com.company.NuttyDelightsBackend.dto.request.UserRegisteration;
import com.company.NuttyDelightsBackend.dto.response.AuthResponse;
import com.company.NuttyDelightsBackend.dto.response.OtpGenerationResponse;
import com.company.NuttyDelightsBackend.dto.response.OtpVerificationResponse;
import com.company.NuttyDelightsBackend.exception.ProductException;
import com.company.NuttyDelightsBackend.exception.UserException;
import com.company.NuttyDelightsBackend.model.User;
import com.company.NuttyDelightsBackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//@CrossOrigin(origins =  "*")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody UserRegisteration userRegisteration) throws UserException, ProductException {

        return  new ResponseEntity<AuthResponse>(userService.createUserHandler(userRegisteration) ,HttpStatus.CREATED );

    }
    @PostMapping("/generateCode")
    public ResponseEntity<OtpGenerationResponse> generateEmailCode(@RequestHeader("Authorization") String jwt , @RequestBody OtpGenerationRequest otpGenerationRequest) throws UserException {

        System.out.println(otpGenerationRequest.getEmail() + " " + jwt);
        return  new ResponseEntity<OtpGenerationResponse>( userService.generateEmailCode(otpGenerationRequest, jwt) ,HttpStatus.CREATED );

    }
    @PostMapping("/verifyCode")
    public ResponseEntity<OtpVerificationResponse> verifyEmailCode(@RequestHeader("Authorization") String jwt , @RequestBody OtpVerificationRequest otpVerificationRequest) throws UserException {

        System.out.println(otpVerificationRequest.getEmail() + " " + jwt);
        return  new ResponseEntity<OtpVerificationResponse>( userService.verifyEmailCode(otpVerificationRequest, jwt) ,HttpStatus.CREATED );

    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginUserHandler(  @RequestBody LoginRequest loginRequest) throws ProductException {

        return new ResponseEntity<AuthResponse>(userService.loginUserHandler(loginRequest) , HttpStatus.OK);

    }



//    @RequestMapping(value="/confirm-account", method= {RequestMethod.GET, RequestMethod.POST})
//    @GetMapping("/confirm-account")
//    public ResponseEntity<?> confirmUserAccount(@RequestParam("token")String confirmationToken) {
//        return userService.confirmEmail(confirmationToken);
//    }
}

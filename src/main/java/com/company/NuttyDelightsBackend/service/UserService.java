package com.company.NuttyDelightsBackend.service;

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

public interface UserService {
    public User findUserById (String userId) throws UserException;
    public User findUserProfileByJwtToken (String jwtToken) throws UserException;
    public AuthResponse createUserHandler(UserRegisteration userRegisteration) throws UserException, ProductException;
    public AuthResponse loginUserHandler(LoginRequest loginRequest) throws ProductException;
    public User getUserProfileHandler(String jwt) throws UserException;

//    ResponseEntity<?> confirmEmail(String confirmationToken);
    public OtpGenerationResponse generateEmailCode(OtpGenerationRequest otpGenerationRequest, String jwt) throws UserException;
    public OtpVerificationResponse verifyEmailCode(OtpVerificationRequest otpVerificationRequest, String jwt) throws UserException;



}

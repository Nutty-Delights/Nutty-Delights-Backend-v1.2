package com.company.NuttyDelightsBackend.controller.User;

import com.company.NuttyDelightsBackend.exception.UserException;
import com.company.NuttyDelightsBackend.model.User;
import com.company.NuttyDelightsBackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;
    @GetMapping("/profile")
    public ResponseEntity<User> getUserProfileHandler(@RequestHeader("Authorization") String jwt) throws UserException {


        return new ResponseEntity<User>(userService.getUserProfileHandler(jwt), HttpStatus.OK);
    }
}

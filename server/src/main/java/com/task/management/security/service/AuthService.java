package com.task.management.security.service;

import com.task.management.dto.LoginRequestDTO;
import com.task.management.dto.ResponseStructure;
import com.task.management.dto.SignupRequestDTO;
import com.task.management.exceptions.UserEmailAlreadyExistsException;
import com.task.management.exceptions.UserNameAlreadyExistsException;
import com.task.management.model.User;
import com.task.management.repository.UserRepository;
import com.task.management.security.jwt.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
    private final CustomUserDetailService customUserDetailService;
    private final JwtUtils jwtUtils;

    @Autowired
    public AuthService(UserRepository userRepository, AuthenticationManager authenticationManager, CustomUserDetailService customUserDetailService, JwtUtils jwtUtils){
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.customUserDetailService = customUserDetailService;
        this.jwtUtils = jwtUtils;
    }


    public ResponseStructure<Object> checkUserName(String username){
        Map<String, Object> object = new HashMap<>();
        object.put("canCreateUser", checkUserNameExists(username));
        return new ResponseStructure<>(HttpStatus.OK.value(), "successful", object);
    }

    public ResponseStructure<Object> login(LoginRequestDTO login){

      return new ResponseStructure<>();
    };
    public ResponseStructure<Object> signUp(SignupRequestDTO signUpObj) throws UserNameAlreadyExistsException, UserEmailAlreadyExistsException{
        if (!checkUserNameExists(signUpObj.getUserName())){
            return new ResponseStructure<>(HttpStatus.CONFLICT.value(), "User already Exists with given UserName!",null);
        }
        if (checkEmailExists(signUpObj.getUserEmail())){
            return new ResponseStructure<>(HttpStatus.CONFLICT.value(), "User already exists with given email address", null);
        }

        User user = encodePassword(signUpObj);
        try {
            userRepository.save(user);
            return new ResponseStructure<>(HttpStatus.CREATED.value(), "User created successfully!", null);
        }
        catch (UserEmailAlreadyExistsException emailException){
            return new ResponseStructure<>(HttpStatus.CONFLICT.value(), "User already exists with given email address.", null);
        }
    }

    public ResponseStructure<Object> authenticate(LoginRequestDTO input, HttpServletResponse response) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getUsername(),
                        input.getPassword()
                )
        );

        Optional<User> optionalUser = userRepository.findByUserName(input.getUsername());
        User authenticatedUser = optionalUser.orElseThrow(()-> new UsernameNotFoundException("user not found"));
        UserDetails userDetails = customUserDetailService.loadUserByUsername(authenticatedUser.getUserName());
        long expireTimeOut = input.isRememberMe() ? 7*24*3600000 : 3600000;
        String jwtToken = jwtUtils.generateToken(userDetails, expireTimeOut);
        ResponseStructure<Object> res = new ResponseStructure<>(HttpStatus.OK.value(), "Logged in successfully!", null);
        ResponseCookie cookie = ResponseCookie.from("bearer", jwtToken)
                .httpOnly(false)
                .secure(true) // Set to true in production for HTTPS
                .path("/")
                .maxAge(expireTimeOut) // 7 days
                .sameSite("Strict") // Adjust SameSite attribute as needed
                .build();

        response.addHeader("Set-Cookie", cookie.toString());
        return res;
    }

    public ResponseStructure<Object> logout(HttpServletResponse response){
        ResponseStructure<Object> res = new ResponseStructure<>(HttpStatus.OK.value(), "Logged in successfully!", null);
        ResponseCookie cookie = ResponseCookie.from("bearer", "")
                .httpOnly(false)
                .secure(true) // Set to true in production for HTTPS
                .path("/")
                .maxAge(0) // 7 days
                .sameSite("Strict") // Adjust SameSite attribute as needed
                .build();
        response.addHeader("Set-Cookie", cookie.toString());
        return res;
    }

    private User encodePassword(SignupRequestDTO signupDTO){
        User user = new User();
        user.setUserName(signupDTO.getUserName());
        user.setFirstName(signupDTO.getFirstName());
        user.setLastName(signupDTO.getLastName());
        user.setUserEmail(signupDTO.getUserEmail());
        user.setPassword(passwordEncoder.encode(signupDTO.getPassword()));
        return user;
    }

    private boolean checkUserNameExists(String username){
        Optional<User> user = userRepository.findByUserName(username);
        return user.isEmpty();
    }

    private boolean checkEmailExists(String userEmail){
        return userRepository.existsByUserEmail(userEmail);
    }
}

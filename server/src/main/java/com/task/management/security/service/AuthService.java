package com.task.management.security.service;

import com.task.management.dto.LoginRequestDTO;
import com.task.management.dto.SignupRequestDTO;
import com.task.management.shared.constant.AppConstant;
import com.task.management.shared.dto.ApiResponse;
import com.task.management.shared.exceptions.UserEmailAlreadyExistsException;
import com.task.management.shared.exceptions.UserNameAlreadyExistsException;
import com.task.management.users.model.User;
import com.task.management.users.repository.UserRepository;
import com.task.management.security.jwt.JwtUtils;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
    private final CustomUserDetailService customUserDetailService;
    private final JwtUtils jwtUtils;

    @Autowired
    public AuthService(UserRepository userRepository, AuthenticationManager authenticationManager, CustomUserDetailService customUserDetailService, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.customUserDetailService = customUserDetailService;
        this.jwtUtils = jwtUtils;
    }

    /**
     * to check if user exist
     *
     * @param username as string
     * @return ApiResponse
     */
    public ApiResponse<Object> checkUserName(String username) {
        Map<String, Object> object = new HashMap<>();
        object.put("canCreateUser", checkUserNameExists(username));
        return new ApiResponse<>(HttpStatus.OK.value(), "successful", object);
    }

    /**
     * to create new user for signup
     *
     * @param signUpObj as SignupRequestDTO
     * @return ApiResponse
     * @throws UserNameAlreadyExistsException  exception
     * @throws UserEmailAlreadyExistsException exception
     */
    public ApiResponse<Object> signUp(SignupRequestDTO signUpObj, HttpServletResponse response) throws UserNameAlreadyExistsException, UserEmailAlreadyExistsException {
        if (!checkUserNameExists(signUpObj.getUserName())) {
            return new ApiResponse<>(HttpStatus.CONFLICT.value(), "User already Exists with given UserName!", null);
        }
        if (checkEmailExists(signUpObj.getUserEmail())) {
            return new ApiResponse<>(HttpStatus.CONFLICT.value(), "User already exists with given email address", null);
        }

        User user = encodePassword(signUpObj);
        try {
            userRepository.save(user);
            long expireTimeOut = 3600000;
            UserDetails userDetails = customUserDetailService.loadUserByUsername(user.getUserName());
            String jwtToken = jwtUtils.generateToken(userDetails, expireTimeOut);
            ResponseCookie cookie = ResponseCookie.from(AppConstant.JWT_TOKEN_CONST, jwtToken)
                    .httpOnly(false)
                    .secure(true) // Set to true in production for HTTPS
                    .path("/")
                    .maxAge(expireTimeOut)
                    .sameSite(AppConstant.SAME_SITE_COOKIE)
                    .build();
            response.addHeader(AppConstant.SET_HEADER, cookie.toString());
            return new ApiResponse<>(HttpStatus.CREATED.value(), "User created successfully!", null);
        } catch (UserEmailAlreadyExistsException emailException) {
            return new ApiResponse<>(HttpStatus.CONFLICT.value(), "User already exists with given email address.", null);
        }
    }

    /**
     * to authentic user for login
     *
     * @param input    as LoginRequestDTO
     * @param response as HttpServletResponse
     * @return ApiResponse
     */
    public ApiResponse<Object> authenticate(LoginRequestDTO input, HttpServletResponse response) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getUsername(),
                        input.getPassword()
                )
        );
        Optional<User> optionalUser = userRepository.findByUserName(input.getUsername());
        User authenticatedUser = optionalUser.orElseThrow(() -> new UsernameNotFoundException("user not found"));
        UserDetails userDetails = customUserDetailService.loadUserByUsername(authenticatedUser.getUserName());
        long expireTimeOut = input.isRememberMe() ? 7 * 24 * 3600000 : 3600000;
        String jwtToken = jwtUtils.generateToken(userDetails, expireTimeOut);
        ApiResponse<Object> res = new ApiResponse<>(HttpStatus.OK.value(), "Logged in successfully!", null);
        ResponseCookie cookie = ResponseCookie.from(AppConstant.JWT_TOKEN_CONST, jwtToken)
                .httpOnly(false)
                .secure(true) // Set to true in production for HTTPS
                .path("/")
                .maxAge(expireTimeOut)
                .sameSite(AppConstant.SAME_SITE_COOKIE)
                .build();
        response.addHeader(AppConstant.SET_HEADER, cookie.toString());
        return res;
    }

    /**
     * method for logout the user from loggedIn session
     *
     * @param response HttpServletResponse
     * @return ApiResponse
     */
    public ApiResponse<Object> logout(HttpServletResponse response) {
        ApiResponse<Object> res = new ApiResponse<>(HttpStatus.OK.value(), "Logged in successfully!", null);
        ResponseCookie cookie = ResponseCookie.from("bearer", "")
                .httpOnly(false)
                .secure(true) // Set to true in production for HTTPS
                .path("/")
                .maxAge(0)
                .sameSite("Strict")
                .build();
        response.addHeader("Set-Cookie", cookie.toString());
        return res;
    }

    /**
     * to encode password while saving a new user
     *
     * @param signupDTO SignupRequestDTO
     * @return User entity
     */
    private User encodePassword(SignupRequestDTO signupDTO) {
        User user = new User();
        user.setUserName(signupDTO.getUserName());
        user.setFirstName(signupDTO.getFirstName());
        user.setLastName(signupDTO.getLastName());
        user.setUserEmail(signupDTO.getUserEmail());
        user.setPassword(passwordEncoder.encode(signupDTO.getPassword()));
        return user;
    }

    /**
     * method to get actual data from db
     *
     * @param username String
     * @return Boolean
     */
    private boolean checkUserNameExists(String username) {
        Optional<User> user = userRepository.findByUserName(username);
        return user.isEmpty();
    }

    /**
     * method to check user email exist
     *
     * @param userEmail String
     * @return Boolean
     */
    private boolean checkEmailExists(String userEmail) {
        return userRepository.existsByUserEmail(userEmail);
    }
}

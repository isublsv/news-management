package com.epam.lab.controller;

import com.epam.lab.dto.UserDto;
import com.epam.lab.payload.JwtLoginResponse;
import com.epam.lab.payload.LoginRequest;
import com.epam.lab.payload.MessageResponse;
import com.epam.lab.security.JwtUtils;
import com.epam.lab.service.UserDetailsImpl;
import com.epam.lab.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:4200" })
@RestController
@RequestMapping("/auth")
@Validated
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder encoder;

    @Autowired
    public AuthController(
            final UserService userServiceValue,
            final AuthenticationManager authenticationManagerValue,
            final JwtUtils jwtUtilsValue,
            final PasswordEncoder encoderValue) {
        userService = userServiceValue;
        authenticationManager = authenticationManagerValue;
        jwtUtils = jwtUtilsValue;
        encoder = encoderValue;
    }

    @PostMapping(value = "/register", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> registerUser(@RequestBody @Valid final UserDto userDto) {
        userDto.setPassword(encoder.encode(userDto.getPassword()));
        userService.create(userDto);
        return ResponseEntity.ok(new MessageResponse("User successfully registered!"));
    }

    @PostMapping(value = "/login", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> authenticateUser(@RequestBody @Valid LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getLogin(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                                        .map(GrantedAuthority::getAuthority)
                                        .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtLoginResponse(jwt,
                                                      userDetails.getId(),
                                                      userDetails.getName(),
                                                      userDetails.getSurname(),
                                                      userDetails.getUsername(),
                                                      roles));
    }   
}

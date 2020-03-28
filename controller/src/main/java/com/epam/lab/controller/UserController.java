package com.epam.lab.controller;

import com.epam.lab.configuration.JwtUtils;
import com.epam.lab.dto.UserDto;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:4200" })
@RestController
@RequestMapping("/auth")
@Validated
public class UserController {

    private UserService userService;
    private AuthenticationManager authenticationManager;
    private JwtUtils jwtUtils;

    @Autowired
    public UserController(final UserService userServiceValue, final AuthenticationManager authenticationManagerValue,
            final JwtUtils jwtUtilsValue) {
        userService = userServiceValue;
        authenticationManager = authenticationManagerValue;
        jwtUtils = jwtUtilsValue;
    }

    @PostMapping(value = "/signup", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto createUser(@RequestBody @Valid final UserDto userDto) {
        return userService.create(userDto);
    }

    @PostMapping("/signin")
    public ResponseEntity<Object> authenticateUser(@RequestBody @Valid UserDto userDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userDto.getLogin(), userDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                                        .map(GrantedAuthority::getAuthority)
                                        .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                                                 userDetails.getId(),
                                                 userDetails.getName(),
                                                 userDetails.getSurname(),
                                                 userDetails.getUsername(),
                                                 roles));
    }
    
    @GetMapping(value = "/find/{id}", produces = APPLICATION_JSON_VALUE)
    public UserDto findUserById(@PathVariable @Positive(message = "Id  must positive") final Long id) {
        return userService.find(id);
    }

    @PutMapping(value = "/edit", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public UserDto editUser(@RequestBody @Valid final UserDto userDto) {
        return userService.update(userDto);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable @Positive(message = "Id must positive") final Long id) {
        userService.delete(id);
    }

    @GetMapping("/findAll")
    public List<UserDto> findAllNews() {
        return userService.findAll();
    }
    
}

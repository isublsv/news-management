package com.epam.lab.controller;

import com.epam.lab.dto.UserDto;
import com.epam.lab.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(final UserService userServiceValue) {
        userService = userServiceValue;
    }

    @PostMapping(value = "/add", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public UserDto createUser(@RequestBody @Valid final UserDto userDto) {
        return userService.create(userDto);
    }

    @GetMapping(value = "/find/{id}", produces = APPLICATION_JSON_VALUE)
    public UserDto findUserById(@PathVariable @Positive(message = "Id  must positive") final Long id) {
        return userService.find(id);
    }

    @PutMapping(value = "/edit", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public UserDto editUser(@RequestBody @Valid final UserDto userDto) {
        return userService.update(userDto);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable @Positive(message = "Id must positive") final Long id) {
        userService.delete(id);
    }
}

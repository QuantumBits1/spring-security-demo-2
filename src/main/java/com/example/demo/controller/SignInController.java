package com.example.demo.controller;

import com.example.demo.core.User;
import com.example.demo.security.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.Month;

@Slf4j
@Validated
@RestController
@RequestMapping("api/signin")
public class SignInController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public SignInController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping
    User signin(@RequestParam(value = "idNum") String idNum, @RequestParam(value = "password") String password) {
        User user = new User(01L, "Ali", idNum, passwordEncoder.encode(password), User.Role.USER,
                LocalDate.of(1999, Month.DECEMBER, 30), 22);
        return userService.add(user);
    }

    @PostMapping("/checkemail")
    Boolean idNumExists(@RequestParam String idNum) {
        return userService.idNumExists(idNum);
    }

}

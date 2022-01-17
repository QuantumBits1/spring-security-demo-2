package com.example.demo.controller;

import com.example.demo.UserProperties;
import com.example.demo.core.User;
import com.example.demo.error.EntityNotFoundException;
import com.example.demo.security.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Collection;
import java.util.Optional;

@Slf4j
@Validated
@CrossOrigin
@RestController
@RequestMapping("api/user")
public class UserController {
/*
    @Value("${greeting}")
    private String message;
*/
    private UserService userService;
    private UserProperties userProperties;
    private PasswordEncoder passwordEncoder;


    public UserController(UserService userService, UserProperties userProperties, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.userProperties = userProperties;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public Collection<User> getUsers(OAuth2Authentication authentication) {
        String auth = (String) authentication.getUserAuthentication().getPrincipal();
        String role = authentication.getAuthorities().iterator().next().getAuthority();

        if(role.equals(User.Role.USER.name()))
            return userService.getUsersByIdNum(auth);
        return userService.getUsers();
    }

    @GetMapping("/{id}")
    public Optional<User> getUser(@PathVariable("id") long id) {
        return userService.get(id);
    }

    @GetMapping(path="/single", produces={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Optional<User>> getSingleUser(@RequestParam("id") Optional<Long> optional) {
        return ResponseEntity.ok(userService.get(optional.orElse(1L)));
    }

    @GetMapping("/getuserbyidnum")
    @PreAuthorize("hasAuthority('USER')")
    public User getUserByIdNum(@RequestParam String idNum, OAuth2Authentication oAuth2Authentication) {
//        String auth = (String) oAuth2Authentication.getPrincipal();
//        String role = oAuth2Authentication.getAuthorities().iterator().next().getAuthority();
        return userService.getUserByIdNum(idNum).orElseThrow(() -> new EntityNotFoundException(User.class, "id_num", idNum));
    }

    @GetMapping(path="/msg")
    public String getMessage(@RequestHeader("user-agent") String userAgent) {
        return userProperties.getGreeting() + " using " + userAgent;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> addUser(@RequestBody User user) {
        userService.add(user);
        if(user.getId() > 0) {
            URI uri = URI.create("/api/user/" + user.getId());
            return ResponseEntity.accepted().location(uri).build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

//    @PostMapping
//    @PreAuthorize("!hasAuthority('USER')")
//    public User createUser(@RequestBody User user) {
//
//    }
}

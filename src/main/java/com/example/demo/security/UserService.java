package com.example.demo.security;

import com.example.demo.core.User;
import com.example.demo.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    public UserService(UserRepository userDao) {
        this.userRepository = userDao;
    }

    public Optional<User> get(long id) {
        return userRepository.findById(id);
    }

    public Optional<User> getUserByIdNum(String idNum) { return userRepository.findByIdNum(idNum); }

    public Collection<User> getUsers() {
        return userRepository.findAll();
    }

    public Collection<User> getUsersByIdNum(String auth) {
        return userRepository.findAllByIdNum(auth);
    }

    public User add(User user) {
            return userRepository.save(user);
    }

    public Boolean idNumExists(String idNum) {
        return userRepository.existsById(idNum);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findByIdNum(s).orElseThrow(() -> new RuntimeException("User not found" + s));

        GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().name());
        return new org.springframework.security.core.userdetails.User(user.getIdNum(), user.getPassword(),
                Arrays.asList(authority));
    }
}

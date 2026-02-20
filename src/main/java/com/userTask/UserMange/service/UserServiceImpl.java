package com.userTask.UserMange.service;

import com.userTask.UserMange.config.auth.JwtService;
import com.userTask.UserMange.dto.AuthRequest;
import com.userTask.UserMange.model.User;
import com.userTask.UserMange.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder,
                           AuthenticationManager authenticationManager, JwtService jwtService){
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @Override
    public void registerUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        long count = userRepository.count();
        if(count == 0){
            user.setRole("ROLE_ADMIN");
        }else {
            user.setRole("ROLE_USER");
        }
        userRepository.save(user);
    }

    @Override
    public String verifyUser(AuthRequest request) {
        // 1. Authenticate the user (this calls your CustomUserDetailService automatically)
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(request.getUsername());
        } else {
            throw new RuntimeException("Authentication failed");
        }
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}

package com.userTask.UserMange.controller;

import com.userTask.UserMange.dto.AuthRequest;
import com.userTask.UserMange.model.User;
import com.userTask.UserMange.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/register")
    public String registerUser(@RequestBody User user){
        userService.registerUser(user);
        return "User Registered Successfully";
    }

    @PostMapping("/login")
    public String loginUser(@RequestBody AuthRequest request){
        return userService.verifyUser(request);
    }

    @GetMapping
    public List<User> getAll(){
        return userService.getAllUsers();
    }

}

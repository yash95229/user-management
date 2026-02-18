package com.userTask.UserMange.service;

import com.userTask.UserMange.dto.AuthRequest;
import com.userTask.UserMange.model.User;

import java.util.List;

public interface UserService {
    void registerUser(User user);
    String verifyUser(AuthRequest request);
    List<User> getAllUsers();
}

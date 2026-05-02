package com.lamnd.service.impl;

import com.lamnd.dto.request.UserCreateRequest;
import com.lamnd.dto.request.UserUpdateRequest;
import com.lamnd.dto.response.UserResponse;
import com.lamnd.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public void createUser(UserCreateRequest request) {

    }

    @Override
    public List<UserResponse> getUsers() {
        return List.of();
    }

    @Override
    public UserResponse getUserById(Long id) {
        return null;
    }

    @Override
    public UserResponse profile() {
        return null;
    }

    @Override
    public UserResponse updateUser(UserUpdateRequest request, Long id) {
        return null;
    }

    @Override
    public void deleteUser(Long id) {

    }
}

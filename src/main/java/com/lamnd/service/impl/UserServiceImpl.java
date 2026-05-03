package com.lamnd.service.impl;

import com.lamnd.dto.request.UserCreateRequest;
import com.lamnd.dto.request.UserUpdateRequest;
import com.lamnd.dto.response.UserResponse;
import com.lamnd.entity.User;
import com.lamnd.exception.ResourceExistedException;
import com.lamnd.exception.ResourceNotFoundException;
import com.lamnd.mapper.UserMapper;
import com.lamnd.repository.UserRepo;
import com.lamnd.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;
    private final UserRepo userRepo;

    @Override
    public void createUser(UserCreateRequest request) {
        User user = userMapper.toEntity(request);

        // xử lý race condition khi có nhiều request tạo user cùng lúc với username/email/phone giống nhau
        try {
            userRepo.save(user);
        } catch (DataIntegrityViolationException ex) {
            throw new ResourceExistedException("User", "username", request.username());
        }
    }

    @Override
    public List<UserResponse> getUsers() {
        List<User> users = userRepo.findAll();
        return userMapper.toList(users);
    }

    @Override
    public UserResponse getUserById(Long id) {
        User existingUser = findUserById(id);
        return userMapper.toDTO(existingUser);
    }

    @Override
    public UserResponse getMyInfo() {
//        var context = SecurityContextHolder.getContext();
//
//        String username = context.getAuthentication().getName();
//
//        User user = findUserByUsername(username);
//
//        return userMapper.toDTO(user);
        return null;
    }

    @Override
    public UserResponse updateUser(UserUpdateRequest request, Long id) {
        User existingUser = findUserById(id);

        // kiểm tra trùng lặp username/email/phone khi update và bỏ qua chính bản thân user đang update
        boolean isUserNameExisted = userRepo.existsByUserNameAndIdNot(request.username(), id);
        boolean isEmailExisted = userRepo.existsByEmailAndIdNot(request.email(), id);
        boolean isPhoneExisted = userRepo.existsByPhoneAndIdNot(request.phoneNumber(), id);

        // Bước này chỉ check và không xử lý được race condition
        if (isUserNameExisted) {
            throw new ResourceExistedException("User", "username", request.username());
        }
        if (isEmailExisted) {
            throw new ResourceExistedException("User", "email", request.email());
        }
        if (isPhoneExisted) {
            throw new ResourceExistedException("User", "phone", request.phoneNumber());
        }

        // cập nhật thông tin user với dữ liệu mới từ request
        userMapper.updateEntityFromRequest(request, existingUser);

        // Tránh race condition khi có nhiều request update cùng lúc với username/email/phone giống nhau
        try {
            return userMapper.toDTO(userRepo.save(existingUser));
        } catch (DataIntegrityViolationException ex) {

            if (ex.getMessage().contains("uq_users_email")) {
                throw new ResourceExistedException("User", "email", request.email());
            }

            if (ex.getMessage().contains("uq_users_username")) {
                throw new ResourceExistedException("User", "username", request.username());
            }

            if (ex.getMessage().contains("uq_users_phone")) {
                throw new ResourceExistedException("User", "phone number", request.phoneNumber());
            }

            throw ex;
        }
    }

    @Override
    public void deleteUser(Long id) {
        userRepo.deleteById(id);
    }

    private User findUserById(Long id) {
        return userRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
    }
}

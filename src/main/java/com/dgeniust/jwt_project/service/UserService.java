package com.dgeniust.jwt_project.service;

import com.dgeniust.jwt_project.constant.PredefinedRole;
import com.dgeniust.jwt_project.dto.request.UserCreationRequest;
import com.dgeniust.jwt_project.dto.response.UserResponse;
import com.dgeniust.jwt_project.entity.Role;
import com.dgeniust.jwt_project.entity.User;
import com.dgeniust.jwt_project.exception.AppException;
import com.dgeniust.jwt_project.exception.ErrorCode;
import com.dgeniust.jwt_project.mapper.UserMapper;
import com.dgeniust.jwt_project.repository.RoleRepository;
import com.dgeniust.jwt_project.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserService {

    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoderBCRYPT;
    RoleRepository roleRepository;

    public UserResponse createUserRequest(UserCreationRequest request) {
        if(userRepository.existsByUsername(request.getUsername()))
            throw new AppException(ErrorCode.USER_EXISTED);

        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoderBCRYPT.encode(request.getPassword()));

        HashSet<Role> roles = new HashSet<>();
        roleRepository.findById(PredefinedRole.USER_ROLE).ifPresent(roles::add);

        user.setRoles(roles);

        return userMapper.toUserResponse(userRepository.save(user));
    }

//    PreAuthorize -> Spring sẽ tạo ra 1 proxy ngay trước hàm này thì để vào hàm này thì trước lúc gọi hàm
//         phải kiểm tra role là ADMIN
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getUserRequest(){
        log.info("In method get all users");
        return userRepository.findAll().stream().map(userMapper::toUserResponse).toList();
    }
//    Nhập vào Bearer Token của ai thì sẽ hiện ra thông tin của người đó ( với điều kiện
//    userId nhập trên đường dẫn phải chung 1 chủ vs token nhận đươc khi đăng nhập)
//    -> thì khi đó sẽ hiện ra thông tin của user vs userId mình nhập và token generate khi mình login
//    tài khoản chính user đó
    @PostAuthorize("returnObject.username == authentication.name")
    public UserResponse getUserById(String id){
        log.info("In method get user by Id");
        return userMapper.toUserResponse(userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found")));
    }

//    public UserResponse updateUserById(String userId,UserUpdateRequest request){
//        User user = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
//        userMapper.updateUser(user, request);
//
//        user.setPassword(passwordEncoderBCRYPT.encode(request.getPassword()));
//
//        var roles = roleRepository.findAllById(request.getRoles());
//        user.setRoles(new HashSet<>(roles));
//
//        return userMapper.toUserResponse(userRepository.save(user));
//    }

    public void deleteUserRequest(String userId){
        userRepository.deleteById(userId);
    }

    //Trong Spring Security khi 1 request đc xác thực thành công thì thông tin của user
    //đăng nhập sẽ được lưu vào SecurityContextHolder
    public UserResponse getMyInfo(){
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        User user= userRepository.findByUsername(name).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        return userMapper.toUserResponse(user);
    }
}


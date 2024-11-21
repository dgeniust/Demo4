package com.dgeniust.jwt_project.mapper;

import com.dgeniust.jwt_project.dto.request.UserCreationRequest;
import com.dgeniust.jwt_project.dto.response.UserResponse;
import com.dgeniust.jwt_project.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest request);

    UserResponse toUserResponse(User user);
}

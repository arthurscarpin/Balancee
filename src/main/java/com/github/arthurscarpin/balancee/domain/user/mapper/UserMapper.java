package com.github.arthurscarpin.balancee.domain.user.mapper;

import com.github.arthurscarpin.balancee.domain.user.dto.UserRequest;
import com.github.arthurscarpin.balancee.domain.user.dto.UserResponse;
import com.github.arthurscarpin.balancee.domain.user.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User map(UserRequest userRequest) {
        User user = new User();
        user.setName(userRequest.getName());
        user.setEmail(userRequest.getEmail());
        return user;
    }

    public UserResponse map(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setName(user.getName());
        userResponse.setEmail(user.getEmail());
        return userResponse;
    }
}

package com.github.arthurscarpin.balancee.domain.user.mapper;

import com.github.arthurscarpin.balancee.domain.user.dto.UserRequestDTO;
import com.github.arthurscarpin.balancee.domain.user.dto.UserResponseDTO;
import com.github.arthurscarpin.balancee.domain.user.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User map(UserRequestDTO userDTO) {
        User user = new User();
        user.setName(userDTO.name());
        user.setEmail(userDTO.email());
        return user;
    }

    public UserResponseDTO map(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail()
        );
    }
}

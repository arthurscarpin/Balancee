package com.github.arthurscarpin.balancee.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserResponse {

    private Long id;

    private String name;

    private String email;
}

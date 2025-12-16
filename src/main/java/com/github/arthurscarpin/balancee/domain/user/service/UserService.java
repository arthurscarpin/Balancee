package com.github.arthurscarpin.balancee.domain.user.service;

import com.github.arthurscarpin.balancee.domain.user.dto.UserRequestDTO;
import com.github.arthurscarpin.balancee.domain.user.dto.UserResponseDTO;
import com.github.arthurscarpin.balancee.domain.user.mapper.UserMapper;
import com.github.arthurscarpin.balancee.domain.user.model.User;
import com.github.arthurscarpin.balancee.domain.user.repository.UserRepository;
import com.github.arthurscarpin.balancee.exception.BusinessException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository repository;

    private final UserMapper mapper;

    public UserService(UserRepository repository, UserMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Transactional
    public UserResponseDTO create(UserRequestDTO userDTO) {
        Optional<User> emailExists = repository.findByEmail(userDTO.email());
        if (!emailExists.equals(Optional.empty())) {
            throw new BusinessException("The email already exists!");
        }
        User userCreated = repository.save(mapper.map(userDTO));
        return mapper.map(userCreated);
    }

    public List<UserResponseDTO> findAll() {
        List<User> users = repository.findAll();
        return users.stream()
                .map(mapper::map)
                .collect(Collectors.toList());
    }

    public UserResponseDTO findById(Long id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new BusinessException("User not found!"));
        return mapper.map(user);
    }

    @Transactional
    public UserResponseDTO updateById(Long id, UserRequestDTO userDTO) {
        User user = repository.findById(id)
                .orElseThrow(() -> new BusinessException("User not found!"));
        repository.findByEmailAndNotId(userDTO.email(), id)
                .ifPresent(u -> {
                    throw new BusinessException("Email already in use!");
                });
        user.setName(userDTO.name());
        user.setEmail(userDTO.email());
        return mapper.map(repository.save(user));
    }

    @Transactional
    public void deleteById(Long id) {
        if (repository.findById(id).isEmpty()) {
            throw new BusinessException("User not found!");
        }
        repository.deleteById(id);
    }
}

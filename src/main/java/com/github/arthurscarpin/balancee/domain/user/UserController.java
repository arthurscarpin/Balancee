package com.github.arthurscarpin.balancee.domain.user.controller;

import com.github.arthurscarpin.balancee.domain.user.dto.UserRequestDTO;
import com.github.arthurscarpin.balancee.domain.user.dto.UserResponseDTO;
import com.github.arthurscarpin.balancee.domain.user.service.UserService;
import com.github.arthurscarpin.balancee.exception.BusinessException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Create a new user.", description = "Creates a new user with the provided details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created successfully."),
            @ApiResponse(responseCode = "400", description = "Invalid input data.")
    })
    public ResponseEntity<?> createPostUser(@RequestBody UserRequestDTO user) {
        try {
            UserResponseDTO userCreated = service.create(user);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(userCreated);
        } catch (BusinessException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @GetMapping
    @Operation(summary = "List all users.", description = "Retrieves a list of all registered users.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of users retrieved successfully."),
    })
    public ResponseEntity<?> findAllGetUsers() {
        List<UserResponseDTO> users = service.findAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get user by ID.", description = "Retrieves a user by its unique ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User by ID."),
            @ApiResponse(responseCode = "404", description = "User not found.")
    })
    public ResponseEntity<?> findByIdGetUser(@PathVariable Long id) {
        try {
            UserResponseDTO user = service.findById(id);
            return ResponseEntity.ok(user);
        } catch (BusinessException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update user by ID.", description = "Updates the details of an existing user by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated successfully."),
            @ApiResponse(responseCode = "404", description = "User not found.")
    })
    public ResponseEntity<?> updateByIdPutUser(@PathVariable Long id, @RequestBody UserRequestDTO user) {
        try {
            UserResponseDTO userUpdated = service.updateById(id, user);
            return ResponseEntity.ok(userUpdated);
        } catch (BusinessException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete user by ID.", description = "Deletes a user by its unique ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User deleted successfully."),
            @ApiResponse(responseCode = "404", description = "User not found.")
    })
    public ResponseEntity<?> deleteByIdDeleteUser(@PathVariable Long id) {
        try {
            service.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .build();
        } catch (BusinessException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }
}

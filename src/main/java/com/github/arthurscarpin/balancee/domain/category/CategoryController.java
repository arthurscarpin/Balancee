package com.github.arthurscarpin.balancee.domain.category;

import com.github.arthurscarpin.balancee.exception.BusinessException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService service;

    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Create a new category.", description = "Creates a new category with the provided details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Category created successfully."),
            @ApiResponse(responseCode = "400", description = "Invalid input data.")
    })
    ResponseEntity<?> createPostCategory(@RequestBody CategoryRequestDTO category) {
        try {
            CategoryResponseDTO categoryCreated = service.create(category);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(categoryCreated);
        } catch (BusinessException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @GetMapping
    @Operation(summary = "List all categories.", description = "Retrieves a list of all registered categories.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of categories retrieved successfully."),
    })
    ResponseEntity<?> findAllGetCategory() {
        List<CategoryResponseDTO> categories = service.findAll();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get category by ID.", description = "Retrieves a category by its unique ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category by ID."),
            @ApiResponse(responseCode = "404", description = "Category not found.")
    })
    ResponseEntity<?> findByIdGetCategory(@PathVariable Long id) {
        try {
            CategoryResponseDTO category = service.findById(id);
            return ResponseEntity.ok(category);
        } catch (BusinessException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update category by ID.", description = "Updates the details of an existing category by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category updated successfully."),
            @ApiResponse(responseCode = "404", description = "Category not found.")
    })
    ResponseEntity<?> updateByIdPutCategory(@PathVariable Long id, @RequestBody CategoryRequestDTO category) {
        try {
            CategoryResponseDTO categoryUpdated = service.updateById(id, category);
            return ResponseEntity.ok(categoryUpdated);
        } catch (BusinessException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete category by ID.", description = "Deletes a category by its unique ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Category deleted successfully."),
            @ApiResponse(responseCode = "404", description = "Category not found.")
    })
    ResponseEntity<?> deleteByIdDeleteCategory(@PathVariable Long id) {
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

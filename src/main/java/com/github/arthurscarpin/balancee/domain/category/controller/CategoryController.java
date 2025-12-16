package com.github.arthurscarpin.balancee.domain.category.controller;

import com.github.arthurscarpin.balancee.domain.category.dto.CategoryRequestDTO;
import com.github.arthurscarpin.balancee.domain.category.dto.CategoryResponseDTO;
import com.github.arthurscarpin.balancee.domain.category.model.Category;
import com.github.arthurscarpin.balancee.domain.category.service.CategoryService;
import com.github.arthurscarpin.balancee.exception.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService service;

    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @PostMapping
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
    ResponseEntity<?> findAllGetCategory() {
        List<CategoryResponseDTO> categories = service.findAll();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{id}")
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

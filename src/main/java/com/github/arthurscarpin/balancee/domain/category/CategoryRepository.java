package com.github.arthurscarpin.balancee.domain.category.repository;

import com.github.arthurscarpin.balancee.domain.category.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}

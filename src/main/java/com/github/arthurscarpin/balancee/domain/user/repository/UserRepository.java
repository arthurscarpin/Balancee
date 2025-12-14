package com.github.arthurscarpin.balancee.domain.user.repository;

import com.github.arthurscarpin.balancee.domain.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}

package com.github.arthurscarpin.balancee.domain.user.repository;

import com.github.arthurscarpin.balancee.domain.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByEmailAndIdNot(String email, Long id);
}

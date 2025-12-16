package com.github.arthurscarpin.balancee.domain.user.repository;

import com.github.arthurscarpin.balancee.domain.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = """
            SELECT u
            FROM User u
            WHERE u.email = :email""")
    Optional<User> findByEmail(@Param("email") String email);

    @Query(value = """
            SELECT u
            FROM User u
            WHERE u.email = :email
            AND u.id <> :id
            """)
    Optional<User> findByEmailAndNotId(@Param("email") String email, @Param("id") Long id);
}

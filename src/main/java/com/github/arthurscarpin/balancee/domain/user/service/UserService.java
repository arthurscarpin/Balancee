package com.github.arthurscarpin.balancee.domain.user.service;

import com.github.arthurscarpin.balancee.domain.user.model.User;
import com.github.arthurscarpin.balancee.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public User create(User user) {
        Optional<User> emailExists = repository.findByEmail(user.getEmail());
        if (emailExists.isPresent()) {
            return repository.save(user);
        } else {
            return null;
        }
    }

    public List<User> findAll() {
        return repository.findAll();
    }

    public User findById(Long id) {
        Optional<User> userExists = repository.findById(id);
        return userExists.orElse(null);
    }

    @Transactional
    public User updateById(Long id, User userUpdate) {
        Optional<User> userExists = repository.findById(id);
        if (userExists.isPresent()) {
            userUpdate.setId(id);
            return repository.save(userUpdate);
        } else {
            return null;
        }
    }

    @Transactional
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}

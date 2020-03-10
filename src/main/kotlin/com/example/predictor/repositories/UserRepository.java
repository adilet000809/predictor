package com.example.predictor.repositories;

import com.example.predictor.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Long> {

    Users findByEmail(String email);
    boolean existsByEmail(String email);

}

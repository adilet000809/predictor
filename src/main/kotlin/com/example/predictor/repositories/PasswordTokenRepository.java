package com.example.predictor.repositories;

import com.example.predictor.entity.PasswordToken;
import com.example.predictor.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Repository
@Transactional
public interface PasswordTokenRepository extends JpaRepository<PasswordToken, Long> {
    Optional<PasswordToken> findByToken(String token);
    void deleteAllByExpirationLessThan(Date now);
    void deleteAllByUser(Users user);
}

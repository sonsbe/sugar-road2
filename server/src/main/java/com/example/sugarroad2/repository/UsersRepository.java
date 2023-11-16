package com.example.sugarroad2.repository;

import com.example.sugarroad2.model.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, String> {
    Optional<Users> findByNickname(String nickname);
    Optional<Users> findByUserEmail(String userEmail);
}

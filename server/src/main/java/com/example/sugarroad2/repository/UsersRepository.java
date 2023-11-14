package com.example.sugarroad2.repository;

import com.example.sugarroad2.model.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, String> {
}

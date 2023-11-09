package com.example.sugarroad2.repository;

import com.example.sugarroad2.model.dto.UsersDTO;
import com.example.sugarroad2.model.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsersRepository extends JpaRepository<Users, String> {


}

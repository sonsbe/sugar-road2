package com.example.sugarroad2.repository;


import com.example.sugarroad2.model.entity.Post;
import com.example.sugarroad2.model.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {
    public List<Post> findByUser(Users users);
}

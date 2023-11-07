package com.example.sugarroad2.repository;

import com.example.sugarroad2.model.entity.PostCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostCategoryRepository extends JpaRepository<PostCategory, String> {
}
package com.example.sugarroad2.repository;

import com.example.sugarroad2.model.entity.PostCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostCategoryRepository extends JpaRepository<PostCategory, String> {
}
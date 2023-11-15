package com.example.sugarroad2.repository;

import com.example.sugarroad2.model.entity.Post;
import com.example.sugarroad2.model.entity.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostImageRepository extends JpaRepository<PostImage, Integer> {
    public List<PostImage> findByPostId(int id);

    public PostImage findByPostImagePath(String postImagePath);
}

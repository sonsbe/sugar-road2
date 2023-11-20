package com.example.sugarroad2.repository;


import com.example.sugarroad2.model.entity.Post;
import com.example.sugarroad2.model.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {
    //작성 글 조회
    public List<Post> findByUserId(String id);

    //검색
    public List<Post> findByContentContainsOrTitleContains(String content, String title);
    public List<Post> findByPostCategoryId(String id);

}

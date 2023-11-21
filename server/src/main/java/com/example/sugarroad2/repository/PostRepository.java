package com.example.sugarroad2.repository;


import com.example.sugarroad2.model.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {
    //작성 글 조회
    public List<Post> findByUserId(String id);



    //검색
    public List<Post> findByContentContainsOrTitleContains(String content, String title);

}

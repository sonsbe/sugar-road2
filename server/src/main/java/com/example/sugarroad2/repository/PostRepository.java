package com.example.sugarroad2.repository;


import com.example.sugarroad2.model.entity.Post;
import com.example.sugarroad2.model.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {
    //작성 글 조회
    public List<Post> findByUserId(String id);
    //날짜 순 정렬
    public List<Post> findByOrderByPostedDate();

    //검색
    List<Post> findByContentLikeOrTitleLike(String content, String title);

}

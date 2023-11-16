package com.example.sugarroad2.repository;

import com.example.sugarroad2.model.entity.Post;
import com.example.sugarroad2.model.entity.PostComment;
import com.example.sugarroad2.model.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostCommentRepository extends JpaRepository<PostComment, Integer> {

	public Page<PostComment> findByPostOrderByParentAscPostedDateAsc(Post post, Pageable pageable);

}

package com.example.sugarroad2.repository;

import com.example.sugarroad2.model.entity.Review;
import com.example.sugarroad2.model.entity.ReviewComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewCommentRepository extends JpaRepository<ReviewComment, Integer> {
	public Page<ReviewComment> findByReviewOrderByParentAscPostedDateAsc(Review review, Pageable pageable);

}

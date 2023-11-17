package com.example.sugarroad2.service;

import com.example.sugarroad2.model.entity.Review;
import com.example.sugarroad2.model.entity.ReviewComment;
import com.example.sugarroad2.repository.ReviewCommentRepository;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ReviewCommentService {

	@Autowired
	private ReviewCommentRepository repository;

	public ReviewComment create(ReviewComment entity){
		return repository.save(entity);
	}
	
	public Page<ReviewComment> readPage(Review review, Pageable pageable){
		return repository.findByReviewOrderByParentAscPostedDateAsc(review, pageable);
	}

	public ReviewComment readById(int id){
		return repository.findById(id).get();
	}
	public ReviewComment update(ReviewComment entity){
		final Optional<ReviewComment> original = repository.findById(entity.getId());
		original.ifPresent(review -> {
			review.setContent(entity.getContent());
			review.setPostedDate(entity.getPostedDate());
			repository.save(review);
		});
		return repository.findById(entity.getId()).get();
	}

	public void delete(ReviewComment entity){
		repository.deleteById(entity.getId());
	}

	public void deleteById(int id){
		repository.deleteById(id);
	}
}

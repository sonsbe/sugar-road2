package com.example.sugarroad2.service;

import com.example.sugarroad2.model.entity.Review;
import com.example.sugarroad2.model.entity.Store;
import com.example.sugarroad2.repository.ReviewRepository;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ReviewService {

	@Autowired
	private ReviewRepository repository;

	public Review create(Review entity){
		return repository.save(entity);
	}

	public Review readById(int id){
		return repository.findById(id).get();
	}

	public Page<Review> readPage(Store store, Pageable pageable){
		return repository.findByStoreOrderByPostedDateDesc(store, pageable);
	}

	public Review update(final Review entity){
		final Optional<Review> original = repository.findById(entity.getId());
		original.ifPresent(review -> {
			review.setContent(entity.getContent());
			review.setStar(entity.getStar());
			review.setPostedDate(entity.getPostedDate());
			review.setReviewImagePath(entity.getReviewImagePath());
			repository.save(review);
		});
		return repository.findById(entity.getId()).get();
	}

	public void delete(Review entity){
		repository.deleteById(entity.getId());
	}

	public void deleteById(int id){
		repository.deleteById(id);
	}
}

package com.example.sugarroad2.repository;

import com.example.sugarroad2.model.entity.Review;
import com.example.sugarroad2.model.entity.Store;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
	public Page<Review> findByStoreOrderByPostedDateDesc(Store store, Pageable pageable);
}

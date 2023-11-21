package com.example.sugarroad2.model.dto.request;

import com.example.sugarroad2.model.entity.Review;
import com.example.sugarroad2.model.entity.Store;

import java.time.LocalDateTime;

import com.example.sugarroad2.model.entity.Users;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ReviewRequestDTO {
	private Integer id;
	private String userId;
	private String nickname;
	private int storeId;
	private String content;
	private LocalDateTime postedDate;
	private int star;
//	private MultipartFile uploadImage;

	public Review toEntity(Users users, Store store, String reviewImagePath){
		Review review = Review.builder()
			.users(users)
			.store(store)
			.content(content)
			.postedDate(postedDate)
			.star(star)
			.build();

		if (id != null){
			review.setId(id);
		}

//		if (!reviewImagePath.isEmpty()){
//			review.setReviewImagePath(reviewImagePath);
//		}

		return review;
	}
}

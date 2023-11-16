package com.example.sugarroad2.model.dto.request;

import com.example.sugarroad2.model.entity.Review;
import com.example.sugarroad2.model.entity.Store;
import com.example.sugarroad2.model.entity.Users;
import com.example.sugarroad2.util.ImageUtil;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

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

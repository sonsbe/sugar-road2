package com.example.sugarroad2.model.dto.response;

import com.example.sugarroad2.model.entity.Review;
import com.example.sugarroad2.util.ImageUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ReviewResponseVO {
	private Integer id;
	private String userId;
	private String nickname;
	private int storeId;
	private String content;
	private LocalDateTime postedDate;
	private int star;
	private MultipartFile uploadImage;
	private String reviewImagePath;

	public ReviewResponseVO(Review entity){
		id = entity.getId();
		storeId = entity.getStore().getId();
		userId = entity.getUsers().getId();
		nickname = entity.getUsers().getNickname();

		content = entity.getContent();
		postedDate = entity.getPostedDate();
		star = entity.getStar();
		reviewImagePath = entity.getReviewImagePath();
	}
}

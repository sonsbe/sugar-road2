package com.example.sugarroad2.model.dto.response;

import com.example.sugarroad2.model.entity.ReviewComment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ReviewCommentResponseVO {
    private Integer id;
    private String userId;
    private String nickname;
    private int reviewId;
    private String content;
    private LocalDateTime postedDate;
    private Integer parentComment;
    
    public ReviewCommentResponseVO(ReviewComment entity){
        id = entity.getId();
        reviewId = entity.getReview().getId();
		userId = entity.getUsers().getId();
		nickname = entity.getUsers().getNickname();
        content = entity.getContent();
        postedDate = entity.getPostedDate();
        if (entity.getParent()!= null){
            parentComment = entity.getParent().getId();
        }
    }
}

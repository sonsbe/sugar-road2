package com.example.sugarroad2.model.dto.request;

import com.example.sugarroad2.model.entity.Review;
import com.example.sugarroad2.model.entity.ReviewComment;

import java.time.LocalDateTime;

import com.example.sugarroad2.model.entity.Users;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewCommentRequestDTO {
    private Integer id;
    private String userId;
    private String nickname;
    private int reviewId;
    private String content;
    private LocalDateTime postedDate;
    private Integer parentComment;

    public ReviewComment toEntity(Users users, Review review){
        ReviewComment reviewComment = ReviewComment.builder()
            .users(users)
            .review(review)
            .content(content)
            .postedDate(postedDate)
            .build();

        if (id != null) {
            reviewComment.setId(id);
        }

        return reviewComment;
    }

    public ReviewComment toEntity(Users users, Review review, ReviewComment parent){
        ReviewComment reviewComment = ReviewComment.builder()
            .users(users)
            .review(review)
            .content(content)
            .postedDate(postedDate)
            .parent(parent)
            .build();

        if (id != null){
            reviewComment.setId(id);
        }

        return reviewComment;
    }
}

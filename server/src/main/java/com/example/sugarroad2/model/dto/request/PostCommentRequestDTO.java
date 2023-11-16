package com.example.sugarroad2.model.dto.request;

import com.example.sugarroad2.model.entity.Post;
import com.example.sugarroad2.model.entity.PostComment;
import com.example.sugarroad2.model.entity.Users;
import java.time.LocalDateTime;
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
public class PostCommentRequestDTO {
    private Integer id;
    private String userId;
    private String nickname;
    private int postId;
    private String content;
    private LocalDateTime postedDate;
    private Integer parentComment;

    public PostComment toEntity(Users users, Post post) {
        PostComment postComment = PostComment.builder()
            .users(users)
            .post(post)
            .content(content)
            .postedDate(postedDate)
            .build();

        if (id != null){
            postComment.setId(id);
        }

        return postComment;
    }
    public PostComment toEntity(Users users, Post post, PostComment parent) {
        PostComment postComment = PostComment.builder()
            .users(users)
            .post(post)
            .content(content)
            .postedDate(postedDate)
            .parent(parent)
            .build();

        if (id != null){
            postComment.setId(id);
        }

        return postComment;
    }
}

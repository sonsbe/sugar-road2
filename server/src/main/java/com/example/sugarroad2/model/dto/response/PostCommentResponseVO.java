package com.example.sugarroad2.model.dto.response;

import com.example.sugarroad2.model.entity.PostComment;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class PostCommentResponseVO {
    private Integer id;
    private String userId;
    private String nickname;
    private int postId;
    private String content;
    private LocalDateTime postedDate;
    private Integer parentComment;

    public PostCommentResponseVO(PostComment entity){
        id = entity.getId();
        postId = entity.getPost().getId();
		userId = entity.getUsers().getId();
		nickname = entity.getUsers().getNickname();
        content = entity.getContent();
        postedDate = entity.getPostedDate();
        if (entity.getParent()!= null){
            parentComment = entity.getParent().getId();
        }
    }
}

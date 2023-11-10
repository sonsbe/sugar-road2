package com.example.sugarroad2.model.dto;

import com.example.sugarroad2.model.entity.Post;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@ToString
public class PostResponse {
    private int id;
    private String content;
    private String title;
    private List<String> postImage;
    private LocalDateTime postedDate;
    private String userId;
    private String postCategoryId;
    private int commentCount;
    private int recommendCount;
    public PostResponse(Post post, List<String> postImage){
        id = post.getId();
        content = post.getContent();
        title = post.getTitle();
        postedDate = post.getPostedDate();
        userId = post.getUser().getId();
        this.postImage = postImage;
        postCategoryId = post.getPostCategory().getId();

    }
}

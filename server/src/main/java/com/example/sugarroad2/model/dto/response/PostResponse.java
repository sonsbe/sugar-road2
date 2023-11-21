package com.example.sugarroad2.model.dto.response;

import com.example.sugarroad2.model.entity.Post;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@ToString
@EqualsAndHashCode
public class PostResponse {
    private int id;
    private String content;
    private String title;
    private List<String> postImage;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime postedDate;
    private String userId;
    private String postCategoryId;
    private long commentCount;
    private long recommendCount;
    private long viewsCount;
    public PostResponse(Post post, List<String> postImage, long viewsCount){
        id = post.getId();
        content = post.getContent();
        title = post.getTitle();
        postedDate = post.getPostedDate();
        userId = post.getUser().getId();
        this.postImage = postImage;
        this.viewsCount = viewsCount;
        postCategoryId = post.getPostCategory().getId();

    }
}

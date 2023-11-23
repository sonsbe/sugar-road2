package com.example.sugarroad2.model.dto.response;

import com.example.sugarroad2.model.entity.Post;
import com.example.sugarroad2.repository.PostRepository;
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
public class PostResponse implements Comparable<PostResponse>{
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
    public PostResponse(Post post, List<String> postImage, long viewsCount, long recommendCount){
        id = post.getId();
        content = post.getContent();
        title = post.getTitle();
        postedDate = post.getPostedDate();
        userId = post.getUser().getId();
        this.postImage = postImage;
        this.viewsCount = viewsCount;
        this.recommendCount = recommendCount;
        postCategoryId = post.getPostCategory().getId();
    }
    @Override public int compareTo(PostResponse postResponse) {
        if (postResponse.recommendCount < recommendCount) {
            return 1;
        } else if (postResponse.recommendCount > recommendCount ){
            return -1;
        }
        return 0;
    }

}

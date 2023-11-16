package com.example.sugarroad2.model.dto.request;

import com.example.sugarroad2.model.entity.Post;
import com.example.sugarroad2.model.entity.PostCategory;
import com.example.sugarroad2.model.entity.PostImage;
import com.example.sugarroad2.model.entity.Users;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class PostRequest {

    private String content;
    private String title;
    private List<String> postImage;
    private String userId;
    private String postCategoryId;
    //private MultipartFile[] uploadImages;
    public Post toEntity(Users user, PostCategory postCategory){
        return Post.builder().
                content(content).
                title(title)
                .user(user)
                .postCategory(postCategory)
                .build();
    }
    public List<PostImage> toPostImage(Post post){
        List<PostImage> postImageList = new ArrayList<>();
        postImage.forEach(image -> postImageList.add(PostImage
                .builder()
                .postImagePath(image)
                .post(post)
                .build()));
        return postImageList;
    }

}

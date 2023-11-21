package com.example.sugarroad2.util;

import com.example.sugarroad2.model.dto.response.PostResponse;
import com.example.sugarroad2.model.entity.Post;
import com.example.sugarroad2.model.entity.PostImage;
import com.example.sugarroad2.service.PostImageService;
import com.example.sugarroad2.service.ViewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ConvertionUtil {

    @Autowired
    private PostImageService postImageService;
    @Autowired
    private ViewsService viewsService;

    public PostResponse convertToPostResponse(Post post) {
        List<String> postImage = new ArrayList<>();
        List<PostImage> postImageList = postImageService.readByPostId(post.getId());
        //postImage
        if (postImageList != null)
            postImageList.forEach(image -> {
                postImage.add(image.getPostImagePath());
            });
        long viewsCount = viewsService.count("p", post.getId());
        PostResponse postResponse = new PostResponse(post, postImage, viewsCount);
        //조회수
        return postResponse;
    }

}
>>>>>>> e748a60cb2bcd6ad90902109081ba3543657055a


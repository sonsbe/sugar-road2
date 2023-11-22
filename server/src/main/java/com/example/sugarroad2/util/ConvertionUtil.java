package com.example.sugarroad2.util;

import com.example.sugarroad2.model.dto.response.PostResponse;
import com.example.sugarroad2.model.entity.Post;
import com.example.sugarroad2.model.entity.PostImage;
import com.example.sugarroad2.service.PostImageService;
import com.example.sugarroad2.service.RecommendationService;
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

    @Autowired
    private RecommendationService recommendationService;
    public PostResponse convertToPostResponse(Post post) {
        List<String> postImage = new ArrayList<>();
        List<PostImage> postImageList = postImageService.readByPostId(post.getId());
        //postImage
        if (postImageList != null)
            postImageList.forEach(image -> {
                postImage.add(image.getPostImagePath());
            });
        long viewsCount = viewsService.count("p", post.getId());
        long recommendCount = recommendationService.countByReference("p", post.getId());
        PostResponse postResponse = new PostResponse(post, postImage, viewsCount, recommendCount);
        //조회수
        return postResponse;
    }

}


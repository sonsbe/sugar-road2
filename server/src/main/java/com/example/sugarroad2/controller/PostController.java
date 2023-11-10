package com.example.sugarroad2.controller;

import com.example.sugarroad2.model.dto.PostRequest;
import com.example.sugarroad2.model.dto.PostResponse;
import com.example.sugarroad2.model.entity.Post;
import com.example.sugarroad2.model.entity.PostCategory;
import com.example.sugarroad2.model.entity.PostImage;
import com.example.sugarroad2.model.entity.Users;
import com.example.sugarroad2.repository.PostCategoryRepository;
import com.example.sugarroad2.repository.UsersRepository;
import com.example.sugarroad2.service.PostImageService;
//import com.example.sugarroad2.service.PostService;
import com.example.sugarroad2.service.PostService;
import com.example.sugarroad2.util.ImageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/posts")
public class PostController {
    @Autowired
    private PostService postService;
    @Autowired
    private PostImageService postImageService;

    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    ImageUtil imageUtil;
    @Autowired
    private PostCategoryRepository postCategoryRepository;
    private PostResponse toResponse(Post post){
        List<String> postImage = new ArrayList<>();
        List<PostImage> postImageList = postImageService.findByPostId(post.getId());
        if(postImageList != null)
            postImageList.forEach(image->{postImage.add(image.getPostImagePath());});
        return new PostResponse(post, postImage);
}

    @GetMapping
    public ResponseEntity<List<PostResponse>> read(){
        List<Post> postList = postService.findAll();
        List<PostResponse> postResponseList = new ArrayList<>();
        for(Post post : postList){
            postResponseList.add(toResponse(post));
        }
        ResponseEntity<List<PostResponse>> entity = new ResponseEntity<>(postResponseList, HttpStatus.OK);

        return entity;
    }
    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> read(@PathVariable int id){
        Post post = postService.findById(id);
        PostResponse postResponse = toResponse(post);
        ResponseEntity<PostResponse> entity = new ResponseEntity<>(postResponse, HttpStatus.OK);

        return entity;
    }
    @GetMapping({"/user/{id}"})
    public ResponseEntity<List<PostResponse>> readByUser(@PathVariable String id) {
        List<Post> postList = postService.findByUser(id);
        List<PostResponse> postResponseList = new ArrayList<>();
        for (Post post : postList)
            postResponseList.add(toResponse(post));
        ResponseEntity<List<PostResponse>> entity = new ResponseEntity<>(postResponseList, HttpStatus.OK);

        return entity;
    }
    @PostMapping
    public ResponseEntity<String> register(PostRequest postRequest){
        System.out.println("form:"+postRequest);
        Users users = usersRepository.findById(postRequest.getUserId()).get();
        PostCategory postCategory = postCategoryRepository.findById(postRequest.getPostCategoryId()).get();
        postService.save(postRequest.toEntity(users, postCategory));
        //이미지 처리
//        List<String> imageList = postRequest.getPostImage();
//        for (MultipartFile mfile : postRequest.getUploadImages()) {
//            String postImagePath = imageUtil.writeImage(mfile);
//            PostImage postImage = PostImage.builder().postImagePath(postImagePath).build();
//            imageList.add(postImage);
//        }
        ResponseEntity<String> entity = new ResponseEntity<>(postRequest.getTitle(), HttpStatus.OK);
        return entity;
    }
    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable int id, PostRequest postRequest){
        System.out.println(postRequest);
        postService.update(id, postRequest);
        ResponseEntity<String> entity = new ResponseEntity<>("수정 완료", HttpStatus.OK);
        return entity;
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable int id){
       postService.delete(id);
       ResponseEntity<String> entity = new ResponseEntity<>("삭제 완료", HttpStatus.OK);
       return entity;

    }

}

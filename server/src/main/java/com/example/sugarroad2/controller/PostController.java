package com.example.sugarroad2.controller;

import com.example.sugarroad2.model.dto.request.PostRequest;
import com.example.sugarroad2.model.dto.response.PostResponse;
import com.example.sugarroad2.model.entity.*;
import com.example.sugarroad2.repository.PostCategoryRepository;
import com.example.sugarroad2.repository.UsersRepository;
import com.example.sugarroad2.service.PostImageService;
//import com.example.sugarroad2.service.PostService;
import com.example.sugarroad2.service.PostService;
import com.example.sugarroad2.service.ViewsService;
import com.example.sugarroad2.util.ImageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

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
    private ViewsService viewsService;
    @Autowired
    ImageUtil imageUtil;

    @Autowired
    private PostCategoryRepository postCategoryRepository;

    //entity->response
    private PostResponse convertToPostResponse(Post post) {
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

    private void saveImage(PostRequest postRequest, MultipartFile[] uploadImages, Post post) {
        List<String> postImage = new ArrayList<>();
        for (MultipartFile multipartFile : uploadImages) {
            String postImagePath = imageUtil.writeImage(multipartFile);
            postImage.add(postImagePath);
        }
        postRequest.setPostImage(postImage);
        List<PostImage> postImageList = postRequest.toPostImage(post);
        postImageService.saveAll(postImageList);
        log.info("이미지 저장 완료");
    }

    //    @GetMapping
//    public ResponseEntity<?> read(String col){
//        try {
//            List<Post> postList = postService.read(col);
//            List<PostResponse> postResponseList = new ArrayList<>();
//            for (Post post : postList) {
//                postResponseList.add(convertToPostResponse(post));
//            }
//            return ResponseEntity.ok().body(postResponseList);
//        } catch (Exception e){
//            String error = e.getMessage();
//            return ResponseEntity.badRequest().body(error);
//        }
//    }
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<PostResponse>>> read(String col) {
        List<Post> postList = postService.read(col);
        List<PostResponse> postResponseList = new ArrayList<>();
        List<EntityModel<PostResponse>> postResponseEntityModel = new ArrayList<>();
        for (Post post : postList) {
            //postResponseList.add(convertToPostResponse(post));
            postResponseEntityModel.
                    add(EntityModel.of(convertToPostResponse(post),
                            linkTo(methodOn(PostController.class).readById(post.getId()))
                                    .withSelfRel())
                            .add(linkTo(methodOn(PostController.class).delete(post.getId()))
                                    .withRel("delete"))
                            .add(linkTo(methodOn(PostController.class).update(post.getId(), null, null))
                                    .withRel("update"))
                    );
        }

        CollectionModel<EntityModel<PostResponse>> postResponseCollectionModel =
                CollectionModel.of(postResponseEntityModel)
                        .add(linkTo(methodOn(PostController.class).read(col))
                                .withSelfRel());

        return ResponseEntity.ok().body(postResponseCollectionModel);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> readById(@PathVariable int id) {
        try {
            Post post = postService.readById(id);
            PostResponse postResponse = convertToPostResponse(post);
            viewsService.create(Views.builder().referenceType("p").referenceId(id).build());
            ResponseEntity<PostResponse> entity = new ResponseEntity<>(postResponse, HttpStatus.OK);
            return ResponseEntity.ok().body(postResponse);
        } catch (Exception e) {
            String error = e.getMessage();
            return ResponseEntity.badRequest().body(error);
        }
    }

    @GetMapping({"/user/{id}"})
    public ResponseEntity<Object> readByUser(@PathVariable String id) {
        try {
            List<Post> postList = postService.readByUser(id);
            List<PostResponse> postResponseList = new ArrayList<>();
            for (Post post : postList)
                postResponseList.add(convertToPostResponse(post));
            return ResponseEntity.ok().body(postResponseList);
        } catch (Exception e) {
            String error = e.getMessage();
            return ResponseEntity.badRequest().body(error);
        }

    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<String> create(@RequestPart PostRequest postRequest, @RequestPart MultipartFile[]
            uploadImages) {
        try {
            System.out.println("form:" + postRequest);
            Users users = usersRepository.findById(postRequest.getUserId()).get();
            PostCategory postCategory = postCategoryRepository.findById(postRequest.getPostCategoryId()).get();
            Post post = postRequest.toEntity(users, postCategory);
            postService.create(post);
            //이미지 저장
            if (uploadImages != null) {
                saveImage(postRequest, uploadImages, post);
            }
            return ResponseEntity.created(URI.create("/posts/" + post.getId())).body("작성 완료");
        } catch (Exception e) {
            String error = e.getMessage();
            return ResponseEntity.badRequest().body(error);
        }
    }

    @PutMapping(value = "/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<String> update(@PathVariable int id, @RequestPart PostRequest postRequest,
                                         @RequestPart MultipartFile[] uploadImages) {
        try {
            Post post = postService.readById(id);
            //제목, 내용 수정
            post.setTitle(postRequest.getTitle());
            post.setContent(postRequest.getContent());
            //이미지 삭제
            if (postRequest.getPostImage() != null) {
                for (String postImagePath : postRequest.getPostImage()) {
                    PostImage postImage = postImageService.readByPostImagePath(postImagePath);
                    postImageService.delete(postImage);
                }
            }
            //이미지 저장
            if (uploadImages != null) {
                saveImage(postRequest, uploadImages, post);
            }
            System.out.println("포스트:" + post);
            postService.update(post);
            //ResponseEntity<String> entity = new ResponseEntity<>("수정 완료", HttpStatus.CREATED);
            return ResponseEntity.created(URI.create("/posts/" + id)).body("수정 완료");
        } catch (Exception e) {
            String error = e.getMessage();
            return ResponseEntity.badRequest().body(error);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        try {
            Post post = postService.readById(id);
            postService.delete(post);
            return ResponseEntity.ok().body("삭제 완료");
        } catch (Exception e) {
            String error = e.getMessage();
            return ResponseEntity.badRequest().body(error);
        }

    }

}

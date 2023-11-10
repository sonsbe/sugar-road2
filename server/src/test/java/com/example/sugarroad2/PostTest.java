package com.example.sugarroad2;

import com.example.sugarroad2.model.dto.PostRequest;
import com.example.sugarroad2.model.dto.PostResponse;
import com.example.sugarroad2.model.entity.*;
import com.example.sugarroad2.repository.PostCategoryRepository;
import com.example.sugarroad2.repository.PostImageRepository;
import com.example.sugarroad2.repository.PostRepository;
import com.example.sugarroad2.repository.UsersRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.ArrayList;
import java.util.List;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DataJpaTest
public class PostTest {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private PostImageRepository postImageRepository;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private PostCategoryRepository postCategoryRepository;

    @Test
    @Rollback(value = false)
    void save(){
        PostRequest postRequest = PostRequest.builder().content("내용23").title("제목23").userId("abcd1234").postCategoryId("01").build();
        Users users = usersRepository.findById(postRequest.getUserId()).get();
        PostCategory postCategory = postCategoryRepository.findById("01").get();
        postRepository.save(postRequest.toEntity(users, postCategory));
        List<Post> list = postRepository.findAll();
        List<PostImage> postImageList = postImageRepository.findByPost(list.get(0));
        list.forEach(System.out :: println);
        List<Post> postList = postRepository.findByUserId(postRequest.getUserId());
        postList.forEach(System.out :: println);
    }

    @Test
    void insert(){
        List<String> postImage = new ArrayList<>();
        postImage.add("123");
        postImage.add("456");
        PostRequest postRequest = PostRequest.builder().postImage(postImage).build();
        Post post = postRepository.findById(6).get();
        System.out.println(post);
        postImageRepository.saveAll(postRequest.toPostImage(post));
        postImageRepository.findAll().forEach(System.out::println);
    }
    @Test
    void findAll(){
//        List<Post> postList = postRepository.findAll();
//        List<PostResponse> postResponseList = new ArrayList<>();
//        postList.forEach(post -> {postResponseList.add(new PostResponse(post));});
//        postResponseList.forEach(System.out :: println);
    }
}

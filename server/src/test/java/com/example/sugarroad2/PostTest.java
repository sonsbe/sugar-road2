package com.example.sugarroad2;

import com.example.sugarroad2.model.dto.PostRequest;
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
    void findAll(){
        PostRequest postRequest = PostRequest.builder().content("내용123").title("제목123").userId("abcd1234").postCategoryId("01").build();
        Users users = usersRepository.findById(postRequest.getUserId()).get();
        System.out.println(users);
        PostCategory postCategory = postCategoryRepository.findById("01").get();
        System.out.println(postCategory);
        postRepository.save(postRequest.toEntity(users, postCategory));
        List<Post> list = postRepository.findAll();
        List<PostImage> postImageList = postImageRepository.findByPostId(list.get(0).getId());
        list.forEach(System.out :: println);
        List<Post> postList = postRepository.findByUser(users);
        postList.forEach(System.out :: println);
    }

    @Test
    void insert(){
        List<String> postImage = new ArrayList<>();

        postImage.add("123");
        postImage.add("456");
        PostRequest postRequest = PostRequest.builder().postImage(postImage).build();
        Post post = postRepository.findById(6).get();
        List<PostImage> postImageList = postRequest.toPostImage(post);
        postImageRepository.saveAll(postImageList);
//        List<PostImage> pl = postImageRepository.findAll();
//        pl.forEach(System.out :: println);
    }
}

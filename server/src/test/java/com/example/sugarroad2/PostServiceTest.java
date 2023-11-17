package com.example.sugarroad2;

import com.example.sugarroad2.model.dto.PostRequest;
import com.example.sugarroad2.model.dto.PostResponse;
import com.example.sugarroad2.model.entity.Post;
import com.example.sugarroad2.model.entity.PostCategory;
import com.example.sugarroad2.model.entity.Users;
import com.example.sugarroad2.repository.PostCategoryRepository;
import com.example.sugarroad2.repository.UsersRepository;
import com.example.sugarroad2.service.PostService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

@SpringBootTest//서비스 레이어 유닛테스트
public class PostServiceTest {


    @Autowired
    private PostService postService;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private PostCategoryRepository postCategoryRepository;

    @Test
    void save() {
        PostRequest postRequest = PostRequest.builder()
                .content("테스트내용").title("테스트제목")
                .userId("abcd1234").postCategoryId("01").build();
        Users users = usersRepository.findById("abcd1234").get();
        PostCategory postCategory = postCategoryRepository.findById("01").get();
        postService.create(postRequest.toEntity(users, postCategory));
        postService.read("userId").forEach(System.out :: println);
    }

    @Test
    void findAll() {
        List<Post> postList = postService.read("postedDate");
        postList.forEach(System.out :: println);
        List<PostResponse> postResponseList = new ArrayList<>();
//        postList.forEach(post -> {postResponseList.add(new PostResponse(post));});
//        postResponseList.forEach(System.out :: println);
    }

    @Test
    void findById() {
        System.out.println(postService.readById(6));
    }

    @Test
    void delete() {
//        postService.read().forEach(System.out :: println);
//        PostResponse postResponse =PostResponse.builder().id(8).build();
//        int id = postResponse.getId();
//        postService.delete(id);
//        postService.findAll().forEach(System.out :: println);


    }

    @Test
    void update() {
        PostRequest postRequest = PostRequest.builder().content("내용_수정").title("제목_수정").postCategoryId("01").build();

//        postService.update(6, postRequest);

//        System.out.println(postService.readById(6));
    }
}
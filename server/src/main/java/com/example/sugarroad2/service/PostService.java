package com.example.sugarroad2.service;

import com.example.sugarroad2.model.dto.PostRequest;
import com.example.sugarroad2.model.entity.Post;
import com.example.sugarroad2.model.entity.PostImage;
import com.example.sugarroad2.model.entity.Users;
import com.example.sugarroad2.repository.PostCategoryRepository;
import com.example.sugarroad2.repository.PostImageRepository;
import com.example.sugarroad2.repository.PostRepository;
import com.example.sugarroad2.repository.UsersRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private PostCategoryRepository postCategoryRepository;
    @Autowired
    private UsersRepository usersRepository;
    public boolean save(Post post){
        try {
            postRepository.save(post);
            return true;
        } catch (Exception e){
            return false;
        }


    }
    public List<Post> findAll(){
        return postRepository.findAll();
    }
    public Post findById(int id){
        return postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + id));
    }
    public List<Post> findByUser(String id){
        List<Post> postList = postRepository.findByUserId(id);
        return postList;
    }
    public boolean delete(int id){
        try {
            postRepository.deleteById(id);
            return true;
        } catch (Exception e){
            return false;
        }

    }
    @Transactional
    public void update(int id, PostRequest postRequest){//이미지 관련 처리
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + id));
        post.setContent(postRequest.getContent());
        post.setTitle(postRequest.getTitle());
        post.setPostCategory(postCategoryRepository.findById(postRequest.getPostCategoryId()).get());
    }

}

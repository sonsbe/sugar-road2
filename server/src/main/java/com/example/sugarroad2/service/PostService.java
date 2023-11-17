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
import org.springframework.data.domain.Sort;
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
    public void create(Post post){
            postRepository.save(post);

    }
    public List<Post> read(String col){
        if(col != null)
            return  postRepository.findAll(Sort.by(Sort.Direction.DESC, col));
        else
            return postRepository.findAll();

    }
    public Post readById(int id){
        return postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + id));
    }
    public List<Post> readByUser(String id){
        List<Post> postList = postRepository.findByUserId(id);
        return postList;
    }

    public void delete(Post post){
            postRepository.delete(post);
    }
    public List<Post> readByTitleOrContent(String query){
        return postRepository.findByContentContainsOrTitleContains(query, query);

    }
//    @Transactional
//    public void update(int id, PostRequest postRequest){//이미지 관련 처리
//        Post post = postRepository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("not found : " + id));
//        post.setContent(postRequest.getContent());
//        post.setTitle(postRequest.getTitle());
//        post.setPostCategory(postCategoryRepository.findById(postRequest.getPostCategoryId()).get());
//    }
    public void update(Post post){
            postRepository.save(post);
    }

}

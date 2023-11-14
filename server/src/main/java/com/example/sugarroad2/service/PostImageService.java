package com.example.sugarroad2.service;

import com.example.sugarroad2.model.entity.Post;
import com.example.sugarroad2.model.entity.PostImage;
import com.example.sugarroad2.repository.PostImageRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PostImageService {
    @Autowired
    private PostImageRepository postImageRepository;

    public void create(PostImage postImage){
        postImageRepository.save(postImage);
    }
    public void saveAll(List<PostImage> postImageList){
        postImageRepository.saveAll(postImageList);
    }
    public List<PostImage> readByPostId(int id){
        return postImageRepository.findByPostId(id);
    }
    public PostImage readByPostImagePath(String postImagePath){
        return postImageRepository.findByPostImagePath(postImagePath);
    }
    public void delete(PostImage postImage){
        postImageRepository.delete(postImage);
    }
}

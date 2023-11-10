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

    public void save(PostImage postImage){
        postImageRepository.save(postImage);
    }
    public void saveAll(List<PostImage> postImageList){
        postImageRepository.saveAll(postImageList);
    }
    public List<PostImage> findByPostId(int id){
        return postImageRepository.findByPostId(id);
    }
    public void delete(int id){
        postImageRepository.deleteById(id);
    }
}

package com.example.sugarroad2.service;

import com.example.sugarroad2.model.entity.Post;
import com.example.sugarroad2.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SearchService {
    @Autowired
    private PostRepository postRepository;

    private List<Post> readPost(String query){
        List<Post> postList = postRepository.findByContentContainsOrTitleContains(query, query);
        return postList;
    }
}

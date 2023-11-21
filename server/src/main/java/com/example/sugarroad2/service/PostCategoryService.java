package com.example.sugarroad2.service;

import com.example.sugarroad2.model.entity.PostCategory;
import com.example.sugarroad2.repository.PostCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PostCategoryService {
    @Autowired
    private PostCategoryRepository postCategoryRepository;

    public PostCategory readById(String id){
        return postCategoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + id));
    }
}

package com.example.sugarroad2.service;

import com.example.sugarroad2.model.entity.Views;
import com.example.sugarroad2.repository.ViewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ViewsService {
    @Autowired
    ViewsRepository viewsRepository;

    public boolean create(Views views){
        try {
            viewsRepository.save(views);
            return  true;
        } catch (Exception e){
            return false;
        }
    }
    public long count(String referenceType, int referenceId){
        long viewsCount = viewsRepository.countByReferenceTypeAndReferenceId(referenceType, referenceId);
        return viewsCount;
    }
}

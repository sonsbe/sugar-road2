package com.example.sugarroad2.service;


import com.example.sugarroad2.model.dto.StoreDTO;
import com.example.sugarroad2.model.entity.Store;
import com.example.sugarroad2.repository.StoreRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class StoreService {
    @Autowired
    StoreRepository storeRepository;
    public List<Store> read() {
        List<Store> storeList = storeRepository.findAll();
        return storeList;
    }
    public Store readOne(int storeId) {
        Optional<Store> optional = storeRepository.findById(storeId);
        return optional.get();
    }
    public Store create(Store store) {
        return storeRepository.save(store);
    }

    public String update(Store store) {
        try {
            storeRepository.save(store);
            return "success";
        } catch (Exception e) {
            return "fail";
        }
    }
    public String delete(int storeId) {
        try {
            storeRepository.deleteById(storeId);
            return "success";
        } catch (Exception e) {
            return "fail";
        }
    }
}

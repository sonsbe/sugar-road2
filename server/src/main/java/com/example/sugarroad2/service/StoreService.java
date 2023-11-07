package com.example.sugarroad2.service;

import com.example.sugarroad2.model.entity.Store;
import com.example.sugarroad2.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreService {
    @Autowired
    StoreRepository storeRepository;

    public List<Store> read(){
        List<Store> storeList = storeRepository.findAll();
        return storeList;
    }
    public String create(Store store){
        try{
            storeRepository.save(store);
            return "success";
        }catch (Exception e){
            return "fail";
        }
    }
    public String delete(int storeId){
        try{
            storeRepository.deleteById(storeId);
            return "success";
        }catch (Exception e){
            return "fail";
        }
    }
}

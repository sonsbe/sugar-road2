package com.example.sugarroad2.service;


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
    public Store readBy(int storeId) {
        Optional<Store> optional = storeRepository.findById(storeId);
        return optional.get();
    }
    public Store create(Store store) {
        return storeRepository.save(store);
    }

    public Store update(Store store) {
        return storeRepository.save(store);
    }
    public void delete(int storeId) {
        storeRepository.deleteById(storeId);
    }

}

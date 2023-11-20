package com.example.sugarroad2.service;


import com.example.sugarroad2.model.entity.Menu;
import com.example.sugarroad2.model.entity.Store;
import com.example.sugarroad2.repository.MenuRepository;
import com.example.sugarroad2.repository.StoreRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
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
    @Autowired
    MenuRepository menuRepository;

    public List<Store> read() {
        List<Store> storeList = storeRepository.findAll();
        return storeList;
    }

    public Store readBy(int storeId) {
        Optional<Store> optional = storeRepository.findById(storeId);
        return optional.get();
    }

    public List<Store> readByStoreName(String storeName) {
        return storeRepository.findByStoreNameContains(storeName);
    }

    public Store create(Store store) {
        return storeRepository.save(store);
    }

    public Store update(Store store) {
        return storeRepository.save(store);
    }

    @Transactional
    public void delete(int storeId) {
        System.out.println("삭제 서비스,, 가게 삭제");
        // 가게 레코드 삭제
        Store store = storeRepository.findById(storeId).orElseThrow(()-> new EntityNotFoundException("가게가 없습니다."));
        storeRepository.deleteById(storeId);
    }
}

//    public void delete(Store store) {
//        storeRepository.delete(store);
//    }
//}

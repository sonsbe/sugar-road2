package com.example.sugarroad2.repository;

import com.example.sugarroad2.model.entity.Menu;
import com.example.sugarroad2.model.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreRepository extends JpaRepository<Store, Integer> {
    public List<Store> findByStoreNameContains(String storeName);
    public List<Store>  findAllByOrderByIdDesc();
}

package com.example.sugarroad2;

import com.example.sugarroad2.model.entity.Store;
import com.example.sugarroad2.repository.StoreRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DataJpaTest
public class StoreTest {
    @Autowired
    StoreRepository storeR;

//    @Test
//    @Transactional
//    void list() {
//        List<Store> list = storeR.findAll();
//        list.parallelStream().forEach(System.out::println);
//    }
    @Test
    @Rollback(value = false)
    void insert(){
        Store s1 = Store.builder().id(2).storeName("테스트2").address("테스트주소2").phoneNumber("0000").storeDesc("테스트설명").build();
        storeR.save(s1);
        List<Store> list = storeR.findAll();
        list.parallelStream().forEach(System.out::println);
    }
}

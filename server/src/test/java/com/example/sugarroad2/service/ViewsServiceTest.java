package com.example.sugarroad2.service;

import com.example.sugarroad2.model.entity.Views;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
class ViewsServiceTest {

    @Autowired
    ViewsService viewsService;
    @Test
    void create() {
        Views views = Views.builder().referenceType("p").referenceId(17).build();
        viewsService.create(views);
        Views views2 = Views.builder().referenceType("p").referenceId(17).build();
        viewsService.create(views2);
        long count = viewsService.count("p", 17);
        System.out.println("count:"+count);
    }

    @Test
    void count() {
    }
}
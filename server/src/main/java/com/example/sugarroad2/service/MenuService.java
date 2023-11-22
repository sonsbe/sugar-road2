package com.example.sugarroad2.service;

import com.example.sugarroad2.model.entity.Menu;
import com.example.sugarroad2.model.entity.Store;
import com.example.sugarroad2.repository.MenuRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class MenuService {
    @Autowired
    MenuRepository menuRepository;

    public List<Menu> findByStore(Store store) {
        return menuRepository.findByStore(store);
    }

    public List<Menu> read() {
        return menuRepository.findAll();
    }

    public Menu readBy(int menuId)
    {
        return menuRepository.findById(menuId).get();
    }

    public Menu create(Menu menu) {
        return menuRepository.save(menu);
    }
    public Menu update(Menu menu) {
        return menuRepository.save(menu);
    }
    public  void delete(int storeId){
        // 해당 가게의 메뉴 레코드를 먼저 삭제
        menuRepository.deleteByStoreId(storeId);
        System.out.println("메뉴삭제하고 가게 삭제하기");
    }
}

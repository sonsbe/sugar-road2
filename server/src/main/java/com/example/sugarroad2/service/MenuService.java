package com.example.sugarroad2.service;

import com.example.sugarroad2.model.entity.Menu;
import com.example.sugarroad2.model.entity.Store;
import com.example.sugarroad2.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuService {
    @Autowired
    MenuRepository menuRepository;
    public List<Menu> findByStore(Store store){
        return menuRepository.findByStore(store);
    }
    public List<Menu> read(){
        return menuRepository.findAll();
    }
}

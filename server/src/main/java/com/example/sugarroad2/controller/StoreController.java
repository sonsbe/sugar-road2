package com.example.sugarroad2.controller;

import com.example.sugarroad2.model.dto.*;
import com.example.sugarroad2.model.entity.Menu;
import com.example.sugarroad2.model.entity.Store;
import com.example.sugarroad2.service.MenuService;
import com.example.sugarroad2.service.StoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/store")
@Slf4j
public class StoreController {
    @Autowired
    StoreService storeService;
    @Autowired
    MenuService menuService;

    //    전체 리스트 읽어오기
    @GetMapping
    public ResponseEntity<List<StoreResponse>> read() {
        // 좋아요 카운트, 리뷰 수, 이미지 유틸 추가
        //  List<StoreDTO> dtoList = storeService.read().stream().map(StoreDTO::new).toList();
        List<Store> storeList = storeService.read();
        List<MenuResponse> menuResponseList = menuService.read().stream().map(MenuResponse::new).toList();
        List<StoreResponse> dtoList = new ArrayList<>();
        for (Store store : storeList) {
            List<MenuResponse> storeMenus = menuResponseList.stream().filter(MenuResponse -> MenuResponse.getStoreId() == store.getId()).collect(Collectors.toList());
            StoreResponse storeResponse = new StoreResponse(store, storeMenus);
            dtoList.add(storeResponse);
        }
        return ResponseEntity.status(HttpStatus.OK).body(dtoList);
    }
    // 가게 1개 읽어오기
    @GetMapping("/{storeId}")
    public ResponseEntity<StoreResponse> readOne(@PathVariable int storeId) {
        Store store = storeService.readOne(storeId);
        List<MenuResponse> menuResponseList = menuService.findByStore(store).stream().map(MenuResponse::new).toList();
        StoreResponse storeResponse = new StoreResponse(store, menuResponseList);
        return ResponseEntity.status(HttpStatus.OK).body(storeResponse);
    }
    // 가게 등록
    @PostMapping
    public ResponseEntity<String> create(StoreRequest storeRequest, List<MenuRequest> menuRequestList) {
        System.out.println("storeRequest:"+storeRequest);
        System.out.println("menuRequestList"+menuRequestList);
        Store store = storeRequest.toEntity();
        String result = storeService.create(store);
        for (int i =0; i<menuRequestList.size(); i++){
            Menu menu = menuRequestList.get(i).toEntity(store);
            String result1 = menuService.create(menu);
        }
        if (result.equals("success")) return ResponseEntity.status(HttpStatus.CREATED).body("가게 저장완료");
        else return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("가게 저장 실패");
    }
    // 가게 삭제
    @DeleteMapping("/{storeId}")
    public ResponseEntity<String> delete(@PathVariable("storeId") int storeId) {
        String result = storeService.delete(storeId);
        if (result.equals("success")) return ResponseEntity.status(HttpStatus.OK).body("가게 삭제완료");
        else return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("가게 삭제 실패");
    }
}
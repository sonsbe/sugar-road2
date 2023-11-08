package com.example.sugarroad2.controller;

import com.example.sugarroad2.model.dto.MenuDTO;
import com.example.sugarroad2.model.dto.StoreDTO;
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

    @GetMapping
    public ResponseEntity<List<StoreDTO>> read() {
        // 좋아요 카운트, 리뷰 수, 이미지 유틸 추가
//        List<StoreDTO> dtoList = storeService.read().stream().map(StoreDTO::new).toList();

        List<Store> storeList = storeService.read();
        List<MenuDTO> menuDTOList = menuService.read().stream().map(MenuDTO::new).toList();
        List<StoreDTO> dtoList = new ArrayList<>();
        for (Store store : storeList) {
            List<MenuDTO> storeMenus = menuDTOList.stream().filter(menuDTO -> menuDTO.getStoreId() == store.getId()).collect(Collectors.toList());
            StoreDTO storeDTO = new StoreDTO(store, storeMenus);
            dtoList.add(storeDTO);
        }
        return ResponseEntity.status(HttpStatus.OK).body(dtoList);
    }

    @GetMapping("/{storeId}")
    public ResponseEntity<StoreDTO> readOne(@PathVariable int storeId) {
        Store store = storeService.readOne(storeId);
        List<MenuDTO> menuDTOList = menuService.findByStore(store).stream().map(MenuDTO::new).toList();
        StoreDTO storeDTO = new StoreDTO(store, menuDTOList);
        return ResponseEntity.status(HttpStatus.OK).body(storeDTO);
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody StoreDTO dto) {
        Store store = dto.toEntity();
        String result = storeService.create(store);
        if (result.equals("success")) return ResponseEntity.status(HttpStatus.CREATED).body("가게 저장완료");
        else return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("가게 저장 실패");
    }

    //    @PutMapping
//    public ResponseEntity<String> update(StoreDTO dto){
//
//    }
    @DeleteMapping("/{storeId}")
    public ResponseEntity<String> delete(@PathVariable("storeId") int storeId) {
        String result = storeService.delete(storeId);
        if (result.equals("success")) return ResponseEntity.status(HttpStatus.OK).body("가게 삭제완료");
        else return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("가게 삭제 실패");
    }
}
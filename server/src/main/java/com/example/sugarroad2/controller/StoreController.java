package com.example.sugarroad2.controller;

import com.example.sugarroad2.model.dto.StoreDTO;
import com.example.sugarroad2.model.entity.Store;
import com.example.sugarroad2.service.StoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/store")
@Slf4j
public class StoreController {
    @Autowired
    StoreService storeService;

    @GetMapping
    public ResponseEntity<List<StoreDTO>> read() {
        List<StoreDTO> dtoList = storeService.read().stream().map(StoreDTO::new).toList();
        return ResponseEntity.status(HttpStatus.OK).body(dtoList);
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
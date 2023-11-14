package com.example.sugarroad2.controller;

import com.example.sugarroad2.model.dto.*;
import com.example.sugarroad2.model.entity.Menu;
import com.example.sugarroad2.model.entity.Store;
import com.example.sugarroad2.service.MenuService;
import com.example.sugarroad2.service.StoreService;
import com.example.sugarroad2.util.ImageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/store")
@Slf4j
public class StoreController {
    @Autowired
    StoreService storeService;
    @Autowired
    MenuService menuService;
    @Autowired
    ImageUtil imageUtil;
    //    전체 리스트 읽어오기
    @GetMapping
    public ResponseEntity<List<StoreResponseDTO>> read() {
        // 좋아요 카운트, 리뷰 수, 이미지 유틸 추가
        //  List<StoreDTO> dtoList = storeService.read().stream().map(StoreDTO::new).toList();
        List<Store> storeList = storeService.read();
        List<MenuResponseDTO> menuResponseDTOList = menuService.read().stream().map(MenuResponseDTO::new).toList();
        List<StoreResponseDTO> dtoList = new ArrayList<>();
        for (Store store : storeList) {
            List<MenuResponseDTO> storeMenus = menuResponseDTOList.stream().filter(MenuResponseDTO -> MenuResponseDTO.getStoreId() == store.getId()).collect(Collectors.toList());
            StoreResponseDTO storeResponseDTO = new StoreResponseDTO(store, storeMenus);
            dtoList.add(storeResponseDTO);
        }
        return ResponseEntity.status(HttpStatus.OK).body(dtoList);
    }
    // 가게 1개 읽어오기
    @GetMapping("/{storeId}")
    public ResponseEntity<StoreResponseDTO> readBy(@PathVariable int storeId) {
        Store store = storeService.readOne(storeId);
        List<MenuResponseDTO> menuResponseDTOList = menuService.findByStore(store).stream().map(MenuResponseDTO::new).toList();
        StoreResponseDTO storeResponseDTO = new StoreResponseDTO(store, menuResponseDTOList);
        return ResponseEntity.status(HttpStatus.OK).body(storeResponseDTO);
    }
    // 가게 생성
    @PostMapping
    public ResponseEntity<?> create(@RequestBody StoreCreateRequestDTO storeCreateRequestDTO) {
        String menuImagePath = "";
       try{
           StoreRequestDTO storeRequestDTO = storeCreateRequestDTO.getStoreRequestDTO();
           String storeImgPath = imageUtil.writeImage(storeRequestDTO.getStoreImagePath());
           Store store = storeRequestDTO.toEntity(storeImgPath);
          Store createStore = storeService.create(store);
           List<MenuRequestDTO> menuRequestDTOList = storeCreateRequestDTO.getMenuRequestListDTO();
           List<MenuResponseDTO> menuResponseDTOList = new ArrayList<>();
           if (menuRequestDTOList.isEmpty()) {
               log.info("등록하는 메뉴가 없습니다");
           } else {
               for(MenuRequestDTO menuRequestDTO : menuRequestDTOList){
                   menuImagePath = imageUtil.writeImage(menuRequestDTO.getMenuImagePath());
                   Menu menu = menuRequestDTO.toEntity(store, menuImagePath);
                   menuResponseDTOList.add(new MenuResponseDTO(menuService.create(menu)));
               }
           }
           StoreResponseDTO storeResponse = new StoreResponseDTO(createStore, menuResponseDTOList);
           return ResponseEntity.status(HttpStatus.CREATED).body("가게 저장완료");
       }catch(Exception exception){
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
       }
    }
    // 가게 수정
    @PutMapping
    public ResponseEntity<?> update(@RequestBody StoreCreateRequestDTO storeCreateRequestDTO) {
        String menuImagePath = "";
        List<MenuResponseDTO> menuResponseDTOList = new ArrayList<>();
        try{
            StoreRequestDTO storeRequestDTO = storeCreateRequestDTO.getStoreRequestDTO();
            String storeImgPath = imageUtil.writeImage(storeRequestDTO.getStoreImagePath());
            Store store = storeRequestDTO.toEntity(storeImgPath);
            Store updateStore = storeService.update(store);
            List<MenuRequestDTO> menuRequestDTOList = storeCreateRequestDTO.getMenuRequestListDTO();
            if (menuRequestDTOList.size() == 0) {
                log.info("수정하는 메뉴가 없습니다");
            } else {
                for(MenuRequestDTO menuRequestDTO: menuRequestDTOList) {
                    menuImagePath = imageUtil.writeImage(menuRequestDTO.getMenuImagePath());
                    Menu menu = menuRequestDTO.toEntity(store, menuImagePath);
                    menuResponseDTOList.add(new MenuResponseDTO(menuService.update(menu)));
                }
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(new StoreResponseDTO(updateStore, menuResponseDTOList));
        }catch (Exception exception){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    // 가게 삭제
    @DeleteMapping("/{storeId}")
    public ResponseEntity<?> delete(@PathVariable("storeId") int storeId) {
       try{
           storeService.delete(storeId);
           return ResponseEntity.status(HttpStatus.OK).build();
       }catch (Exception exception){
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
       }
    }
}
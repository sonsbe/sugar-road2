package com.example.sugarroad2.controller;

import com.example.sugarroad2.config.auth.NowUserDetails;
import com.example.sugarroad2.model.dto.request.MenuRequestDTO;
import com.example.sugarroad2.model.dto.request.StoreCreateRequestDTO;
import com.example.sugarroad2.model.dto.request.StoreRequestDTO;
import com.example.sugarroad2.model.dto.response.MenuResponseDTO;
import com.example.sugarroad2.model.dto.response.StoreResponseDTO;
import com.example.sugarroad2.model.entity.Menu;
import com.example.sugarroad2.model.entity.Store;
import com.example.sugarroad2.service.MenuService;
import com.example.sugarroad2.service.StoreService;
import com.example.sugarroad2.util.ImageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

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

    public Store storeRequestToEntity(StoreRequestDTO storeRequestDTO) {
        Store store = null;
        if (storeRequestDTO.getStoreImagePath() != null) {
            String storeImgPath = imageUtil.writeImage(storeRequestDTO.getStoreImagePath());
            store = storeRequestDTO.toEntity(storeImgPath);
        } else {
            store = storeRequestDTO.toEntity("");
        }
        return store;
    }

    //    전체 리스트 읽어오기
    @GetMapping
    public ResponseEntity<List<StoreResponseDTO>> read() {
        // 좋아요 카운트, 리뷰 수 추가
        //  List<StoreDTO> dtoList = storeService.read().stream().map(StoreDTO::new).toList();
        List<Store> storeList = storeService.read();
        List<MenuResponseDTO> menuResponseDTOList = menuService.read().stream().map(MenuResponseDTO::new).toList();
        List<StoreResponseDTO> storeResponseDTOList = new ArrayList<>();
        for (Store store : storeList) {
            List<MenuResponseDTO> storeMenus = menuResponseDTOList.stream().filter(MenuResponseDTO -> MenuResponseDTO.getStoreId() == store.getId()).collect(Collectors.toList());
            storeResponseDTOList.add(new StoreResponseDTO(store, storeMenus));
        }
        return ResponseEntity.status(HttpStatus.OK).body(storeResponseDTOList);
    }

    // 가게 1개 읽어오기
    @GetMapping("/{storeId}")
    public ResponseEntity<StoreResponseDTO> readBy(@PathVariable int storeId) {
        Store store = storeService.readBy(storeId);
        List<MenuResponseDTO> menuResponseDTOList = menuService.findByStore(store).stream().map(MenuResponseDTO::new).toList();
        return ResponseEntity.status(HttpStatus.OK).body(new StoreResponseDTO(store, menuResponseDTOList));
    }

    // 가게 생성
    @PostMapping
    // 필요한게 user_id
   // @AuthenticationPrincipal NowUserDetails nowUserDetails
    public ResponseEntity<?> create(@RequestBody StoreCreateRequestDTO storeCreateRequestDTO) {
        try {
            log.info("storeCreateRequestDTO" + storeCreateRequestDTO);
            String menuImagePath = "";
            List<MenuResponseDTO> menuResponseDTOList = new ArrayList<>();
            Store store = storeRequestToEntity(storeCreateRequestDTO.getStoreRequestDTO());

            Store createStore = storeService.create(store);
            List<MenuRequestDTO> menuRequestDTOList = storeCreateRequestDTO.getMenuRequestListDTO();
            log.info("등록하는 메뉴 " + menuRequestDTOList.size());
            if (menuRequestDTOList.isEmpty()) {
                log.info("등록하는 메뉴가 없습니다");
            } else {
                log.info("등록하는 메뉴 " + menuRequestDTOList.size());
                Menu menu = null;
                for (MenuRequestDTO menuRequestDTO : menuRequestDTOList) {
                    log.info("메뉴 등록중");
                    if (menuRequestDTO.getMenuImagePath() != null) {
                        menuImagePath = imageUtil.writeImage(menuRequestDTO.getMenuImagePath());
                        menu = menuRequestDTO.toEntity(store, menuImagePath);
                    } else {
                        log.info("이미지 없는 메뉴");
                        log.info("*****menuRequestDTO:" + menuRequestDTO);
                        menu = menuRequestDTO.toEntity(store, "");
                        System.out.println("menu: " + menu);
                    }
                    menuResponseDTOList.add(new MenuResponseDTO(menuService.create(menu)));
                }
            }
            StoreResponseDTO storeResponse = new StoreResponseDTO(createStore, menuResponseDTOList);
            return ResponseEntity.status(HttpStatus.CREATED).body(storeResponse);
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
    }

    // 가게 수정
    @PutMapping("/{storeId}")
    public ResponseEntity<?> update(@PathVariable int storeId, @RequestBody StoreCreateRequestDTO storeCreateRequestDTO) {
        try {
            String menuImagePath = "";
            List<MenuResponseDTO> menuResponseDTOList = new ArrayList<>();
//          Store store = Store.builder() build();
            StoreRequestDTO storeRequestDTO = storeCreateRequestDTO.getStoreRequestDTO();
            Store store = storeRequestToEntity(storeRequestDTO);
            store.setId(storeService.readBy(storeId).getId());
            store.setStoreName(storeRequestDTO.getStoreName());
            store.setAddress(storeRequestDTO.getAddress());
            store.setPhoneNumber(storeRequestDTO.getPhoneNumber());
            store.setStoreDesc(storeRequestDTO.getStoreDesc());
            Store updateStore = storeService.update(store);
            List<MenuRequestDTO> menuRequestDTOList = storeCreateRequestDTO.getMenuRequestListDTO();
            if (menuRequestDTOList.isEmpty()) {
                log.info("수정하는 메뉴가 없습니다");
            } else {
                for (MenuRequestDTO menuRequestDTO : menuRequestDTOList) {
                    menuImagePath = imageUtil.writeImage(menuRequestDTO.getMenuImagePath());
                    Menu menu = menuRequestDTO.toEntity(store, menuImagePath);
                    menuResponseDTOList.add(new MenuResponseDTO(menuService.update(menu)));
                }
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(new StoreResponseDTO(updateStore, menuResponseDTOList));
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // 가게 삭제
    @DeleteMapping("/{storeId}")
    public ResponseEntity<?> delete(@PathVariable("storeId") int storeId) {
        try {
            storeService.delete(storeId);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
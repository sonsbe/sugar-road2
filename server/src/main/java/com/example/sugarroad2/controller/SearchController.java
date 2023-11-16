package com.example.sugarroad2.controller;

import com.example.sugarroad2.model.dto.MenuResponseDTO;
import com.example.sugarroad2.model.dto.PostResponse;
import com.example.sugarroad2.model.dto.StoreResponseDTO;
import com.example.sugarroad2.model.entity.Post;
import com.example.sugarroad2.model.entity.Store;
import com.example.sugarroad2.service.MenuService;
import com.example.sugarroad2.service.PostService;
import com.example.sugarroad2.service.StoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/search")
public class SearchController {
    @Autowired
    private PostService postService;
    @Autowired
    private StoreService storeService;
    @Autowired
    private MenuService menuService;
    @GetMapping
    public ResponseEntity<Object> read(String query){
        List<PostResponse> postResponseList = postService.readByTitleOrContent(query)
                .stream().map( post -> {return new PostResponse(post, null, 0);})
                .toList();
        List<StoreResponseDTO> storeResponseDTOList = storeService.readByStoreName(query)
                .stream().map( store -> {return new StoreResponseDTO(store, null);})
                .toList();
        List<Object> objectList = new ArrayList<>();
        objectList.add(postResponseList);
        objectList.add(storeResponseDTOList);
        return ResponseEntity.ok(objectList);
        //List<Store> storeList = storeService.search(query);

    }
}

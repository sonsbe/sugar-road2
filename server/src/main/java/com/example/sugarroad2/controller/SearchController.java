package com.example.sugarroad2.controller;

import com.example.sugarroad2.model.dto.MenuResponseDTO;
import com.example.sugarroad2.model.dto.PostResponse;
import com.example.sugarroad2.model.dto.StoreResponseDTO;
import com.example.sugarroad2.model.entity.Post;
import com.example.sugarroad2.model.entity.Store;
import com.example.sugarroad2.service.MenuService;
import com.example.sugarroad2.service.PostService;
import com.example.sugarroad2.service.StoreService;
import com.example.sugarroad2.util.ConvertionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    @Autowired
    private ConvertionUtil convertionUtil;
    @GetMapping
    public ResponseEntity<Map<String, List<?>>> read(String query){
        List<PostResponse> postResponseList = postService.readByTitleOrContent(query)
                .stream().map( post -> { return convertionUtil.convertToPostResponse(post);})
                .toList();
        List<StoreResponseDTO> storeResponseDTOList = storeService.readByStoreName(query)
                .stream().map( store -> {return new StoreResponseDTO(store, null);})
                .toList();
        Map<String, List<?>> listMap = new HashMap<>();
        listMap.put("postList", postResponseList);
        listMap.put("storeList", storeResponseDTOList);
        return ResponseEntity.ok(listMap);
        //List<Store> storeList = storeService.search(query);

    }
}

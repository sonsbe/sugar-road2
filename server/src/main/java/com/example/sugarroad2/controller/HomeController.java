package com.example.sugarroad2.controller;

import com.example.sugarroad2.model.dto.PostResponse;
import com.example.sugarroad2.model.dto.StoreResponseDTO;
import com.example.sugarroad2.service.MenuService;
import com.example.sugarroad2.service.PostService;
import com.example.sugarroad2.service.StoreService;
import com.example.sugarroad2.service.ViewsService;
import com.example.sugarroad2.util.ConvertionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin("*")
@Slf4j
@RestController
@RequestMapping("/home")
public class HomeController {
    @Autowired
    private PostService postService;
    @Autowired
    private StoreService storeService;
    @Autowired
    private MenuService menuService;
    @Autowired
    private ConvertionUtil convertionUtil;

    @GetMapping
    public ResponseEntity<Map<String, List<?>>> read() {
        List<PostResponse> postResponseList = postService.readTop5ByOrderByPostedDateDesc()
                .stream().map(post -> {
                    return convertionUtil.convertToPostResponse(post);
                })
                .toList();
        List<StoreResponseDTO> storeResponseDTOList = storeService.read()
                .stream().map(store -> {
                    return new StoreResponseDTO(store, null);
                })
                .toList();
        Map<String, List<?>> listMap = new HashMap<>();
        listMap.put("postList", postResponseList);
        listMap.put("storeList", storeResponseDTOList);
        return ResponseEntity.ok(listMap);
    }
}

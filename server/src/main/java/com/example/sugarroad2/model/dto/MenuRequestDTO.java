package com.example.sugarroad2.model.dto;

import com.example.sugarroad2.model.entity.Menu;
import com.example.sugarroad2.model.entity.Store;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MenuRequestDTO {
    private String menuName;
    private int price;
    private String menuDesc;
    private int storeId;
    private MultipartFile menuImagePath;
    public Menu toEntity(Store store, String menuImagePath) {
       return Menu.builder()
                .menuName(menuName)
                .price(price)
                .menuDesc(menuDesc)
                .store(store)
                .menuImagePath(menuImagePath)
                .build();
    }
}

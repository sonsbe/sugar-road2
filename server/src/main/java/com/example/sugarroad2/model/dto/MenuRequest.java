package com.example.sugarroad2.model.dto;

import com.example.sugarroad2.model.entity.Menu;
import com.example.sugarroad2.model.entity.Store;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
//@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
//@ToString
public class MenuRequest {
    private String menuName;
    private int price;
    private String menuDesc;
    private int storeId;
    private String menuImagePath;
    public Menu toEntity(Store store) {
        Menu menu = Menu.builder()
                .menuName(menuName)
                .price(price)
                .menuDesc(menuDesc)
                .store(store)
                .menuImagePath(menuImagePath)
                .build();
        return menu;
    }
}

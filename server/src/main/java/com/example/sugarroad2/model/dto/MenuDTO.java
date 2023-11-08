package com.example.sugarroad2.model.dto;

import com.example.sugarroad2.model.entity.Menu;
import com.example.sugarroad2.model.entity.Store;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MenuDTO {
    private int menuId;
    private String menuName;
    private int price;
    private String menuDesc;
    private int storeId;
    private String menuImagePath;

    public MenuDTO(Menu menu) {
        menuId = menu.getId();
        menuName = menu.getMenuName();
        price = menu.getPrice();
        menuDesc = menu.getMenuDesc();
        storeId = menu.getStore().getId(); //
        menuImagePath = menu.getMenuImagePath();
    }

    public Menu toEntity(Store store) {
        Menu menu = Menu.builder()
                .id(menuId)
                .menuName(menuName)
                .price(price)
                .menuDesc(menuDesc)
                .store(store)
                .menuImagePath(menuImagePath)
                .build();
        return menu;
    }
}

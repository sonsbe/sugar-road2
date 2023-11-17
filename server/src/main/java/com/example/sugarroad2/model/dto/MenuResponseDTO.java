package com.example.sugarroad2.model.dto;

import com.example.sugarroad2.model.entity.Menu;
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
public class MenuResponseDTO {
    private int menuId;
    private String menuName;
    private int price;
    private String menuDesc;
    private int storeId;
    private String menuImagePath;
    public MenuResponseDTO(Menu menu) {
        menuId = menu.getId();
        menuName = menu.getMenuName();
        price = menu.getPrice();
        menuDesc = menu.getMenuDesc();
        storeId = menu.getStore().getId(); //
        menuImagePath = menu.getMenuImagePath();
    }
}

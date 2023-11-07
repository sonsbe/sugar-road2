package com.example.sugarroad2.model.dto;

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
}

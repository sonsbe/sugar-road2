package com.example.sugarroad2.model.dto;

import com.example.sugarroad2.model.entity.Menu;
import com.example.sugarroad2.model.entity.Store;
import lombok.*;

import java.util.List;

@Getter
//@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
//@ToString
public class StoreRequest {
    private String storeName;
    private String address;
    private String phoneNumber;
    private String storeDesc;
    private float latitude;
    private float longitude;
    private String storeImagePath;
//    private List<MenuRequest> menuList;
//    private String userId;
//    private int recommendCount;
    public Store toEntity() { // 엔티티로
        Store store = Store.builder()
                .storeName(storeName)
                .address(address)
                .phoneNumber(phoneNumber)
                .storeDesc(storeDesc)
                .storeImagePath(storeImagePath)
                .build();
        return store;
    }

}

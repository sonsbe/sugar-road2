package com.example.sugarroad2.model.dto;

import com.example.sugarroad2.model.entity.Store;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
@Getter
//@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
//@ToString
public class StoreResponse {
    private int storeId;
    private String storeName;
    private String address;
    private String phoneNumber;
    private String storeDesc;
    private float latitude;
    private float longitude;
    private String storeImagePath;
    private List<MenuResponse> menuList;
    //    private String userId;
//    private int recommendCount;

    public StoreResponse(Store store, List<MenuResponse> menuResponseList){
        storeId = store.getId();
        storeName = store.getStoreName();
        address = store.getAddress();
        phoneNumber = store.getPhoneNumber();
        storeDesc = store.getStoreDesc();
        storeImagePath = store.getStoreImagePath();
        menuList = menuResponseList;
    }
}
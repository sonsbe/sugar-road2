package com.example.sugarroad2.model.dto.response;

import com.example.sugarroad2.model.dto.response.MenuResponseDTO;
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
public class StoreResponseDTO {
    private int storeId;
    private String storeName;
    private String address;
    private String phoneNumber;
    private String storeDesc;
    private float latitude;
    private float longitude;
    private String storeImagePath;
    private List<MenuResponseDTO> menuDTOList;
    //    private String userId;
//    private int recommendCount;

    public StoreResponseDTO(Store store, List<MenuResponseDTO> menuResponseDTOList){
        storeId = store.getId();
        storeName = store.getStoreName();
        address = store.getAddress();
        phoneNumber = store.getPhoneNumber();
        storeDesc = store.getStoreDesc();
        storeImagePath = store.getStoreImagePath();
        menuDTOList = menuResponseDTOList;
    }
}
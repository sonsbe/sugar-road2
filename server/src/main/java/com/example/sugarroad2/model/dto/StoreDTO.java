package com.example.sugarroad2.model.dto;

import com.example.sugarroad2.model.entity.Store;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class StoreDTO {
    private int storeId;
    private String storeName;
    private String address;
    private String phoneNumber;
    private String storeDesc;
    private float latitude;
    private float longitude;
    private String storeImagePath;
//    private String userId;
    private List<MenuDTO> menuList;
//    private int recommendCount;

    public StoreDTO(Store entity, List<MenuDTO> menuDTOList){ // 사용자와 주고받을 때 사용
        storeId=entity.getId();
        storeName=entity.getStoreName();
        address=entity.getAddress();
        phoneNumber=entity.getPhoneNumber();
        storeDesc=entity.getStoreDesc();
        storeImagePath=entity.getStoreImagePath();
        menuList  = menuDTOList;
    }
    public Store toEntity(){ // 엔티티로
        Store store = Store.builder()
                .id(storeId)
                .storeName(storeName)
                .address(address)
                .phoneNumber(phoneNumber)
                .storeDesc(storeDesc)
                .storeImagePath(storeImagePath)
                .build();
        return store;
    }
}

// class A {
//   public A(int n)
//}
// A a = new A(3);

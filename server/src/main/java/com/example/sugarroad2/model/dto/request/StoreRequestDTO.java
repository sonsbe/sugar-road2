package com.example.sugarroad2.model.dto.request;

import com.example.sugarroad2.model.entity.Store;
import com.example.sugarroad2.model.entity.Users;
import lombok.*;
import org.apache.catalina.User;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
//@ToString
public class StoreRequestDTO {
    private String storeName;
    private String address;
    private String phoneNumber;
    private String storeDesc;
    private float latitude;
    private float longitude;
    private MultipartFile storeImagePath;
//    private List<MenuRequest> menuList;
    private String userId;
//    private int recommendCount;
    public Store toEntity(String storeImagePath, Users user) { // 엔티티로
        return Store.builder()
                .storeName(storeName)
                .address(address)
                .phoneNumber(phoneNumber)
                .storeDesc(storeDesc)
                .storeImagePath(storeImagePath)
                .user(user)
                .build();
    }
}

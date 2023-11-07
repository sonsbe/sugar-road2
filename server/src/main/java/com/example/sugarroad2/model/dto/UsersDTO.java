package com.example.sugarroad2.model.dto;

import jakarta.persistence.Entity;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UsersDTO {
    private Integer id;
    private String userName;
    private String nickname;
    private String userEmail;
    private String role;
    private String status;
    private String gender;
    private String userImagePath;
    private MultipartFile image;

//    toEntity

//    DTO(Entity)
}

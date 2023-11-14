package com.example.sugarroad2.model.dto.response;

import com.example.sugarroad2.model.entity.Users;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class UsersResponseDTO {
    private String userId;
    private String userName;
    private String nickname;
    private String userEmail;
    private String role;
    private String status;
    private String gender;
    private LocalDate birth;
    private String userImagePath;
    private MultipartFile image; //프로필 사진 선택 시 임의로 저장할 속성

    public UsersResponseDTO(Users entity){
        userId = entity.getUserId();
        userName = entity.getUserName();
        nickname = entity.getNickname();
        userEmail = entity.getUserEmail();
        role = entity.getRole();
        status = entity.getStatus();
        gender = entity.getGender();
        birth = entity.getBirth();
        userImagePath = entity.getUserImagePath();
    }
}

package com.example.sugarroad2.model.dto.request;

import com.example.sugarroad2.model.entity.Users;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UsersRequestDTO {
    private String userId;
    private String userName;
    private String userPassword;
    private String nickname;
    private String userEmail;
    private String role;
    private String status;
    private String gender;
    private LocalDate birth;
    private MultipartFile image; //프로필 사진 선택 시 임의로 저장할 속성

    public Users toEntity(String userImagePath){
        Users users = Users.builder()
                .id(userId)
                .userPassword(userPassword)
                .userName(userName)
                .nickname(nickname)
                .userEmail(userEmail)
                .role(role)
                .status(status)
                .gender(gender)
                .birth(birth)
                .build();

        if(!userImagePath.isEmpty()){
            users.setUserImagePath(userImagePath);
        }

        return users;
    }

}

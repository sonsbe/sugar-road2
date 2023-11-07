package com.example.sugarroad2.model.dto;

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
public class UsersDTO {
    private Integer id;
    private String userName;
    private String nickname;
    private String userEmail;
    private String role;
    private String status;
    private String gender;
    private LocalDate birth;
    private String userImagePath;
    private MultipartFile image; //프로필 사진 선택 시 임의로 저장할 속성

    public Users toEntity(){
        Users users = Users.builder()
                .userId(String.valueOf(id))
                .userName(userName)
                .nickname(nickname)
                .userEmail(userEmail)
                .role(role)
                .status(status)
                .gender(gender)
                .birth(birth)
                .userImagePath(userImagePath)
                .build();

        if(id != null){
            users.setUserId(String.valueOf(id));
        }

//        if(!userImagePath.isEmpty()){
//            users.setUserImagePath(imageUtil.writeImage(uploadImage));
//        }

        return users;
    }

    public UsersDTO(Users entity){
        id = Integer.valueOf(entity.getUserId());
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

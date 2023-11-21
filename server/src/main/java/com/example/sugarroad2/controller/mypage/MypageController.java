package com.example.sugarroad2.controller.mypage;

import com.example.sugarroad2.model.entity.Users;
import com.example.sugarroad2.service.UsersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/mypage")
@CrossOrigin(origins="http://localhost:5173", allowedHeaders = "*",
        exposedHeaders="Authorization", allowCredentials = "true")//SOP 문제 해결과 쿠키를 전달받기 위한 설정
public class MypageController {

    @Autowired
    UsersService usersService;

//    @PreAuthorize("hasAnyRole('ADMIN')")
//    @GetMapping("/adminrolepage")
//    public String adminSettingPage() {
//        return "admin_role";
//    }
//
//    @PreAuthorize("hasAnyRole('USER')")
//    @GetMapping("/userrolepage")
//    public String userSettingPage() {
//        return "user_role";
//    }

    @GetMapping("/{id}") //해당 유저 정보 출력
    public ResponseEntity<Users> userInfo(@PathVariable("id") String userId) {
        ResponseEntity<Users> entity = new ResponseEntity<>(usersService.readById(userId), HttpStatus.OK);

        return entity;
    }

    @PutMapping("/{id}") //받은 유저 정보 업데이트
    public ResponseEntity<String> userInfoUpdate(@PathVariable("id") String userId, @RequestBody Users users) {
        Users updateUser = usersService.readById(userId);

        updateUser.setId(users.getId());
        updateUser.setUserPassword(users.getUserPassword());
        updateUser.setUserName(users.getUserName());
        updateUser.setNickname(users.getNickname());
        updateUser.setUserEmail(users.getUserEmail());
        //updateUser.setBirth(users.getBirth());
        //updateUser.setGender(users.getGender());
        //updateUser.setRole(users.getRole());
        //updateUser.setStatus(users.getStatus());
        updateUser.setUserImagePath(users.getUserImagePath());

        usersService.update(updateUser);

        return new ResponseEntity<>("성공적으로 업데이트", HttpStatus.OK);
    }

    @DeleteMapping("/{id}") //받은 유저 정보 삭제
    public ResponseEntity<String> userDelete(@PathVariable("id") String userId){
        Users deleteUser = usersService.readById(userId);

        usersService.delete(deleteUser);

        return new ResponseEntity<>("성공적으로 탈퇴", HttpStatus.OK);
    }
}


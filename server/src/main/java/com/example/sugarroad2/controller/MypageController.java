package com.example.sugarroad2.controller;

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
public class MypageController {

    @Autowired
    UsersService usersService;

    @GetMapping("/{id}") //해당 유저 정보 출력
    public ResponseEntity<Users> userInfo(@PathVariable("id") String userId) {
        ResponseEntity<Users> entity = new ResponseEntity<>(usersService.readById(userId), HttpStatus.OK);

        return entity;
    }

    @PostMapping //받은 유저 정보 생성 (테스트용도, 로그인 구현되면 해당 컨트롤러로 옮길 예정)
    public ResponseEntity<String> userSignUp(@RequestBody Users users) {
        if (usersService.duplicationId(users)) {
            return new ResponseEntity<>("아이디가 중복됩니다", HttpStatus.FORBIDDEN);
        } else if (usersService.duplicationNick(users)) {
            return new ResponseEntity<>("닉네임이 중복됩니다", HttpStatus.FORBIDDEN);
        } else if (usersService.duplicationEmail(users)) {
            return new ResponseEntity<>("이메일이 중복됩니다", HttpStatus.FORBIDDEN);
        } else {
            usersService.create(users);
        }

        return new ResponseEntity<>("성공적으로 생성", HttpStatus.OK);
    }

    @PutMapping("/{id}") //받은 유저 정보 업데이트
    public ResponseEntity<String> userInfoUpdate(@PathVariable("id") String userId, @RequestBody Users users) {
        Users updateUser = usersService.readById(userId);

        updateUser.setId(users.getId());
        updateUser.setUserPassword(users.getUserPassword());
        updateUser.setUserName(users.getUserName());
        updateUser.setNickname(users.getNickname());
        updateUser.setUserEmail(users.getUserEmail());
        updateUser.setBirth(users.getBirth());
        updateUser.setGender(users.getGender());
        updateUser.setRole(users.getRole());
        updateUser.setStatus(users.getStatus());
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


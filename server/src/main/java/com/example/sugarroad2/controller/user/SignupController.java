package com.example.sugarroad2.controller.user;

import com.example.sugarroad2.model.entity.Users;
import com.example.sugarroad2.service.UsersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/signup")
public class SignupController {

    private final UsersService usersService;

    public SignupController(UsersService usersService){
        this.usersService = usersService;
    }

    @PostMapping //받은 유저 정보 생성
    public ResponseEntity<String> userSignUp(@RequestBody Users users) {

        if (usersService.duplicationId(users)) {
            return new ResponseEntity<>("중복된 아이디입니다", HttpStatus.FORBIDDEN);
        } else if (usersService.duplicationNick(users)) {
            return new ResponseEntity<>("중복된 닉네임입니다", HttpStatus.FORBIDDEN);
        } else if (usersService.duplicationEmail(users)) {
            return new ResponseEntity<>("중복된 이메일입니다", HttpStatus.FORBIDDEN);
        } else {
            usersService.create(users);
            return new ResponseEntity<>("성공적으로 생성", HttpStatus.OK);
        }
    }
}

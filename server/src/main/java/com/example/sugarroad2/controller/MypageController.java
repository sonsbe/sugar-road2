package com.example.sugarroad2.controller;

import com.example.sugarroad2.model.entity.Users;
import com.example.sugarroad2.repository.UsersRepository;
import com.example.sugarroad2.service.UsersService;
import com.example.sugarroad2.util.ImageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/mypage")
public class MypageController {

    @Autowired
    UsersService usersService;

    @Autowired
    ImageUtil imageUtil;

    @Autowired
    private UsersRepository usersRepository;

    @GetMapping
    public ResponseEntity<Users> info(@PathVariable("id") String userId) { //해당 유저 정보 출력
        ResponseEntity<Users> entity = new ResponseEntity<>(usersService.selectId(userId), HttpStatus.OK);

        return entity;
    }
}


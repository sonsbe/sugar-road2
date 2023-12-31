package com.example.sugarroad2.config.auth;

import com.example.sugarroad2.model.entity.Users;
import com.example.sugarroad2.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
public class SecurityDetailsService implements UserDetailsService {

    private final UsersService usersService;

    @Autowired
    public SecurityDetailsService(UsersService usersService){
        this.usersService = usersService;
    }

    @Override
    public UserDetails loadUserByUsername(String insertUserId) {
        //회원 인증 절차
        //여기의 username은 PK 속성을 가진 ID와 동등합니다
        
        Users findOne = usersService.findUser(insertUserId).orElseThrow(()-> new RuntimeException());

//      findOne.orElseThrow(() -> new IllegalArgumentException("존재 하지 않는 회원 입니다"));

        //인증 절차 성공 시 UserDetails 객체를 생성해서 리턴
        return new NowUserDetails(findOne);
    }
}

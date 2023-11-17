package com.example.sugarroad2.service;

import com.example.sugarroad2.model.entity.Users;
import com.example.sugarroad2.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsersService {
    // 아이디 닉네임 이메일 중복 처리 repository, service에 추가하고
    // 중복될 경우 throw DuplicateException하고 스태이터스 Forbidden 로 반환

    @Autowired
    UsersRepository usersRepository;

    public void create(Users users){
        usersRepository.save(users);
    }

    public boolean duplicationId(Users users){ //아이디 중복 확인
        //유저 ID를 통해 DB조회를 합니다
        Optional<Users> optionalUsers = usersRepository.findById(users.getId());
        optionalUsers.orElseThrow(() -> new DuplicateKeyException("DuplicateKey id : " + users.getId()));
        return true;

    }

    public boolean duplicationNick(Users users){ //닉네임 중복 확인
        Optional<Users> optionalUsers = usersRepository.findByNickname(users.getNickname());
        optionalUsers.orElseThrow(() -> new DuplicateKeyException("DuplicateKey nickname : " + users.getNickname()));
        return true;

    }

    public boolean duplicationEmail(Users users){ //이메일 중복 확인
        Users selectUsers = usersRepository.findById(users.getId()).get();

        if (users.getUserEmail() == selectUsers.getUserEmail()) { //이메일이 같으면
            return true;
        } else { //해당되지 않으면
            return false;
        }

    }

    public Users readById(String id){ //Read
        //각종 서비스에서 유저 ID를 통해 DB조회를 합니다
        Optional<Users> optionalUsers = usersRepository.findById(id);
        optionalUsers.orElseThrow(() -> new OptimisticLockingFailureException("Not Found id : " + id));
        return optionalUsers.get();
    }

    public void update(Users users){
        Users origin = usersRepository.findById(users.getId()).get();
        origin.setBirth(users.getBirth());
        origin.setRole(users.getRole());
        origin.setStatus(users.getStatus());
        origin.setUserEmail(users.getUserEmail());
        origin.setUserImagePath(users.getUserImagePath());
        origin.setNickname(users.getNickname());
        origin.setUserPassword(users.getUserPassword());
        origin.setGender(users.getGender());
        origin.setUserName(users.getUserName());
        usersRepository.save(origin);
    }

    public void delete(Users users){ //Delete
        // 현재 패스워드 일치 여부는 구현하지 않았습니다. 추후 구현할 예정입니다.
        // 패스워드 일치하지 않으면 ...
        usersRepository.delete(users);
    }
}

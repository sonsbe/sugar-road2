package com.example.sugarroad2.service;

import com.example.sugarroad2.model.entity.Users;
import com.example.sugarroad2.repository.UsersRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class UsersService {
    // 아이디 닉네임 이메일 중복 처리 repository, service에 추가하고
    // 중복될 경우 throw DuplicateException하고 <- 방법을 못찾음
    // 스태이터스 Forbidden 로 반환 <- 컨트롤러에서 구현함

    private final PasswordEncoder passwordEncoder;
    private final UsersRepository usersRepository;

    @Autowired
    public UsersService(PasswordEncoder passwordEncoder, UsersRepository usersRepository){
        this.passwordEncoder = passwordEncoder;
        this.usersRepository = usersRepository;
    }

    public void create(Users users){ //회원가입
        users.setUserPassword(passwordEncoder.encode(users.getUserPassword()));
        //패스워드 암호화

        users.setRole("USER");
        //회원가입 유저 권한 설정

        usersRepository.save(users);
    }

    public boolean duplicationId(Users users){ //아이디 중복 확인
        Optional<Users> optionalUsers = usersRepository.findById(users.getId());
        return optionalUsers.isPresent();
    }

    public boolean duplicationNick(Users users){ //닉네임 중복 확인
        Optional<Users> optionalUsers = usersRepository.findByNickname(users.getNickname());
        return optionalUsers.isPresent();
    }

    public boolean duplicationEmail(Users users){ //이메일 중복 확인
        Optional<Users> optionalUsers = usersRepository.findByUserEmail(users.getUserEmail());
        return optionalUsers.isPresent();
    }

    public Users readById(String id){ //Read
        //각종 서비스에서 유저 ID를 통해 DB조회를 합니다
        Optional<Users> optionalUsers = usersRepository.findById(id);
        optionalUsers.orElseThrow(() -> new OptimisticLockingFailureException("Not Found id : " + id));
        return optionalUsers.get();
    }

    public Optional<Users> findUser(String id){
        //Spring Security의 UserDetailsService를 위해 추가했습니다
        //리턴한 곳에서 회원 인증 절차를 수행합니다
        return usersRepository.findById(id);
    }

    public void update(Users users){
        Users origin = usersRepository.findById(users.getId()).get(); //수정하려는 유저 조회

        origin.setUserPassword(passwordEncoder.encode(users.getUserPassword())); //암호화하여 저장
        origin.setUserName(users.getUserName()); //유저 이름
        origin.setBirth(users.getBirth()); //생일
        //origin.setRole(users.getRole()); //유저권한 수정 필요 시 사용
        origin.setStatus(users.getStatus()); //유저 허용 상태
        origin.setUserEmail(users.getUserEmail()); //이메일
        origin.setUserImagePath(users.getUserImagePath()); //유저 프로필 이미지 경로
        origin.setNickname(users.getNickname()); //유저 별명
        origin.setGender(users.getGender()); //유저 성별

        usersRepository.save(origin);
    }

    public void delete(Users users){ //Delete
        // 현재 패스워드 일치 여부는 구현하지 않았습니다. 추후 구현할 예정입니다.
        // 패스워드 일치하지 않으면 ...
        usersRepository.delete(users);
    }
}

package com.example.sugarroad2.service;

import com.example.sugarroad2.model.dto.request.UsersRequestDTO;
import com.example.sugarroad2.model.entity.Users;
import com.example.sugarroad2.repository.UsersRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class UsersService {

    @Autowired
    UsersRepository usersRepository;

    public boolean insert(UsersRequestDTO newbieUser){ //Create
        //회원가입 시 신규회원의 DTO를 받아서 save() 메서드를 호출합니다
        //신규회원의 DTO 안에는 이미지 경로도 함께 들어있습니다
        //아귀먼트로 유저 이미지 경로가 들어갑니다 (Users Entity 참고)
        Users newbieUserEntity = newbieUser.toEntity(newbieUser.getUserImagePath());

        try {
            usersRepository.save(newbieUserEntity);
        } catch (Exception e){
            return  false;
        }
        return true;
    }

    public Users selectId(String selectId){ //Read
        //각종 서비스에서 유저 ID를 통해 DB조회를 합니다
        Optional<Users> selectUser = usersRepository.findById(selectId);

        if(selectUser.isPresent()){ //조회 결과가 없으면 텅 빈 엔티티를 리턴합니다
            return Users.builder().userId("").build();
        } else {
            return selectUser.get();
        }
    }

    public boolean save(UsersRequestDTO editUser, String updateUserImagePath){ //Update
        //마이페이지에서 회원정보 수정할 때 사용합니다.
        //아귀먼트로 수정할 프로필 이미지 경로를 따로 받습니다.
        Users editUserEntity = editUser.toEntity(updateUserImagePath);

        try {
            usersRepository.save(editUserEntity);
        } catch (Exception e){
            return false;
        }
        return true;
    }

    public boolean delete(UsersRequestDTO deleteUser){ //Delete
        //회원 탈퇴 시 사용할 메서드입니다.
        //DTO안에 회원의 패스워드, ID를 받아올 예정입니다
        //현재 패스워드 일치 여부는 구현하지 않았습니다. 추후 구현할 예정입니다.
        Users deleteUserEntity = deleteUser.toEntity("");

        try {
            usersRepository.delete(deleteUserEntity);
        } catch (Exception e){
            return false;
        }
        return true;
    }
}

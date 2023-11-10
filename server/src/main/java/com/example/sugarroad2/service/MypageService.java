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
public class MypageService {

    @Autowired
    UsersRepository usersRepository;

    public boolean insert(UsersRequestDTO newbieUser){ //Create
        Users newbieUserEntity = newbieUser.toEntity(newbieUser.getUserImagePath());

        try {
            usersRepository.save(newbieUserEntity);
        } catch (Exception e){
            return  false;
        }
        return true;
    }

    public Users selectId(String selectId){ //Read

        Optional<Users> selectUser = usersRepository.findById(selectId);

        if(selectUser.isPresent()){
            return Users.builder().build();
        } else {
            return selectUser.get();
        }
    }

    public boolean save(UsersRequestDTO editUser, String updateUserImagePath){ //Update
        Users editUserEntity = editUser.toEntity(updateUserImagePath);

        try {
            usersRepository.save(editUserEntity);
        } catch (Exception e){
            return false;
        }
        return true;
    }

    public boolean delete(UsersRequestDTO deleteUser){ //Delete
        Users deleteUserEntity = deleteUser.toEntity("");

        try {
            usersRepository.delete(deleteUserEntity);
        } catch (Exception e){
            return false;
        }
        return true;
    }
}

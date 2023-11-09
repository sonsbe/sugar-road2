package com.example.sugarroad2.service;

import com.example.sugarroad2.model.dto.UsersDTO;
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
    UsersDTO usersDTO;

    @Autowired
    Users usersE;

    @Autowired
    UsersRepository usersRepository;

    public void insert(UsersDTO newbieUser){ //Create
        Users newbieUserEntity = newbieUser.toEntity(newbieUser.getUserImagePath());

        usersRepository.save(newbieUserEntity);
    }

    public Optional<Users> selectId(String selectId){ //Read

        Optional<Users> selectUser = usersRepository.findById(selectId);

        return selectUser;
    }

    public boolean save(UsersDTO editUser, String updateUserImagePath){ //Update
        Users editUserEntity = editUser.toEntity(updateUserImagePath);

        try {
            usersRepository.save(editUserEntity);
        } catch (Exception e){
            return false;
        }
        return true;
    }

    public void delete(UsersDTO deleteUser){ //Delete
        Users deleteUserEntity = deleteUser.toEntity("");

        usersRepository.delete(deleteUserEntity);
    }
}

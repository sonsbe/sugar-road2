package com.example.sugarroad2.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class Users {
    @Id
    @Column(name="user_id")
    private String userId;
    private String userPassword;
    private String userName;
    private String nickname;
    private String userEmail;
    private String userImagePath;
    private String role;
    private String status;
    private String gender;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime birth;

    //private MultipartFile image;
}

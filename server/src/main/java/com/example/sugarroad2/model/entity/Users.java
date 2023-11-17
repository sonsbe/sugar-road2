package com.example.sugarroad2.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Users {
    @Id
    @Column(name="user_id")
    private String id;

    @Column(nullable = false)
    private String userPassword;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String userEmail;

    @Column(nullable = false)
    private String role;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private String gender;

    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birth;

    private String userImagePath;


}

package com.example.sugarroad2.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Store {
@Id
@Column(name="store_id")
@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String storeName;
    private String address;
    private String phoneNumber;
    private String storeDesc;
    private float latitude;
    private float longitude;
    private String storeImagePath;
//  private int recommendCount;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name="user_id")
    private Users user;
}

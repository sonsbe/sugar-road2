package com.example.sugarroad2.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Menu {
    @Id
    @Column(name="menu_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String menuName;
    private int price;
    private String menuDesc;
    @ManyToOne()
    @JoinColumn(name="store_id")
    @NotFound(action = NotFoundAction.IGNORE)
    private Store store;
    private String menuImagePath;
}

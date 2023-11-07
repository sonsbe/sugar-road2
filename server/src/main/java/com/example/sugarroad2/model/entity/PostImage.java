package com.example.sugarroad2.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostImage {
    @Id
    @Column(name = "post_image_id")
    private int id;
    @Column(name = "post_image_path")
    private String postImagePath;
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;
}

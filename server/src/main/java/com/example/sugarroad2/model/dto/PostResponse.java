package com.example.sugarroad2.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class PostResponse {
    private String content;
    private String title;
    private List<String> postImage;
    private LocalDateTime postedDate;
    private String userId;
    private String postCategoryId;
    private int commentCount;
    private int recommendCount;
}

package com.example.sugarroad2.model.dto.request;

import com.example.sugarroad2.model.entity.Recommendation;
import com.example.sugarroad2.model.entity.Users;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RecommendationRequestDTO {
    private Integer id;
    private String referenceType;
    private int referenceId;
    private LocalDateTime postedDate;
    private String userId;

    public Recommendation toEntity(Users users){
        Recommendation recommendation = Recommendation.builder()
            .users(users)
            .referenceId(referenceId)
            .referenceType(referenceType)
            .postedDate(postedDate)
            .build();

        if (id != null){
           recommendation.setId(id);
        }

        return recommendation;
    }
}

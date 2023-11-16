package com.example.sugarroad2.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class RecommendationResultResponseVO {
	private String referenceType;
	private int referenceId;
	private long count;
	private Boolean isRecommended;
}

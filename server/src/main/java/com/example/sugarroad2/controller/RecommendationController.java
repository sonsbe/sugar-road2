package com.example.sugarroad2.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import com.example.sugarroad2.model.dto.request.RecommendationRequestDTO;
import com.example.sugarroad2.model.dto.response.RecommendationResultResponseVO;
import com.example.sugarroad2.model.entity.Recommendation;
import com.example.sugarroad2.model.entity.Users;
import com.example.sugarroad2.service.RecommendationService;
import com.example.sugarroad2.service.UsersService;
import com.example.sugarroad2.util.HATEOASUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/recommendation")
@Slf4j
public class RecommendationController {

	@Autowired
	private RecommendationService recommendationService;
	@Autowired
	private UsersService usersService;
	@Autowired
	private HATEOASUtil hateoasUtil;

	@GetMapping("/reference-type/{referenceType}/reference-id/{referenceId}")
	public ResponseEntity<EntityModel<RecommendationResultResponseVO>> checkRecommendation(
		@PathVariable("referenceType") String referenceType,
		@PathVariable("referenceId") int referenceId) {
		try {

			String userId = "vv980113";

			long count = recommendationService.countByReference(referenceType, referenceId);
			Users users = usersService.readById(userId);
			boolean isRecommended = recommendationService.existsByUserAndReference(users,
				referenceType,
				referenceId);
			RecommendationResultResponseVO recommendationResultResponseVO =
				new RecommendationResultResponseVO(referenceType, referenceId, count, isRecommended);

			EntityModel<RecommendationResultResponseVO> recommendationResultResponseVOModel =
				hateoasUtil.constrainHATEOAS(recommendationResultResponseVO);

			return ResponseEntity.status(HttpStatus.OK)
				.body(recommendationResultResponseVOModel);
		} catch (OptimisticLockingFailureException exception) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		} catch (Exception exception) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
	}

	@PostMapping
	public ResponseEntity<EntityModel<RecommendationResultResponseVO>> createRecommendation(
		@RequestBody RecommendationRequestDTO recommendationRequestDTO) {
		try {
			Users users = usersService.readById(recommendationRequestDTO.getUserId());
			Recommendation recommendation = recommendationRequestDTO.toEntity(users);
			recommendationService.create(recommendation);

			long count = recommendationService.countByReference(
				recommendationRequestDTO.getReferenceType(),
				recommendationRequestDTO.getReferenceId()
			);
			RecommendationResultResponseVO recommendationResultResponseVO =
				new RecommendationResultResponseVO(
					recommendationRequestDTO.getReferenceType(),
					recommendationRequestDTO.getReferenceId(),
					count,
					true);

			EntityModel<RecommendationResultResponseVO> recommendationResultResponseVOModel =
				hateoasUtil.constrainHATEOAS(recommendationResultResponseVO);

			return ResponseEntity.status(HttpStatus.CREATED)
				.body(recommendationResultResponseVOModel);
		} catch (Exception exception) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
	}

	@DeleteMapping("/reference-type/{referenceType}/reference-id/{referenceId}")
	public ResponseEntity<EntityModel<RecommendationResultResponseVO>> deleteRecommendation(
		@PathVariable("referenceType") String referenceType,
		@PathVariable("referenceId") int referenceId) {

		try {
			String userId = "vv980113";
			Users users = usersService.readById(userId);
			recommendationService.deleteByReference(referenceType, referenceId, users);

			long count = recommendationService.countByReference(referenceType, referenceId);

			RecommendationResultResponseVO recommendationResultResponseVO =
				new RecommendationResultResponseVO(referenceType, referenceId, count, false);

			EntityModel<RecommendationResultResponseVO> recommendationResultResponseVOModel =
				hateoasUtil.constrainHATEOAS(recommendationResultResponseVO);

			return ResponseEntity.status(HttpStatus.OK)
				.body(recommendationResultResponseVOModel);
		} catch (OptimisticLockingFailureException exception) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		} catch (Exception exception) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
	}
}

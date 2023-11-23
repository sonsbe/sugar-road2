package com.example.sugarroad2.controller;

import com.example.sugarroad2.config.auth.NowUserDetails;
import com.example.sugarroad2.model.dto.request.ReviewCommentRequestDTO;
import com.example.sugarroad2.model.dto.response.ReviewCommentResponseVO;
import com.example.sugarroad2.model.entity.Review;
import com.example.sugarroad2.model.entity.ReviewComment;
import com.example.sugarroad2.model.entity.Users;
import com.example.sugarroad2.service.ReviewCommentService;
import com.example.sugarroad2.service.ReviewService;
import com.example.sugarroad2.service.UsersService;
import com.example.sugarroad2.util.HATEOASUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/review-comment")
@Slf4j
@RequiredArgsConstructor
public class ReviewCommentController {

	private final PagedResourcesAssembler<ReviewCommentResponseVO> pagedResourcesAssembler;

	@Autowired
	private ReviewCommentService reviewCommentService;

	@Autowired
	UsersService usersService;

	@Autowired
	ReviewService reviewService;

	@Autowired
	HATEOASUtil hateoasUtil;

	@PostMapping
	public ResponseEntity<EntityModel<ReviewCommentResponseVO>> createReviewComment(
		@RequestBody ReviewCommentRequestDTO reviewCommentRequestDTO,
		@AuthenticationPrincipal NowUserDetails nowUserDetails) {

		Users users = usersService.readById(nowUserDetails.getUser().getId());
		Review review = reviewService.readById(reviewCommentRequestDTO.getReviewId());
		ReviewComment reviewComment;

		if (reviewCommentRequestDTO.getParentComment() != null){
			ReviewComment parent = reviewCommentService.readById(reviewCommentRequestDTO.getParentComment());
			reviewComment = reviewCommentRequestDTO.toEntity(users, review, parent);
		}
		else {
			reviewComment = reviewCommentRequestDTO.toEntity(users, review);
		}

		reviewComment = reviewCommentService.create(reviewComment);
		ReviewCommentResponseVO reviewCommentResponseVO = new ReviewCommentResponseVO(reviewComment);
		EntityModel<ReviewCommentResponseVO> reviewCommentResponseVOEntityModel =
			hateoasUtil.constrainHATEOAS(reviewCommentResponseVO);

		return ResponseEntity.status(HttpStatus.CREATED).body(reviewCommentResponseVOEntityModel);
	}

	@GetMapping("/{id}")
	public ResponseEntity<EntityModel<ReviewCommentResponseVO>> readReviewComment(
		@PathVariable("id") int id) {

		ReviewComment reviewComment = reviewCommentService.readById(id);
		ReviewCommentResponseVO reviewCommentResponseVO = new ReviewCommentResponseVO(reviewComment);
		EntityModel<ReviewCommentResponseVO> reviewCommentResponseVOEntityModel =
			hateoasUtil.constrainHATEOAS(reviewCommentResponseVO);
		return ResponseEntity.status(HttpStatus.CREATED).body(reviewCommentResponseVOEntityModel);
	}

	@GetMapping("/of/review/{reviewId}")
	public ResponseEntity<PagedModel<EntityModel<ReviewCommentResponseVO>>> readReviewCommentPage(
		@PathVariable("reviewId") int reviewId,
		@PageableDefault(value = 5, page = 0) Pageable pageable) {

		Review review = reviewService.readById(reviewId);
		Page<ReviewComment> reviewCommentPage = reviewCommentService.readPage(review, pageable);
		Page<ReviewCommentResponseVO> reviewCommentResponseVOPage = reviewCommentPage.map(ReviewCommentResponseVO::new);
		PagedModel<EntityModel<ReviewCommentResponseVO>> reviewCommentResponseVOPagedModel =
			hateoasUtil.constrainHATEOASReviewComment(reviewCommentResponseVOPage, pagedResourcesAssembler);
		return ResponseEntity.status(HttpStatus.OK).body(reviewCommentResponseVOPagedModel);
	}

	@PutMapping("/{id}")
	public ResponseEntity<EntityModel<ReviewCommentResponseVO>> updateReviewComment(
		@PathVariable("id") int id,
		@RequestBody ReviewCommentRequestDTO reviewCommentRequestDTO,
		@AuthenticationPrincipal NowUserDetails nowUserDetails) {

		reviewCommentRequestDTO.setId(id);
		Users users = usersService.readById(nowUserDetails.getUser().getId());
		Review review = reviewService.readById(reviewCommentRequestDTO.getReviewId());
		ReviewComment reviewComment;

		if (reviewCommentRequestDTO.getParentComment() != null){
			ReviewComment parent = reviewCommentService.readById(reviewCommentRequestDTO.getParentComment());
			reviewComment = reviewCommentRequestDTO.toEntity(users, review, parent);
		}
		else {
			reviewComment = reviewCommentRequestDTO.toEntity(users, review);
		}
		reviewComment = reviewCommentService.update(reviewComment);
		ReviewCommentResponseVO reviewCommentResponseVO = new ReviewCommentResponseVO(reviewComment);
		EntityModel<ReviewCommentResponseVO> reviewCommentResponseVOEntityModel =
			hateoasUtil.constrainHATEOAS(reviewCommentResponseVO);
		return ResponseEntity.status(HttpStatus.CREATED).body(reviewCommentResponseVOEntityModel);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteReviewComment(
		@PathVariable("id") int id) {

		reviewCommentService.deleteById(id);
		return ResponseEntity.status(HttpStatus.OK).body(null);
	}

}

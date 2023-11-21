package com.example.sugarroad2.controller;

import com.example.sugarroad2.model.dto.request.ReviewRequestDTO;
import com.example.sugarroad2.model.dto.response.ReviewResponseVO;
import com.example.sugarroad2.model.entity.Review;
import com.example.sugarroad2.model.entity.Store;
import com.example.sugarroad2.model.entity.Users;
import com.example.sugarroad2.service.ReviewService;
import com.example.sugarroad2.service.StoreService;
import com.example.sugarroad2.service.UsersService;
import com.example.sugarroad2.util.HATEOASUtil;
import com.example.sugarroad2.util.ImageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/review")
@Slf4j
@RequiredArgsConstructor
public class ReviewController {

	@Autowired
	ReviewService reviewService;
	@Autowired
	UsersService usersService;
	@Autowired
	StoreService storeService;
	@Autowired
	HATEOASUtil hateoasUtil;
	@Autowired
	ImageUtil imageUtil;
	private final PagedResourcesAssembler<ReviewResponseVO> pagedResourcesAssembler;
	@GetMapping("/of/store/{storeId}")
	public ResponseEntity<PagedModel<EntityModel<ReviewResponseVO>>> readReviewPage(
		@PathVariable("storeId") int storeId,
		@PageableDefault(value = 3) Pageable pageable) {

		try {
			Page<Review> reviewPage = reviewService.readPage(storeService.readById(storeId), pageable);
			Page<ReviewResponseVO> reviewResponseVOPage = reviewPage.map(ReviewResponseVO::new);
			PagedModel<EntityModel<ReviewResponseVO>> reviewResponseVOPagedModel =
				hateoasUtil.constrainHATEOASReviewPage(reviewResponseVOPage, pagedResourcesAssembler);
			return ResponseEntity.status(HttpStatus.OK).body(reviewResponseVOPagedModel);
		}
		catch (OptimisticLockingFailureException exception){
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		catch (Exception exception){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<EntityModel<ReviewResponseVO>> readReview(
		@PathVariable("id") int id) {

		try {
			Review review = reviewService.readById(id);
			ReviewResponseVO reviewResponseVO = new ReviewResponseVO(review);
			EntityModel<ReviewResponseVO> reviewResponseVOEntityModel =
				hateoasUtil.constrainHATEOAS(reviewResponseVO);

			return ResponseEntity.status(HttpStatus.OK).body(reviewResponseVOEntityModel);
		}
		catch (OptimisticLockingFailureException exception){
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		catch (Exception exception){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
	}

	@PostMapping
	public ResponseEntity<EntityModel<ReviewResponseVO>> createReview(
		@RequestBody ReviewRequestDTO reviewRequestDTO) {
		try {
			Users users = usersService.readById("vv980113");
			Store store = storeService.readById(reviewRequestDTO.getStoreId());
//			String reviewImagePath = imageUtil.writeImage(reviewRequestDTO.getUploadImage());
			String reviewImagePath = "";
			Review review = reviewService.create(
				reviewRequestDTO.toEntity(users, store, reviewImagePath)
			);
			ReviewResponseVO reviewResponseVO = new ReviewResponseVO(review);

			EntityModel<ReviewResponseVO> reviewResponseVOEntityModel =
				hateoasUtil.constrainHATEOAS(reviewResponseVO);

			return ResponseEntity.status(HttpStatus.CREATED).body(reviewResponseVOEntityModel);
		}
		catch (Exception exception){
			exception.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<EntityModel<ReviewResponseVO>> updateReview(
		@RequestBody ReviewRequestDTO reviewRequestDTO,
		@PathVariable("id") int id) {
		try {
			Users users = usersService.readById("vv980113");
			Store store = storeService.readById(reviewRequestDTO.getStoreId());
//			String reviewImagePath = imageUtil.writeImage(reviewRequestDTO.getUploadImage());
			String reviewImagePath = "";
			reviewRequestDTO.setId(id);
			System.out.println(reviewRequestDTO.toString());
			Review review = reviewService.update(
				reviewRequestDTO.toEntity(users, store, reviewImagePath)
			);
			ReviewResponseVO reviewResponseVO = new ReviewResponseVO(review);

			EntityModel<ReviewResponseVO> reviewResponseVOEntityModel =
				hateoasUtil.constrainHATEOAS(reviewResponseVO);

			return ResponseEntity.status(HttpStatus.CREATED).body(reviewResponseVOEntityModel);
		}
		catch (Exception exception){
			exception.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteReview(
		@PathVariable("id") int id) {
		try {
			reviewService.deleteById(id);
			return ResponseEntity.status(HttpStatus.OK).body(null);
		}
		catch (Exception exception) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
	}
}

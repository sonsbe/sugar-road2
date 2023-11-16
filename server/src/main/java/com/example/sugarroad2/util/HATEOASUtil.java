package com.example.sugarroad2.util;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.example.sugarroad2.controller.PostCommentController;
import com.example.sugarroad2.controller.PostController;
import com.example.sugarroad2.controller.RecommendationController;
import com.example.sugarroad2.controller.ReviewCommentController;
import com.example.sugarroad2.controller.ReviewController;
import com.example.sugarroad2.controller.StoreController;
import com.example.sugarroad2.model.dto.request.RecommendationRequestDTO;
import com.example.sugarroad2.model.dto.response.PostCommentResponseVO;
import com.example.sugarroad2.model.dto.response.RecommendationResultResponseVO;
import com.example.sugarroad2.model.dto.response.ReviewCommentResponseVO;
import com.example.sugarroad2.model.dto.response.ReviewResponseVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Component;

@Component
public class HATEOASUtil {

	private EntityModel<ReviewResponseVO> addLinkReview(EntityModel<ReviewResponseVO> entityModel) {
		ReviewResponseVO reviewResponseVO = entityModel.getContent();
		assert reviewResponseVO != null;
		return entityModel
			.add(
				linkTo(
					methodOn(ReviewController.class)
						.readReview(reviewResponseVO.getId())
				).withSelfRel()
			)
			.add(
				linkTo(
					methodOn(RecommendationController.class)
						.checkRecommendation("S", reviewResponseVO.getId())
				).withRel("recommendation")
			)
			.add(
				linkTo(
					methodOn(StoreController.class)
						.readBy(reviewResponseVO.getStoreId())
				).withRel("store")
			)
			.add(
				linkTo(
					methodOn(ReviewCommentController.class)
						.readReviewCommentPage(
							reviewResponseVO.getStoreId(),
							PageRequest.of(0, 5))
				).withRel("reviewCommentFirstPage")
			);
	}

	public EntityModel<ReviewResponseVO> constrainHATEOAS(ReviewResponseVO reviewResponseVO) {
		return addLinkReview(EntityModel.of(reviewResponseVO));

	}

	public PagedModel<EntityModel<ReviewResponseVO>> constrainHATEOASReviewPage(
		Page<ReviewResponseVO> page,
		PagedResourcesAssembler<ReviewResponseVO> assembler) {

		PagedModel<EntityModel<ReviewResponseVO>> pagedModel = assembler.toModel(page);
		pagedModel.forEach(this::addLinkReview);
		return pagedModel;
	}

	private EntityModel<RecommendationResultResponseVO> addLinkRecommendation(
		EntityModel<RecommendationResultResponseVO> entityModel) {

		RecommendationResultResponseVO recommendationResultResponseVO = entityModel.getContent();
		assert recommendationResultResponseVO != null;
		return entityModel
			.add(
				linkTo(
					methodOn(RecommendationController.class)
						.checkRecommendation(
								recommendationResultResponseVO.getReferenceType(),
								recommendationResultResponseVO.getReferenceId()
							)
				).withSelfRel()
			)
			.add(
				linkTo(
					methodOn(RecommendationController.class)
						.createRecommendation(new RecommendationRequestDTO())
				).withRel("createRecommendation")
			)
			.add(
				linkTo(
					methodOn(RecommendationController.class)
						.deleteRecommendation(
							recommendationResultResponseVO.getReferenceType(),
							recommendationResultResponseVO.getReferenceId()
						)
				).withRel("deleteRecommendation")
			);
	}

	public EntityModel<RecommendationResultResponseVO> constrainHATEOAS(
		RecommendationResultResponseVO recommendationResultResponseVO) {

		return addLinkRecommendation(EntityModel.of(recommendationResultResponseVO));
	}

	private EntityModel<PostCommentResponseVO> addLinkPostComment(EntityModel<PostCommentResponseVO> entityModel) {
		PostCommentResponseVO postCommentResponseVO = entityModel.getContent();
		assert postCommentResponseVO != null;
		return entityModel
			.add(
				linkTo(
					methodOn(PostCommentController.class)
						.readPostComment(postCommentResponseVO.getId())
				).withSelfRel()
			)
			.addIf(
				postCommentResponseVO.getParentComment() != null,
				() -> linkTo(
					methodOn(PostCommentController.class)
						.readPostComment(postCommentResponseVO.getParentComment())
				).withRel("parent")
			)
			.add(
				linkTo(
					methodOn(PostController.class)
						.readById(postCommentResponseVO.getPostId())
				).withRel("post")
			);
	}

	public EntityModel<PostCommentResponseVO> constrainHATEOAS(
		PostCommentResponseVO postCommentResponseVO) {

		return addLinkPostComment(EntityModel.of(postCommentResponseVO));
	}

	public PagedModel<EntityModel<PostCommentResponseVO>> constrainHATEOASPostComment(
		Page<PostCommentResponseVO> page,
		PagedResourcesAssembler<PostCommentResponseVO> assembler) {

		PagedModel<EntityModel<PostCommentResponseVO>> pagedModel = assembler.toModel(page);
		pagedModel.forEach(this::addLinkPostComment);
		return pagedModel;
	}

	public EntityModel<ReviewCommentResponseVO> addLinkReviewComment(
		EntityModel<ReviewCommentResponseVO> entityModel) {
		ReviewCommentResponseVO content = entityModel.getContent();
		assert content != null;
		return entityModel
			.add(
				linkTo(
					methodOn(ReviewCommentController.class)
						.readReviewComment(content.getId())
				).withSelfRel()
			)
			.addIf(
				content.getParentComment() != null,
				() -> linkTo(
					methodOn(ReviewCommentController.class)
						.readReviewComment(content.getParentComment())
				).withRel("parent")
			)
			.add(
				linkTo(
					methodOn(ReviewController.class)
						.readReview(content.getReviewId())
				).withRel("review")
			);
	}

	public EntityModel<ReviewCommentResponseVO> constrainHATEOAS(
		ReviewCommentResponseVO reviewCommentResponseVO) {

		return addLinkReviewComment(EntityModel.of(reviewCommentResponseVO));
	}

	public PagedModel<EntityModel<ReviewCommentResponseVO>> constrainHATEOASReviewComment(
		Page<ReviewCommentResponseVO> page,
		PagedResourcesAssembler<ReviewCommentResponseVO> assembler) {

		PagedModel<EntityModel<ReviewCommentResponseVO>> pagedModel = assembler.toModel(page);
		pagedModel.forEach(this::addLinkReviewComment);
		return pagedModel;
	}
}

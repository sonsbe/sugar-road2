package com.example.sugarroad2.util;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.example.sugarroad2.controller.*;
import com.example.sugarroad2.model.dto.request.RecommendationRequestDTO;
import com.example.sugarroad2.model.dto.response.*;
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
                        .checkRecommendation("R", reviewResponseVO.getId())
                ).withRel("recommendation")
            )
            .add(
                linkTo(
                    methodOn(RecommendationController.class)
                        .createRecommendation("R", reviewResponseVO.getId())
                ).withRel("createRecommendation")
            )
            .add(
                linkTo(
                    methodOn(RecommendationController.class)
                        .deleteRecommendation(
                            "R",
                            reviewResponseVO.getId()
                        )
                ).withRel("deleteRecommendation")
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
            )
            .add(
                linkTo(
                    methodOn(RecommendationController.class)
                        .checkRecommendation("C", postCommentResponseVO.getId())
                ).withRel("recommendation")
            )
            .add(
                linkTo(
                    methodOn(RecommendationController.class)
                        .createRecommendation("C", postCommentResponseVO.getId())
                ).withRel("createRecommendation")
            )
            .add(
                linkTo(
                    methodOn(RecommendationController.class)
                        .deleteRecommendation(
                            "R",
                            postCommentResponseVO.getId()
                        )
                ).withRel("deleteRecommendation")
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
            )
            .add(
                linkTo(
                    methodOn(RecommendationController.class)
                        .checkRecommendation("M", content.getId())
                ).withRel("recommendation")
            )
            .add(
                linkTo(
                    methodOn(RecommendationController.class)
                        .createRecommendation("M", content.getId())
                ).withRel("createRecommendation")
            )
            .add(
                linkTo(
                    methodOn(RecommendationController.class)
                        .deleteRecommendation(
                            "R",
                            content.getId()
                        )
                ).withRel("deleteRecommendation")
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

    public EntityModel<PostResponse> addLinkPost(
        EntityModel<PostResponse> entityModel) {
        PostResponse content = entityModel.getContent();
        assert content != null;
        return entityModel
            .add(
                linkTo(
                    methodOn(PostController.class)
                        .readById(content.getId())
                ).withSelfRel()
            )
            .add(
                linkTo(
                    methodOn(PostCommentController.class)
                        .readPostCommentPage(
                            content.getId(),
                            PageRequest.of(0, 5)
                        )
                ).withRel("reviewCommentFirstPage")
            )
            .add(
                linkTo(
                    methodOn(RecommendationController.class)
                        .checkRecommendation("P", content.getId())
                ).withRel("recommendation")
            )
            .add(
                linkTo(
                    methodOn(RecommendationController.class)
                        .createRecommendation("P", content.getId())
                ).withRel("createRecommendation")
            )
            .add(
                linkTo(
                    methodOn(RecommendationController.class)
                        .deleteRecommendation(
                            "P",
                            content.getId()
                        )
                ).withRel("deleteRecommendation")
            );
    }

	public EntityModel<PostResponse> constrainHATEOAS(
        PostResponse postResponse) {

		return addLinkPost(EntityModel.of(postResponse));
	}

	public PagedModel<EntityModel<PostResponse>> constrainHATEOASPost(
			Page<PostResponse> page,
			PagedResourcesAssembler<PostResponse> assembler) {

		PagedModel<EntityModel<PostResponse>> pagedModel = assembler.toModel(page);
		pagedModel.forEach(this::addLinkPost);
		return pagedModel;
	}

    public EntityModel<StoreResponseDTO> addLinkStore(
        EntityModel<StoreResponseDTO> entityModel) {
        StoreResponseDTO content = entityModel.getContent();
        assert content != null;
        return entityModel
            .add(
                linkTo(
                    methodOn(StoreController.class)
                        .readBy(content.getStoreId())
                ).withSelfRel()
            )
            .add(
                linkTo(
                    methodOn(ReviewController.class)
                        .readReviewPage(
                            content.getStoreId(),
                            PageRequest.of(0, 3))
                ).withRel("reviewFirstPage")
            )
            .add(
                linkTo(
                    methodOn(RecommendationController.class)
                        .checkRecommendation("S", content.getStoreId())
                ).withRel("recommendation")
            )
            .add(
                linkTo(
                    methodOn(RecommendationController.class)
                        .createRecommendation("S", content.getStoreId())
                ).withRel("createRecommendation")
            )
            .add(
                linkTo(
                    methodOn(RecommendationController.class)
                        .deleteRecommendation(
                            "S",
                            content.getStoreId()
                        )
                ).withRel("deleteRecommendation")
            );
    }

    public EntityModel<StoreResponseDTO> constrainHATEOAS(
        StoreResponseDTO response) {

        return addLinkStore(EntityModel.of(response));
    }

    public PagedModel<EntityModel<StoreResponseDTO>> constrainHATEOASStore(
        Page<StoreResponseDTO> page,
        PagedResourcesAssembler<StoreResponseDTO> assembler) {

        PagedModel<EntityModel<StoreResponseDTO>> pagedModel = assembler.toModel(page);
        pagedModel.forEach(this::addLinkStore);
        return pagedModel;
    }
}

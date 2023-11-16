package com.example.sugarroad2.controller;

import com.example.sugarroad2.model.dto.request.PostCommentRequestDTO;
import com.example.sugarroad2.model.dto.response.PostCommentResponseVO;
import com.example.sugarroad2.model.entity.Post;
import com.example.sugarroad2.model.entity.PostComment;
import com.example.sugarroad2.model.entity.Users;
import com.example.sugarroad2.service.PostCommentService;
import com.example.sugarroad2.service.PostService;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/post-comment")
@Slf4j
@RequiredArgsConstructor
public class PostCommentController {

	private final PagedResourcesAssembler<PostCommentResponseVO> pagedResourcesAssembler;

	@Autowired
	private PostCommentService postCommentService;

	@Autowired
	UsersService usersService;

	@Autowired
	PostService postService;

	@Autowired
	HATEOASUtil hateoasUtil;

	@PostMapping
	public ResponseEntity<EntityModel<PostCommentResponseVO>> createPostComment(
		@RequestBody PostCommentRequestDTO postCommentRequestDTO) {

		Users users = usersService.readById(postCommentRequestDTO.getUserId());
		Post post = postService.readById(postCommentRequestDTO.getPostId());
		PostComment postComment;

		if (postCommentRequestDTO.getParentComment() != null){
			PostComment parent = postCommentService.readById(postCommentRequestDTO.getParentComment());
			postComment = postCommentRequestDTO.toEntity(users, post, parent);
		}
		else {
			postComment = postCommentRequestDTO.toEntity(users, post);
		}

		postComment = postCommentService.create(postComment);
		PostCommentResponseVO postCommentResponseVO = new PostCommentResponseVO(postComment);
		EntityModel<PostCommentResponseVO> postCommentResponseVOEntityModel =
			hateoasUtil.constrainHATEOAS(postCommentResponseVO);

		return ResponseEntity.status(HttpStatus.CREATED).body(postCommentResponseVOEntityModel);
	}

	@GetMapping("/{id}")
	public ResponseEntity<EntityModel<PostCommentResponseVO>> readPostComment(
		@PathVariable("id") int id) {

		PostComment postComment = postCommentService.readById(id);
		PostCommentResponseVO postCommentResponseVO = new PostCommentResponseVO(postComment);
		EntityModel<PostCommentResponseVO> postCommentResponseVOEntityModel =
			hateoasUtil.constrainHATEOAS(postCommentResponseVO);
		return ResponseEntity.status(HttpStatus.CREATED).body(postCommentResponseVOEntityModel);
	}

	@GetMapping("/of/post/{postId}")
	public ResponseEntity<PagedModel<EntityModel<PostCommentResponseVO>>> readPostCommentPage(
		@PathVariable("postId") int postId,
		@PageableDefault(value = 5)Pageable pageable) {

		Post post = postService.readById(postId);
		Page<PostComment> postCommentPage = postCommentService.readPage(post, pageable);
		Page<PostCommentResponseVO> postCommentResponseVOPage = postCommentPage.map(PostCommentResponseVO::new);
		PagedModel<EntityModel<PostCommentResponseVO>> postCommentResponseVOPagedModel =
			hateoasUtil.constrainHATEOASPostComment(postCommentResponseVOPage, pagedResourcesAssembler);
		return ResponseEntity.status(HttpStatus.OK).body(postCommentResponseVOPagedModel);
	}

	@PutMapping("/{id}")
	public ResponseEntity<EntityModel<PostCommentResponseVO>> updatePostComment(
		@PathVariable("id") int id,
		@RequestBody PostCommentRequestDTO postCommentRequestDTO) {

		postCommentRequestDTO.setId(id);
		Users users = usersService.readById(postCommentRequestDTO.getUserId());
		Post post = postService.readById(postCommentRequestDTO.getPostId());
		PostComment postComment;

		if (postCommentRequestDTO.getParentComment() != null){
			PostComment parent = postCommentService.readById(postCommentRequestDTO.getParentComment());
			postComment = postCommentRequestDTO.toEntity(users, post, parent);
		}
		else {
			postComment = postCommentRequestDTO.toEntity(users, post);
		}
		postComment = postCommentService.update(postComment);
		PostCommentResponseVO postCommentResponseVO = new PostCommentResponseVO(postComment);
		EntityModel<PostCommentResponseVO> postCommentResponseVOEntityModel =
			hateoasUtil.constrainHATEOAS(postCommentResponseVO);
		return ResponseEntity.status(HttpStatus.CREATED).body(postCommentResponseVOEntityModel);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletePostComment(
		@PathVariable("id") int id) {

		postCommentService.deleteById(id);
		return ResponseEntity.status(HttpStatus.OK).body(null);
	}

}

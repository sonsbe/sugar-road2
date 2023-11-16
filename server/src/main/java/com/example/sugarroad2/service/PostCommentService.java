package com.example.sugarroad2.service;

import com.example.sugarroad2.model.entity.Post;
import com.example.sugarroad2.model.entity.PostComment;
import com.example.sugarroad2.repository.PostCommentRepository;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PostCommentService {

	@Autowired
	private PostCommentRepository repository;

	public PostComment create(PostComment entity){
		return repository.save(entity);
	}

	public PostComment readById(int id){
		return repository.findById(id).get();
	}

	public Page<PostComment> readPage(Post post, Pageable pageable){
		return repository.findByPostOrderByParentAscPostedDateAsc(post, pageable);
	}
	
	public PostComment update(PostComment entity){
		final Optional<PostComment> original = repository.findById(entity.getId());
		original.ifPresent(review -> {
			review.setContent(entity.getContent());
			review.setPostedDate(entity.getPostedDate());
			repository.save(review);
		});
		return repository.findById(entity.getId()).get();
	}

	public void delete(PostComment entity){
		repository.deleteById(entity.getId());
	}

	public void deleteById(int id){
		repository.deleteById(id);
	}
}

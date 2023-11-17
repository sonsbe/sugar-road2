package com.example.sugarroad2.service;

import com.example.sugarroad2.model.entity.Recommendation;
import com.example.sugarroad2.model.entity.Users;
import com.example.sugarroad2.repository.RecommendationRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RecommendationService {

	@Autowired
	private RecommendationRepository repository;

	public long countByReference(String referenceType, int referenceId){
		return repository.countByReferenceTypeAndReferenceId(referenceType, referenceId);
	}

	public boolean existsByUserAndReference(Users users, String referenceType, int referenceId){
		return repository.existsByUsersAndReferenceTypeAndReferenceId(users, referenceType, referenceId);
	}

	@Transactional
	public void create(Recommendation entity){
		repository.save(entity);
	}

	@Transactional
	public void delete(Recommendation entity){
		repository.deleteById(entity.getId());
	}

	@Transactional
	public void deleteById(int id){
		repository.deleteById(id);
	}

	@Transactional
	public void deleteByReference(String referenceType, int referenceId, Users users){

		repository.deleteByReferenceTypeAndReferenceIdAndUsers(referenceType, referenceId, users);
	}

}

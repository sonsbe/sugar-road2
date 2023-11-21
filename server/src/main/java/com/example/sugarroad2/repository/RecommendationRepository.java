package com.example.sugarroad2.repository;

import com.example.sugarroad2.model.entity.Recommendation;

import com.example.sugarroad2.model.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecommendationRepository extends JpaRepository<Recommendation, Integer> {
	public boolean existsByUsersAndReferenceTypeAndReferenceId(Users users, String referenceType, int referenceId);
	public long countByReferenceTypeAndReferenceId(String referenceType, int referenceId);
	public void deleteByReferenceTypeAndReferenceIdAndUsers(String referenceType, int referenceId, Users users);

}

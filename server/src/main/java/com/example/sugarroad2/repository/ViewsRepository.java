package com.example.sugarroad2.repository;

import com.example.sugarroad2.model.entity.Views;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ViewsRepository extends JpaRepository<Views, Integer> {
    public long countByReferenceTypeAndReferenceId(String referenceType, int referenceId);
}

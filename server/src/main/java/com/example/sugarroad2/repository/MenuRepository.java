package com.example.sugarroad2.repository;

import com.example.sugarroad2.model.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuRepository  extends JpaRepository<Menu, Integer> {

}

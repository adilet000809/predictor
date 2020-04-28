package com.example.predictor.repositories;

import com.example.predictor.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    //int countAll();
    Page<Category>  findAllByDeletedAtNull(Pageable pageable);
    List<Category> findAllByDeletedAtNull();
}

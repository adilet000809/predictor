package com.example.predictor.rest;

import com.example.predictor.entity.Category;
import com.example.predictor.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/admin/")
public class AdminRestController {

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping(path = "/category")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<Page<Category>> getAllItems(
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable
    ){

        Page<Category> page = categoryRepository.findAll(pageable);

        return new ResponseEntity<>(page, HttpStatus.OK);
    }

}

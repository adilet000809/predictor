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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(path = "/api/admin/")
public class AdminRestController {

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping(path = "/category")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<Page<Category>> getAllCategories(
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable
    ){

        Page<Category> page = categoryRepository.findAll(pageable);

        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @PostMapping(path = "/category/add")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<Category> addCategory(
            @RequestBody Map<String, String> category
            ){

        return new ResponseEntity<>(categoryRepository.save(new Category(category.get("name"))), HttpStatus.OK);

    }

}

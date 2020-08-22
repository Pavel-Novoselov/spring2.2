package com.geekbrains.july.market.controllers;

import com.geekbrains.july.market.entities.Category;
import com.geekbrains.july.market.entities.ResponseErrorRest;
import com.geekbrains.july.market.services.CategoriesService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/category")
@Api("Set of endpoints for CRUD operations for Categories")
public class RestCategoryController {
    @Autowired
    private CategoriesService categoriesService;

    @GetMapping
    public ResponseEntity<?> allCategories(){
        return new ResponseEntity<>(categoriesService.getAllCategories(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getCategory(@PathVariable Long id){
        Category category = categoriesService.getCategoryById(id);
        if (category == null){
            return new ResponseEntity<>(new ResponseErrorRest("No such category found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> saveCategory(@RequestBody Category category){
        final Category categorySaved = categoriesService.saveCategory(category);
        if (categorySaved == null){
            return new ResponseEntity<>(new ResponseErrorRest("Category not saved"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(categorySaved, HttpStatus.OK);
    }


}

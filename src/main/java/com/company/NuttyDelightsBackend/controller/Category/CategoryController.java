package com.company.NuttyDelightsBackend.controller.Category;

import com.company.NuttyDelightsBackend.dto.request.CategoryDTO;
import com.company.NuttyDelightsBackend.dto.response.CategoryResponseDTO;
import com.company.NuttyDelightsBackend.model.Category;
import com.company.NuttyDelightsBackend.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;
    @PostMapping("/createCategory")
    public ResponseEntity<CategoryResponseDTO> createCategory(@RequestBody CategoryDTO categoryDTO){
        return  ResponseEntity.ok(categoryService.createCategory(categoryDTO));
    }

    @PutMapping("/updateCategory/{id}")
    public ResponseEntity<CategoryResponseDTO> updateCategory(@PathVariable("id") String categoryId, @RequestBody CategoryDTO categoryDTO){
        return  ResponseEntity.ok(categoryService.updateCategory(categoryId , categoryDTO));
    }

    @DeleteMapping("/deleteCategory/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable("id") String categoryId){
        return ResponseEntity.ok(categoryService.deleteCategory(categoryId));
    }

    @GetMapping("/getAllCategories")
    public ResponseEntity<List<Category>> getAllCategories(){
        return  ResponseEntity.ok(categoryService.getAllCategories());
    }

}


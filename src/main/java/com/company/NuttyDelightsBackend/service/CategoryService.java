package com.company.NuttyDelightsBackend.service;

import com.company.NuttyDelightsBackend.dto.request.CategoryDTO;
import com.company.NuttyDelightsBackend.dto.response.CategoryResponseDTO;
import com.company.NuttyDelightsBackend.model.Category;

import java.util.List;

public interface CategoryService {
    CategoryResponseDTO createCategory(CategoryDTO categoryDTO);
    CategoryResponseDTO updateCategory(String categoryId , CategoryDTO categoryDTO);
    String deleteCategory(String categoryId);
    List<Category> getAllCategories();

}

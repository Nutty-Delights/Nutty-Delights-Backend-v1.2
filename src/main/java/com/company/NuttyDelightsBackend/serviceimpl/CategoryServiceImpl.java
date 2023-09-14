package com.company.NuttyDelightsBackend.serviceimpl;

import com.company.NuttyDelightsBackend.dto.request.CategoryDTO;
import com.company.NuttyDelightsBackend.dto.response.CategoryResponseDTO;
import com.company.NuttyDelightsBackend.model.Category;
import com.company.NuttyDelightsBackend.repository.CategoryRepository;
import com.company.NuttyDelightsBackend.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;


    @Override
    public CategoryResponseDTO createCategory(CategoryDTO categoryDTO) {
        System.out.println(categoryDTO);
        try{
            Category category = Category.builder().
                    categoryName(categoryDTO.getCategoryName()).
                    categoryImageUrl(categoryDTO.getCategoryImageUrl()).
                    categoryType(categoryDTO.getCategoryType()).build();

            System.out.println("category generated : " + category);
            categoryRepository.save(category);
            return  new CategoryResponseDTO(category , "Category Created Successfully");

        }
        catch (Exception e){
            e.printStackTrace();
        }


        CategoryResponseDTO categoryResponseDTO = new CategoryResponseDTO("Error occurred");
        return categoryResponseDTO;
    }

    @Override
    public CategoryResponseDTO updateCategory(String categoryId , CategoryDTO categoryDTO) {
        try {
            Category category = categoryRepository.findById(categoryId).get();
            category.setCategoryId(categoryId);
            category.setCategoryName(categoryDTO.getCategoryName());
            category.setCategoryType(categoryDTO.getCategoryType());
            category.setCategoryImageUrl(categoryDTO.getCategoryImageUrl());
            System.out.println(category);
            categoryRepository.save(category);


            return  new CategoryResponseDTO(category , "Category Updated Successfully");

        }
        catch (Exception e){
            e.printStackTrace();
        }


        CategoryResponseDTO categoryResponseDTO = new CategoryResponseDTO("Error occurred");
        return categoryResponseDTO;
    }

    @Override
    public String deleteCategory(String categoryId) {
        try {
            categoryRepository.deleteById(categoryId);
            return categoryId;
        }
        catch (Exception e)
        {
            e.printStackTrace();

        }

        return "Failed to delete category";
    }

    @Override
    public List<Category> getAllCategories() {
        try{
                List<Category> categories = categoryRepository.findAll();
                System.out.println("Fetched the categories");
                return categories;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }

}

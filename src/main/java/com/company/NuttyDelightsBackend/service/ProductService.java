package com.company.NuttyDelightsBackend.service;

import com.company.NuttyDelightsBackend.dto.request.CategoryDTO;
import com.company.NuttyDelightsBackend.dto.request.ProductDTO;
import com.company.NuttyDelightsBackend.model.Category;
import com.company.NuttyDelightsBackend.model.Product;

import java.util.List;

public interface ProductService {
    Product createProduct(ProductDTO productDTO);
    Product updateProduct(String productId , ProductDTO productDTO);
    String deleteProduct(String productId);
    List<Product> getAllProductsByCategoryId(String categoryId);
    List<Product> getAllProducts();

    Product getProductById(String productId);
}

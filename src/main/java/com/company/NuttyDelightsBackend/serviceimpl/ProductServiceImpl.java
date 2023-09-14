package com.company.NuttyDelightsBackend.serviceimpl;

import com.company.NuttyDelightsBackend.dto.request.ProductDTO;
import com.company.NuttyDelightsBackend.model.Product;
import com.company.NuttyDelightsBackend.repository.ProductRepository;
import com.company.NuttyDelightsBackend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductServiceImpl implements  ProductService{
    @Autowired
    ProductRepository productRepository;


    @Override
    public Product createProduct(ProductDTO productDTO) {
        try {
            Product product = Product.builder().
                    productCategoryId(productDTO.getProductCategoryId()).
                    productVariants(productDTO.getProductVariants()).
//                    productDiscount(productDTO.getProductDiscount()).
                    productImageUrl(productDTO.getProductImageUrl()).
                    productName(productDTO.getProductName()).
//                    productPrice(productDTO.getProductPrice()).
                    productDescription(productDTO.getProductDescription()).
//                    productStockCount(productDTO.getProductStockCount()).
                    productNumberOfReviews(productDTO.getProductNumberOfReviews()).
                    build();
            productRepository.save(product);
            return product;
        }
        catch (Exception e){
            e.printStackTrace();

        }

        return new Product();
    }

    @Override
    public Product updateProduct(String productId, ProductDTO productDTO) {
        try{
            Product product = productRepository.findById(productId).get();
            product.setProductId(productId);
            product.setProductDescription(productDTO.getProductDescription());
            product.setProductCategoryId(productDTO.getProductCategoryId());
            product.setProductVariants(productDTO.getProductVariants());
//            product.setProductDiscount(productDTO.getProductDiscount());
//            product.setProductPrice(productDTO.getProductPrice());
            product.setProductImageUrl(productDTO.getProductImageUrl());
            product.setProductNumberOfReviews(productDTO.getProductNumberOfReviews());
            product.setProductName(productDTO.getProductName());
//            product.setProductStockCount(productDTO.getProductStockCount());

            productRepository.save(product);

            return product;
        }

        catch (Exception e){
            e.printStackTrace();

        }

        return new Product();
    }

    @Override
    public String deleteProduct(String productId) {
        try{
            System.out.println("inside delete");
            System.out.println(productId);
            productRepository.deleteById(productId);
            return productId;
        }
        catch (Exception e){
            e.printStackTrace();

        }

        return "";

    }

    @Override
    public List<Product> getAllProductsByCategoryId(String categoryId) {
        try{
            System.out.println("categoryId: " + categoryId + " " + categoryId.getClass().getSimpleName());

            if(categoryId.equals("null") || categoryId.equals("") || categoryId.equalsIgnoreCase("undefined")) {
                System.out.println("Inside if block");
                List<Product> products = productRepository.findAll();
                System.out.println(products);
                return products;
            }


            List<Product> productsFilteredByCategory = productRepository.findByProductCategoryId(categoryId).get();
            return productsFilteredByCategory;
        }
        catch (Exception e){
            e.printStackTrace();

        }

        return null;
    }

    @Override
    public List<Product> getAllProducts() {
        try{
            List<Product>  productList = productRepository.findAll();
            return productList;
        }
        catch (Exception e){
            e.printStackTrace();

        }

        return null;
    }

    @Override
    public Product getProductById(String productId) {
        try{
            Product product = productRepository.findById(productId).get();
            return product;
        }
        catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("Product not found");

        }

//        return  null;
    }
}

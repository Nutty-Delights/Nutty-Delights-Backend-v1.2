package com.company.NuttyDelightsBackend.controller.Product;
import com.company.NuttyDelightsBackend.dto.request.ProductDTO;
import com.company.NuttyDelightsBackend.model.Product;
import com.company.NuttyDelightsBackend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;
    @PostMapping("/createProduct")
    public ResponseEntity<Product> createProduct(@RequestBody ProductDTO productDTO){
        return  ResponseEntity.ok(productService.createProduct(productDTO));
    }

    @PutMapping("/updateProduct/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable("id") String productId, @RequestBody ProductDTO productDTO){
        return  ResponseEntity.ok(productService.updateProduct(productId , productDTO));
    }

    @DeleteMapping("/deleteProduct/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable("id") String productId){
        return ResponseEntity.ok(productService.deleteProduct(productId));
    }

    @GetMapping("/getAllProducts/{categoryId}")
    public ResponseEntity<List<Product>> getAllProductsByCategory(@PathVariable("categoryId") String categoryId  ){
        return  ResponseEntity.ok(productService.getAllProductsByCategoryId(categoryId));
    }
    @GetMapping("/getAllProducts")
    public ResponseEntity<List<Product>> getAllProducts(){
        return  ResponseEntity.ok(productService.getAllProducts());
    }
    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProduct(@PathVariable("productId") String productId){
        return  ResponseEntity.ok(productService.getProductById(productId));
    }
}

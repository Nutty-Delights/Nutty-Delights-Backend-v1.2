package com.company.NuttyDelightsBackend.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
@Document("Products")
public class Product {
    @Id
    private  String productId;
    private String productCategoryId;
    private String productName;
    private String productDescription;
    private String productImageUrl = "";
    private int productNumberOfReviews = 0;
//    private Double productPrice = 0.0;
//    private Double productDiscount = 0.0;
//    private int productStockCount = 0;
    private ArrayList<Variants>  productVariants =new ArrayList<>();

    private List<String> reviews = new ArrayList<>();


}

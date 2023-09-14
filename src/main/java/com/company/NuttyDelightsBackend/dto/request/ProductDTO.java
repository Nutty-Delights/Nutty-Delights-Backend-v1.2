package com.company.NuttyDelightsBackend.dto.request;

import com.company.NuttyDelightsBackend.model.Variants;
import lombok.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class ProductDTO {
    private String productCategoryId;
    private String productName;
    private String productDescription;

    private String productImageUrl = "";
    private int productNumberOfReviews = 0;

    private ArrayList<Variants>  productVariants =new ArrayList<>();
    //    private Double productPrice = 0.0;
//    private Double productDiscount = 0.0;
//    private int productStockCount = 0;
    private List<String> reviews = new ArrayList<>();
}

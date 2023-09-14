package com.company.NuttyDelightsBackend.dto.response;

import com.company.NuttyDelightsBackend.model.Category;
import lombok.*;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResponseDTO {
    private String categoryId;
    private String categoryName;
    private  String categoryType;
    private String categoryImageUrl;
    private String responseMessage;

    public CategoryResponseDTO(Category category , String responseMessage){
        this.categoryId = category.getCategoryId();
        this.categoryName = category.getCategoryName();
        this.categoryType = category.getCategoryType();
        this.categoryImageUrl = category.getCategoryImageUrl();
        this.responseMessage = responseMessage;

    }
    public CategoryResponseDTO( String responseMessage){
        this.responseMessage = responseMessage;

    }
}

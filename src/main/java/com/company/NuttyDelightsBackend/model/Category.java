package com.company.NuttyDelightsBackend.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.annotation.processing.Generated;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Document("Categories")
public class Category {

    @Id
    private String categoryId;
    private String categoryName;
    private  String categoryType;
    private String categoryImageUrl;

}

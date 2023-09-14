package com.company.NuttyDelightsBackend.dto.request;

import lombok.*;
import org.springframework.stereotype.Component;
//@Indexed(unique = true)
@Component
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class CategoryDTO {
    private String categoryName;
    private  String categoryType;
    private String categoryImageUrl;
}

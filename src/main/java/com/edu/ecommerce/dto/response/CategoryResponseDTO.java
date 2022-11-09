package com.edu.ecommerce.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)

public class CategoryResponseDTO {

    private Long id;
    private String categoryName;
    private String description;
    private String imageUrl;

}
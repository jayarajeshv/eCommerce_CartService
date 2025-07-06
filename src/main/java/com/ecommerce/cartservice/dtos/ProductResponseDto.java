package com.ecommerce.cartservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductResponseDto {
    private Long id;
    private String title;
    private Double price;
    private String description;
    private CategoryResponseDto category;
}

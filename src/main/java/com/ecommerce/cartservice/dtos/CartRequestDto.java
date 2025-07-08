package com.ecommerce.cartservice.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CartRequestDto {
    private List<Long> productIds; // List of product IDs
    private List<Integer> quantities; // List of quantities for each product
}

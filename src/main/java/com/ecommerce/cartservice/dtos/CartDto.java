package com.ecommerce.cartservice.dtos;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class CartDto {
    private Long cartId;
    private Long userId;
    private List<Long> productItems;
    private double totalPrice;
}

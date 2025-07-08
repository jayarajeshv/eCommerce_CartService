package com.ecommerce.cartservice.models;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@Entity
public class Cart extends BaseModel{
    private Long userId;
    private List<Long> productIds; // Map of product ID to quantity
    private List<Integer> quantities; // List of quantities for each product
    private Double totalPrice;
}

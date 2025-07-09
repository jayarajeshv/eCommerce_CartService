package com.ecommerce.cartservice.models;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@Entity
public class Cart extends BaseModel {
    private Long userId;

    @ElementCollection
    private List<Long> productIds;

    @ElementCollection
    private List<Integer> quantities;

    private Double totalPrice;
}

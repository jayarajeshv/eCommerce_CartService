package com.ecommerce.cartservice.services;

import com.ecommerce.cartservice.dtos.CartDto;
import com.ecommerce.cartservice.exceptions.CartNotFoundException;

public interface ICartService {
    CartDto addToCart(Long userId, Long productId, int quantity);

    CartDto updateCart(Long cartId, Long userId, Long productId, int quantity) throws CartNotFoundException;

    void deleteCart(Long cartId) throws CartNotFoundException;
}

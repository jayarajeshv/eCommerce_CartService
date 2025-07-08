package com.ecommerce.cartservice.services;

import com.ecommerce.cartservice.dtos.CartResponseDto;
import com.ecommerce.cartservice.exceptions.CartNotFoundException;

import java.util.List;

public interface ICartService {
    CartResponseDto addToCart(List<Long> productItems, List<Integer> quantities);
    CartResponseDto updateCart(Long cartId, List<Long> productItems, List<Integer> quantities) throws CartNotFoundException;

    void deleteCart(Long cartId) throws CartNotFoundException;
}

package com.ecommerce.cartservice.controllers;

import com.ecommerce.cartservice.dtos.CartRequestDto;
import com.ecommerce.cartservice.dtos.CartResponseDto;
import com.ecommerce.cartservice.services.ICartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/carts")
public class CartController {

    private final ICartService cartService;

    public CartController(ICartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/add")
    public ResponseEntity<CartResponseDto> addToCart(@RequestBody CartRequestDto cartRequestDto) {
        return new ResponseEntity<>(cartService.addToCart(cartRequestDto.getProductIds(), cartRequestDto.getQuantities()), HttpStatus.CREATED);
    }
}

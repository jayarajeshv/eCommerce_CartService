package com.ecommerce.cartservice.services;

import com.ecommerce.cartservice.dtos.CartDto;
import com.ecommerce.cartservice.dtos.ProductResponseDto;
import com.ecommerce.cartservice.exceptions.CartNotFoundException;
import com.ecommerce.cartservice.models.Cart;
import com.ecommerce.cartservice.repositories.CartRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService implements ICartService {

    private final CartRepository cartRepository;
    private final RestTemplate restTemplate;

    public CartService(CartRepository cartRepository, RestTemplate restTemplate) {
        this.cartRepository = cartRepository;
        this.restTemplate = restTemplate;
    }

    @Override
    public CartDto addToCart(Long userId, Long productId, int quantity) {
        Cart cart = new Cart();
        cart.setUserId(userId);
        List<Long> productItems = new ArrayList<>();
        productItems.add(productId);
        cart.setProductItems(productItems); // Assuming productItems is a list of product IDs
        cart.setTotalPrice(calculateTotalPrice(productItems)); // Assuming a fixed price of 10.0 for simplicity
        Cart savedCart = cartRepository.save(cart);
        return fromCart(savedCart);
    }

    @Override
    public CartDto updateCart(Long cartId, Long userId, Long productId, int quantity) throws CartNotFoundException {
        Cart updatedCart = cartRepository.findById(cartId)
                .orElseThrow(() -> new CartNotFoundException("Cart not found for user ID: " + userId));
        List<Long> productItems = updatedCart.getProductItems();
        productItems.add(productId);
        updatedCart.setProductItems(productItems);
        updatedCart.setTotalPrice(quantity * 100); // Assuming a fixed price of 10.0 for simplicity
        return fromCart(updatedCart);
    }

    @Override
    public void deleteCart(Long cartId) throws CartNotFoundException {
        if (!cartRepository.existsById(cartId)) {
            throw new CartNotFoundException("Cart not found with ID: " + cartId);
        }
        cartRepository.deleteById(cartId);
    }


    private CartDto fromCart(Cart cart) {
        CartDto cartDto = new CartDto();
        cartDto.setCartId(cart.getId());
        cartDto.setUserId(cart.getUserId());
        cartDto.setProductItems(cart.getProductItems());
        cartDto.setTotalPrice(cart.getTotalPrice());
        return cartDto;
    }

    private Double calculateTotalPrice(List<Long> productItems) {
        Double totalPrice = 0.0;
        for (int i = 0; i < productItems.size(); i++) {
            ProductResponseDto productResponseDto = restTemplate.getForObject("http://localhost:8080/products/" + productItems.get(i), ProductResponseDto.class);
            if (productResponseDto != null) {
                totalPrice += productResponseDto.getPrice();
            }
        }
        return totalPrice * 100; // Assuming a fixed price of 100 for each product
    }
}

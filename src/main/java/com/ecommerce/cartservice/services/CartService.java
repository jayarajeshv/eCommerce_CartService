package com.ecommerce.cartservice.services;

import com.ecommerce.cartservice.dtos.CartResponseDto;
import com.ecommerce.cartservice.exceptions.CartNotFoundException;
import com.ecommerce.cartservice.models.Cart;
import com.ecommerce.cartservice.models.Product;
import com.ecommerce.cartservice.repositories.CartRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class CartService implements ICartService {

    private final CartRepository cartRepository;
    private final WebClient.Builder webClient;



    public CartService(CartRepository cartRepository, WebClient.Builder webClient) {
        this.cartRepository = cartRepository;
        this.webClient = webClient;
    }

    @Override
    public CartResponseDto addToCart(List<Long> productItems, List<Integer> quantities) {
        Cart cart = new Cart();
        cart.setProductIds(productItems);
        cart.setQuantities(quantities);
        cart.setTotalPrice(calculateTotalPrice(productItems,quantities));
        Cart savedCart = cartRepository.save(cart);
        return fromCart(savedCart);
    }

    @Override
    public CartResponseDto updateCart(Long cartId, List<Long> productItems, List<Integer> quantities) throws CartNotFoundException {
        Cart updatedCart = cartRepository.findById(cartId)
                .orElseThrow(() -> new CartNotFoundException("Cart not found for cart ID: " + cartId));
        updatedCart.setProductIds(productItems);
        updatedCart.setQuantities(quantities);
        updatedCart.setTotalPrice(calculateTotalPrice(productItems, quantities));
        Cart savedCart = cartRepository.save(updatedCart);
        return fromCart(savedCart);
    }

    @Override
    public void deleteCart(Long cartId) throws CartNotFoundException {
        if (!cartRepository.existsById(cartId)) {
            throw new CartNotFoundException("Cart not found with ID: " + cartId);
        }
        cartRepository.deleteById(cartId);
    }

    private CartResponseDto fromCart(Cart cart) {
        CartResponseDto cartDto = new CartResponseDto();
        cartDto.setCartId(cart.getId());
        cartDto.setProductIds(cart.getProductIds());
        cartDto.setQuantities(cart.getQuantities());
        cartDto.setTotalPrice(cart.getTotalPrice());
        return cartDto;
    }

    private Double calculateTotalPrice(List<Long> productItems, List<Integer> quantities) {
        double totalPrice = 0;
        int productCount = productItems.size();
        for (int i = 0; i < productCount; i++) {
            Long productId = productItems.get(i);
            Integer quantity = quantities.get(i);
            Product product = webClient.build()
                    .get()
                    .uri("http://PRODUCTSERVICE/products/{id}", productId)
                    .retrieve()
                    .bodyToMono(Product.class)
                    .block();
            // Blocking call to get the product details

            if (product == null) {
                throw new RuntimeException("Product not found for ID: " + productId);
            }
            totalPrice += product.getPrice() * quantity;
        }
        return totalPrice;
    }
}
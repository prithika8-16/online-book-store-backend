package com.bookstore.bookstore.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bookstore.bookstore.entity.Cart;
import com.bookstore.bookstore.entity.CartItem;
import com.bookstore.bookstore.service.CartService;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    // Add book to cart
    @PostMapping("/add")
    public ResponseEntity<CartItem> addToCart(
            @RequestParam Long userId,
            @RequestParam Long bookId,
            @RequestParam int quantity) {

        CartItem cartItem = cartService.addToCart(userId, bookId, quantity);

        return ResponseEntity.ok(cartItem);
    }

    // Get user's cart
    @GetMapping("/{userId}")
    public ResponseEntity<Cart> getCart(@PathVariable Long userId) {

        Cart cart = cartService.getCart(userId);

        return ResponseEntity.ok(cart);
    }

    // Update cart item quantity
    @PutMapping("/item/{cartItemId}")
    public ResponseEntity<CartItem> updateQuantity(
            @PathVariable Long cartItemId,
            @RequestParam int quantity) {

        CartItem cartItem = cartService.updateQuantity(cartItemId, quantity);

        return ResponseEntity.ok(cartItem);
    }

    // Remove item from cart
    @DeleteMapping("/item/{cartItemId}")
    public ResponseEntity<String> removeFromCart(
            @PathVariable Long cartItemId) {

        cartService.removeFromCart(cartItemId);

        return ResponseEntity.ok("Item removed from cart successfully");
    }
}

package com.bookstore.bookstore.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookstore.bookstore.entity.Cart;
import com.bookstore.bookstore.entity.CartItem;
import com.bookstore.bookstore.entity.Book;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    Optional<CartItem> findByCartAndBook(Cart cart, Book book);
}

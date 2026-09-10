
package com.bookstore.bookstore.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bookstore.bookstore.entity.Book;
import com.bookstore.bookstore.entity.Cart;
import com.bookstore.bookstore.entity.CartItem;
import com.bookstore.bookstore.entity.Order;
import com.bookstore.bookstore.entity.OrderItem;
import com.bookstore.bookstore.entity.User;
import com.bookstore.bookstore.repository.BookRepository;
import com.bookstore.bookstore.repository.CartRepository;
import com.bookstore.bookstore.repository.OrderRepository;
import com.bookstore.bookstore.repository.UserRepository;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final CartRepository cartRepository;

    public OrderService(
            OrderRepository orderRepository,
            UserRepository userRepository,
            BookRepository bookRepository,
            CartRepository cartRepository) {

        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.cartRepository = cartRepository;
    }

    @Transactional
    public Order placeOrder(Long userId, Long bookId, int quantity) {

        // Find user
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Find book
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        // Validate quantity
        if (quantity <= 0) {
            throw new RuntimeException("Quantity must be greater than 0");
        }

        // Check stock
        if (book.getStock() < quantity) {
            throw new RuntimeException("Not enough stock available");
        }

        // Find user's cart
        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        // Find matching cart item
        CartItem cartItem = cart.getItems()
                .stream()
                .filter(item ->
                        item.getBook().getId().equals(bookId))
                .findFirst()
                .orElseThrow(() ->
                        new RuntimeException("Book not found in cart"));

        // Make sure requested quantity is not greater
        // than the quantity in the cart
        if (cartItem.getQuantity() < quantity) {
            throw new RuntimeException(
                    "Order quantity is greater than cart quantity");
        }

        // Create order
        Order order = new Order();

        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("PLACED");

        // Create order item
        OrderItem orderItem = new OrderItem();

        orderItem.setOrder(order);
        orderItem.setBook(book);
        orderItem.setQuantity(quantity);
        orderItem.setPrice(book.getPrice());

        // Calculate total
        double total = book.getPrice() * quantity;

        order.setTotalAmount(total);

        // Add item to order
        order.getItems().add(orderItem);

        // Reduce stock
        book.setStock(book.getStock() - quantity);

        // Save updated book
        bookRepository.save(book);

        // Remove purchased quantity from cart
        if (cartItem.getQuantity() == quantity) {

            cart.getItems().remove(cartItem);

        } else {

            cartItem.setQuantity(
                    cartItem.getQuantity() - quantity
            );
        }

        // Save cart
        cartRepository.save(cart);

        // Save order
        return orderRepository.save(order);
    }

    // Get all orders for a user
    public List<Order> getUserOrders(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return orderRepository.findByUser(user);
    }

    // Get order by ID
    public Order getOrderById(Long orderId) {

        return orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }
}

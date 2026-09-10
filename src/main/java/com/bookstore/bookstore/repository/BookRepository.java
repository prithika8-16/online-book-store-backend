package com.bookstore.bookstore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookstore.bookstore.entity.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

    // Return only books that are active
    List<Book> findByActiveTrue();
}

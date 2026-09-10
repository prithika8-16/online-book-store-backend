package com.bookstore.bookstore.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.bookstore.bookstore.entity.Book;
import com.bookstore.bookstore.repository.BookRepository;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    // Get only active books
    public List<Book> getAllBooks() {
        return bookRepository.findByActiveTrue();
    }

    // Get book by ID
    public Optional<Book> getBookById(Long id) {

        Optional<Book> book = bookRepository.findById(id);

        if (book.isPresent() && book.get().isActive()) {
            return book;
        }

        return Optional.empty();
    }

    // Add a new book
    public Book addBook(Book book) {

        // New books are active by default
        book.setActive(true);

        return bookRepository.save(book);
    }

    // Update a book
    public Book updateBook(Long id, Book bookDetails) {

        Book book = bookRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Book not found"));

        book.setTitle(bookDetails.getTitle());
        book.setAuthor(bookDetails.getAuthor());
        book.setDescription(bookDetails.getDescription());
        book.setPrice(bookDetails.getPrice());
        book.setCategory(bookDetails.getCategory());
        book.setImageUrl(bookDetails.getImageUrl());
        book.setStock(bookDetails.getStock());

        return bookRepository.save(book);
    }

    // Permanently delete a book when possible
    public void deleteBook(Long id) {

        if (!bookRepository.existsById(id)) {
            throw new RuntimeException("Book not found");
        }

        bookRepository.deleteById(id);
    }
}

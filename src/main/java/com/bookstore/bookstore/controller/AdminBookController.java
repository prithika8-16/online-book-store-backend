
package com.bookstore.bookstore.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bookstore.bookstore.entity.Book;
import com.bookstore.bookstore.repository.BookRepository;

@RestController
@RequestMapping("/api/admin/books")
public class AdminBookController {

    private final BookRepository bookRepository;

    public AdminBookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    // =========================
    // ADD BOOK
    // =========================

    @PostMapping
    public ResponseEntity<Book> addBook(
            @RequestBody Book book) {

        Book savedBook = bookRepository.save(book);

        return ResponseEntity.ok(savedBook);
    }


    // =========================
    // GET ALL BOOKS
    // =========================

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {

        return ResponseEntity.ok(
                bookRepository.findAll()
        );
    }


    // =========================
    // UPDATE BOOK
    // =========================

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(
            @PathVariable Long id,
            @RequestBody Book book) {

        Book existingBook =
                bookRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Book not found"
                                )
                        );

        existingBook.setTitle(book.getTitle());
        existingBook.setAuthor(book.getAuthor());
        existingBook.setDescription(book.getDescription());
        existingBook.setPrice(book.getPrice());
        existingBook.setCategory(book.getCategory());
        existingBook.setImageUrl(book.getImageUrl());
        existingBook.setStock(book.getStock());

        Book updatedBook =
                bookRepository.save(existingBook);

        return ResponseEntity.ok(updatedBook);
    }


    // =========================
    // DELETE BOOK
    // =========================

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBook(
            @PathVariable Long id) {

        OptionalBookCheck:
        {
            if (!bookRepository.existsById(id)) {

                return ResponseEntity
                        .notFound()
                        .build();
            }
        }

        try {

            bookRepository.deleteById(id);

            return ResponseEntity.ok(
                    "Book deleted successfully"
            );

        } catch (Exception e) {

            // Book is probably referenced by
            // an existing order.

            Book book =
                    bookRepository.findById(id)
                            .orElse(null);

            if (book != null) {

                book.setStock(0);

                bookRepository.save(book);

                return ResponseEntity.ok(
                        "Book has existing orders, so it was removed from sale."
                );
            }

            return ResponseEntity
                    .internalServerError()
                    .body(
                            "Unable to delete book: "
                                    + e.getMessage()
                    );
        }
    }
}

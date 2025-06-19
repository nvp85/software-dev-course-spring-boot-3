package com.example.springBoot2.controllers;

import com.example.springBoot2.models.Book;
import com.example.springBoot2.repositories.BookRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/books")
public class BookController {
    private final BookRepository bookRepository;

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping
    public ResponseEntity <List<Book>>getAllBooks() {
        return ResponseEntity.status(HttpStatus.OK).body(bookRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody Book newBook) {
        // TODO: validate the input
        Book savedBook = bookRepository.save(newBook);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBook);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable int id) {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(book.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable int id, @RequestBody Book updatedBook) {
        // TODO: validate the input
        Optional <Book> currentBook = bookRepository.findById(id);
        if (currentBook.isPresent()) {
            Book book = currentBook.get();
            book.setAuthor(updatedBook.getAuthor());
            book.setName(updatedBook.getName());
            book.setYear(updatedBook.getYear());
            book.setPages(updatedBook.getPages());
            return ResponseEntity.status(HttpStatus.OK).body(bookRepository.save(book));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable int id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        return ResponseEntity.notFound().build();
    }
}

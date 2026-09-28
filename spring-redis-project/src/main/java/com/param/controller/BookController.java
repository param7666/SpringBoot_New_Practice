package com.param.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.param.entity.Book;
import com.param.service.BookService;

import lombok.RequiredArgsConstructor;

@RestController 
@RequestMapping("/book")
@RequiredArgsConstructor 
public class BookController {
    
    private final BookService service;


    @GetMapping("/{id}")
    public Book fetchBook(@PathVariable Long id) {
        return service.findBookById(id);
    }

    @PostMapping 
    public Book saveBook(@RequestBody Book book) {
        return service.addBooks(book);
    }

    @PutMapping("/{id}")
    public Book updateBookDetails(@RequestBody Book b,@PathVariable  Long id){
        return service.updateBook(b, id);
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable Long id) {
        return service.deleteBook(id);
    }
}
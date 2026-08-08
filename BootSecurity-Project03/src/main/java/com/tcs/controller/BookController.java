package com.tcs.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tcs.entity.Book;
import com.tcs.service.BookService;

import lombok.RequiredArgsConstructor;
@Controller
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookController {

	private final BookService bookService;
	
    // GET /bookApp/book
    @GetMapping
    public String listBooks(Map<String, Object> map) {
        map.put("books", bookService.getAllBooks());
        return "book/list";
    }

    // GET /bookApp/book/new
    @GetMapping("/new")
    public String showAddForm(Map<String, Object> map) {
        map.put("book", new Book());
        return "book/form";
    }

    // POST /bookApp/book
    @PostMapping
    public String addBook(@ModelAttribute Book book) {
        bookService.addBook(book);
        return "redirect:/book";
    }

    // GET /bookApp/book/edit/{id}
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Map<String, Object> map) {
        map.put("book", bookService.getBookById(id));
        return "book/form";
    }

    // POST /bookApp/book/update/{id}
    @PostMapping("/update/{id}")
    public String updateBook(@PathVariable Long id, @ModelAttribute Book book) {
        bookService.updateBook(id, book);
        return "redirect:/book";
    }

    // GET /bookApp/book/delete/{id}
    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return "redirect:/book";
    }

    // GET /bookApp/book/search?keyword=
    @GetMapping("/search")
    public String searchBooks(@RequestParam String keyword, Map<String, Object> map) {
        map.put("books", bookService.searchByTitle(keyword));
        return "book/list";
    }
}
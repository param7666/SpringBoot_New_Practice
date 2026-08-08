package com.tcs.service;

import java.util.List;

import com.tcs.entity.Book;

public interface BookService {
	Book addBook(Book book);

    Book updateBook(Long id, Book book);

    void deleteBook(Long id);

    Book getBookById(Long id);

    List<Book> getAllBooks();

    List<Book> searchByTitle(String title);

    List<Book> searchByAuthor(String author);

    List<Book> searchByCategory(String category);

    boolean isIsbnExists(String isbn);
}

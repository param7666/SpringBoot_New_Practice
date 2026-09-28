package com.param.service;

import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.param.entity.Book;
import com.param.repository.BookRepository;

import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
@CacheConfig (cacheNames = "books")
public class BookService {

    private final BookRepository bookRepository;



    @CachePut (key = "#result.id")
    public Book addBooks(Book book){
        return bookRepository.save(book);
    }

    @Cacheable(key = "#bookId")
    public Book findBookById(Long bookId){
        System.out.println("Book Fetched From DB "+LocalDateTime.now());
        return bookRepository.findById(bookId).orElseThrow(()-> new RuntimeException("Book Not found..."));
    }


    @CachePut(key = "#id")
    public Book updateBook(Book book, Long id) {
        Book existing=bookRepository.findById(id).orElseThrow(()-> new RuntimeException("Invalid book Id"));
        existing.setName(book.getName());
        existing.setAuthor(book.getAuthor());
        existing.setPrice(book.getPrice());
        return bookRepository.save(existing);
    }

    @CacheEvict(key = "#id")
    public String deleteBook(Long id){
        if(bookRepository.existsById(id)){
            bookRepository.deleteById(id);
            return "Book deleted From DB with id "+id;
        }
        throw new RuntimeException("Invalid book id");
    }




}

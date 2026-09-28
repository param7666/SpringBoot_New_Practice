package com.param.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.param.entity.Book;

public interface BookRepository extends JpaRepository<Book,Long> {
    
    
}
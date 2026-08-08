package com.tcs.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tcs.entity.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
	
	public Optional<Book> findByIsbn(String isbn);
	
	public List<Book> findByTitleContainingIgnoreCase(String title);
	
	public List<Book> findByAuthorContainingIgnoreCase(String author);
	
	public List<Book> findByCategoryIgnoreCase(String category);
	
	public boolean existsByIsbn(String isbn);

}

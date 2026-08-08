package com.tcs.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tcs.entity.Book;
import com.tcs.repository.BookRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService{
	
	private final BookRepository repo;

	@Override
	public Book addBook(Book book) {
		if(repo.existsByIsbn(book.getIsbn())) {
			throw new IllegalArgumentException("Book with this ISBN already exists");
		}
		book.setAvailableCopies(book.getAvailableCopies());
		Book b=repo.save(book);
		return b;
	}

	@Override
	public Book updateBook(Long id, Book book) {
		Optional<Book> b=repo.findById(id);
		if(b.isEmpty()) throw new RuntimeException("Invalid Book id");
		else {
			Book existing=b.get();
			existing.setTitle(book.getTitle());
	        existing.setAuthor(book.getAuthor());
	        existing.setCategory(book.getCategory());
	        existing.setTotalCopies(book.getTotalCopies());
	        existing.setAvailableCopies(book.getAvailableCopies());
	        return repo.save(existing);
		}
	}

	@Override
	public void deleteBook(Long id) {
		Book existing = getBookById(id);
        repo.delete(existing);
		
	}

	@Override
	@Transactional(readOnly = true)
	public Book getBookById(Long id) {
		return repo.findById(id).orElseThrow(()-> new RuntimeException("Invalid id"));
	}

	@Override
	
	@Transactional(readOnly = true)
	public List<Book> getAllBooks() {
		return repo.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Book> searchByTitle(String title) {
		return repo.findByTitleContainingIgnoreCase(title);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Book> searchByAuthor(String author) {
		return repo.findByAuthorContainingIgnoreCase(author);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Book> searchByCategory(String category) {
		return repo.findByCategoryIgnoreCase(category);
	}

	@Override
	@Transactional(readOnly = true)
	public boolean isIsbnExists(String isbn) {
		return repo.existsByIsbn(isbn);
	}

}

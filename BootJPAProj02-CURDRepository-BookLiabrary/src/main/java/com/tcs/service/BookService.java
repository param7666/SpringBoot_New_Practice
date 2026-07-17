package com.tcs.service;


import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tcs.entity.Book;
import com.tcs.exception.BookNotFoundException;
import com.tcs.repository.BookRepo;

@Service
public class BookService implements IBookService{

	@Autowired
	private BookRepo repo;

	@Override
	public String addBook(Book b) throws Exception {
		if(b==null) throw new IllegalArgumentException("Book Object is null");
		Integer id=repo.save(b).getId();
		return "Book is saved with id"+id;
	}

	@Override
	public String addMultipleBooks(Iterable<Book> books) throws Exception {
	    if (books == null) throw new IllegalArgumentException("Books Values are null");

	    Iterable<Book> savedBooks = repo.saveAll(books);

	    List<Integer> ids = StreamSupport.stream(savedBooks.spliterator(), false)
	            .map(Book::getId)
	            .collect(Collectors.toList());

	    return ids.toString();
	}

	@Override
	public Iterable<Book> findAllBook() throws Exception {
		Iterable<Book> books=repo.findAll();
		return books;
	}

	@Override
	public Book getBookById(Integer id) throws Exception {
		if(id<0) throw new IllegalArgumentException("Invalid id");
		Book b = repo.findById(id)
		        .orElseThrow(() -> new BookNotFoundException("Book not found with id: " + id));
			
		return b;
	}

	@Override
	public Book updateBook(Integer id,String status) throws Exception {
		if(id<=-1 || status==null || (!status.equalsIgnoreCase("available") && !status.equalsIgnoreCase("inavailable"))) {
			throw new IllegalArgumentException("Invalid status or id");
		}
		Book b=repo.findById(id)
				.orElseThrow(()-> new BookNotFoundException("Book not found with this id= "+id));
		
		if(status.equalsIgnoreCase("available")) {
			b.setIsAvailable(true);
		} else {
			b.setIsAvailable(false);
		}
		Book updatedBook=repo.save(b);
		return updatedBook;
	}

	@Override
	public String deleteBookById(Integer id) throws Exception {
		Book b=repo.findById(id).
				orElseThrow(()-> new BookNotFoundException("Book not found with id: " + id));
		repo.deleteById(id);
		return "Book deleted";
	}
	
	
}

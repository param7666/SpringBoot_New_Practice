package com.tcs.service;

import com.tcs.entity.Book;

public interface IBookService {

	public String addBook(Book b) throws Exception;
	
	public String addMultipleBooks(Iterable<Book> books) throws Exception;
	
	public Iterable<Book> findAllBook() throws Exception;
	
	public Book getBookById(Integer id) throws Exception;
	
	public Book updateBook(Integer id,String status) throws Exception;
	
	public String deleteBookById(Integer id) throws Exception;
}

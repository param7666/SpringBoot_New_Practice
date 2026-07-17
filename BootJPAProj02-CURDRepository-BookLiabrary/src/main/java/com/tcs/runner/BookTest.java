package com.tcs.runner;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.tcs.entity.Book;
import com.tcs.service.IBookService;

@Component
public class BookTest implements CommandLineRunner{

	
	@Autowired
	private IBookService service;
	
	@Override
	public void run(String... args) throws Exception {
		
		Book b1 = new Book("The Alchemist", "Paulo Coelho", 399.0, true);
		Book b2 = new Book("Atomic Habits", "James Clear", 599.0, false);
		Book b3 = new Book("1984", "George Orwell", 299.0, true);
		Book b4 = new Book("To Kill a Mockingbird", "Harper Lee", 350.0, true);
		Book b5 = new Book("The Hobbit", "J.R.R. Tolkien", 450.0, false);
		Book b6 = new Book("Sapiens", "Yuval Noah Harari", 699.0, true);
		Book b7 = new Book("The Great Gatsby", "F. Scott Fitzgerald", 250.0, true);
		Book b8 = new Book("Deep Work", "Cal Newport", 499.0, false);
		Book b9 = new Book("Clean Code", "Robert C. Martin", 799.0, true);
		Book b10 = new Book("The Pragmatic Programmer", "David Thomas", 850.0, true);
		try {
			
//			String msg=service.addBook(b2);
//			System.out.println(msg);
			
//			List<Book> books = List.of(b1, b2, b3, b4, b5, b6, b7, b8, b9, b10);
//			
//			String result=service.addMultipleBooks(books);
//			System.out.println(result);
			
			service.findAllBook().forEach(System.out::println);
			
//			System.out.println(service.updateBook(55, "inavailable"));
			System.out.println(service.deleteBookById(55));
			service.findAllBook().forEach(System.out::println);
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
	}

}

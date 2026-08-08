package com.tcs;

import java.util.ArrayList;
import java.util.List;

import org.apache.catalina.core.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.tcs.entity.Book;
import com.tcs.repository.BookRepository;

@SpringBootApplication
public class BootSecurityProject03Application {

	
	public static void main(String[] args) {
		 SpringApplication.run(BootSecurityProject03Application.class, args);
	}

}

package com.tcs.repository;


import org.springframework.data.repository.CrudRepository;

import com.tcs.entity.Book;

public interface BookRepo extends CrudRepository<Book, Integer>{

}

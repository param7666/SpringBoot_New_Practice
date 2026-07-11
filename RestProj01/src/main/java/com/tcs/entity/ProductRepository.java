package com.tcs.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // JpaRepository already gives us save(), findAll(), findById(), deleteById() for free.
    // No need to write SQL — Spring Data JPA generates it under the hood.
}
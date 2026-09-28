package com.param.entity;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Entity 
@Table 
@AllArgsConstructor 
@NoArgsConstructor 
@Data 
@RequiredArgsConstructor 
public class Book {

    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull 
    private String name;

    @NonNull 
    private String author;

    @NonNull 
    private Double price;
}

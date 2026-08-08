package com.tcs.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name="Book101")
@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	
	@Column(nullable = false)
	@Nonnull
	private String title;
	
	
	@Column(nullable = false)
	@Nonnull
	private String author;
	
	
	@Column(nullable = false,unique = true)
	@Nonnull
	private String isbn;
	
	
	@Nonnull
	private String category;
	
	
	@Column(name = "total_copies")
	@Nonnull
	private Integer totalCopies;
	
	@Column(name = "available_copies")
	@Nonnull
	private Integer availableCopies;

	@Version
	private Integer updateCount;
	
	@CreationTimestamp
	@Column(insertable = true)
	private LocalDateTime createdOn;
	
	@UpdateTimestamp
	//@Column(insertable = false, nullable = true)
	private LocalDateTime updatedOn;
}

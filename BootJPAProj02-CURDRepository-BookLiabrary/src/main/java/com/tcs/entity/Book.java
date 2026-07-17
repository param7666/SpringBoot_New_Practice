package com.tcs.entity;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name="Book101")
@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Nonnull
	@Column(name="BNAME", length = 50)
	private String name;
	
	@Nonnull
	@Column(name="BAUTHOR",length = 50)
	private String author;
	
	@Nonnull
	@Column(name="BPRICE")
	private Double price;
	
	@Nonnull
	@Column(name="ISAVAILABLE")
	private Boolean isAvailable;
	
	
}

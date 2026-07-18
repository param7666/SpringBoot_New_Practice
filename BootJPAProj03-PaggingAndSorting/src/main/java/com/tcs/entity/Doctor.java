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
@Table(name = "Doctor")
@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class Doctor {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Nonnull
	@Column(length = 30, name = "DOC_NAME")
	private String name;
	
	@Nonnull
	@Column(length = 30, name = "DOC_QLFY")
	private String qlfy;
	
	@Nonnull
	@Column(name="DOC_INCOME")
	private Long income;
	
	@Nonnull
	@Column(name="DOC_MOBILE")
	private Long mobileNo;
	
	@Nonnull
	@Column(length = 30,name="DOC_SPLZ")
	private String specilization;
	
}


package com.tcs.entity;

import java.time.LocalDate;
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
@Table
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
public class CallerTune {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer tuneId;
	
	@Nonnull
	private String tuneName;;
	
	@Nonnull
	private String movieName;
	
	@Version
	private Integer updateCount;
	
	@CreationTimestamp
	@Column(insertable = true)
	private LocalDateTime createdOn;
	
	@UpdateTimestamp
	//@Column(insertable = false, nullable = true)
	private LocalDateTime updatedOn;
	
	
}

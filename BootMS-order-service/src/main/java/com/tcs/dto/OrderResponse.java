package com.tcs.dto;

import java.time.LocalDateTime;

import com.tcs.enums.OrderStatus;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {

	private Long id;
	private String authUsername;
	private String productName;
	private Integer quantity;
	private Double totalPrice;
	private String shippingAddress;
	@Enumerated(EnumType.STRING)
	private OrderStatus status;
	private LocalDateTime createdAt;
	
}

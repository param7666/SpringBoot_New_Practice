package com.tcs.service;

import java.util.List;

import com.tcs.dto.CreateOrderRequest;
import com.tcs.dto.OrderResponse;

public interface OrderService {

	public OrderResponse placeOrder(String authUsername, CreateOrderRequest request) throws Exception;
	
	public OrderResponse getOrderById(Long id) throws Exception;
	
	public List<OrderResponse> getMyOrders(String authUsername) throws Exception;
	
	public String cancelOrder(Long id, String authUsername) throws Exception;
	
}

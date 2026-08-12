package com.tcs.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.tcs.clientconponants.ProductServiceClient;
import com.tcs.clientconponants.UserServiceClient;
import com.tcs.dto.CreateOrderRequest;
import com.tcs.dto.OrderResponse;
import com.tcs.dto.ProductResponse;
import com.tcs.dto.ProfileResponse;
import com.tcs.entity.Order;
import com.tcs.enums.OrderStatus;
import com.tcs.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{
	
	private final ProductServiceClient pc;
	private final UserServiceClient uc;
	private final OrderRepository repo;

	@Override
	public OrderResponse placeOrder(String authUsername, CreateOrderRequest request) throws Exception {
		// get the product details from client componant
		ProductResponse product=pc.getProductById(request.getProductId());
		//validate the product
		if(product==null) throw new IllegalArgumentException("Invalid product id");
		
		// validate stock
		if(product.getStock()<request.getQuantity()) {
			throw new IllegalArgumentException("Insufficient stock for product: " + product.getName());
		}
		
		ProfileResponse user=uc.getProfile(authUsername);
		
		// validate the user and address
		if(user==null || user.getAddress()==null) {
			throw new IllegalArgumentException("Shipping address not found. Please complete your profile first.");
		}
		
		// reduce the stock
		pc.reduceStock(request.getProductId(), request.getQuantity());
		
		Double totalPrice=product.getPrice()*request.getQuantity();
		
		Order order=new Order();
		order.setAuthUsername(authUsername);
		order.setProductId(product.getId());
		order.setProductName(product.getName());
		order.setQuantity(request.getQuantity());
		order.setTotalPrice(totalPrice);
		order.setStatus(OrderStatus.PLACED);
		
		// save order
		Order saved=repo.save(order);
		
		// return response
		return toResponse(saved);
	}

	@Override
	public OrderResponse getOrderById(Long id) throws Exception {
		Order order=repo.findById(id).orElseThrow(()-> new IllegalArgumentException("Invalid Product id"));
		return toResponse(order);
	}

	@Override
	public List<OrderResponse> getMyOrders(String authUsername) throws Exception {
		List<Order> orders=repo.findByAuthUsername(authUsername);
		
		return orders.stream().map(this::toResponse).toList();
	}

	@Override
	public String cancelOrder(Long id, String authUsername) throws Exception {
		Order order=repo.findById(id).orElseThrow(()-> new IllegalArgumentException("Order not found"));
		
		if(!order.getAuthUsername().equalsIgnoreCase(authUsername)) {
			throw new RuntimeException("You are not authorised to cancel this order");
		}
		
		if(OrderStatus.CANCELLED.equals(order.getStatus())) {
			throw new RuntimeException("Order is allready cancelled..");
		}
		order.setStatus(OrderStatus.CANCELLED);
		repo.save(order);
		return "Your order "+order.getProductName()+" is cancelled";
	}
	
	
	// converts Order to ordersResponse
	private OrderResponse toResponse(Order order) {
		OrderResponse response=new OrderResponse();
		BeanUtils.copyProperties(order, response);
		return response;
	}

}

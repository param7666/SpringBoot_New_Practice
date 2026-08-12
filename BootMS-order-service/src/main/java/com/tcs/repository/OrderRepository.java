package com.tcs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tcs.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{

	public List<Order> findByAuthUsername(String authUsername);
	
	public List<Order> findByStatus(String status);
}

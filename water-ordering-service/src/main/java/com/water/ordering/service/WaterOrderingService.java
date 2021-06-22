package com.water.ordering.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.water.ordering.dto.OrderDto;
import com.water.ordering.entity.Order;

public interface WaterOrderingService {
	
	public ResponseEntity<Map<String,Object>> createOrder(OrderDto orderDto);
	
	public Order cancelOrder(String orderId);
	
	public List<OrderDto> getOrdersDetail(String farmId);
}

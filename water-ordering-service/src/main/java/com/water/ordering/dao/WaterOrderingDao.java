package com.water.ordering.dao;

import java.util.List;

import com.water.ordering.entity.Order;
import com.water.ordering.entity.StatusEnum;

public interface WaterOrderingDao {
	
	public Order createOrder(Order order);
	
	public Order cancelOrder(String orderId);
	
	public List<Order> getOrdersDetail(String farmId);
	
	public List<StatusEnum> getStatusDetails();
}

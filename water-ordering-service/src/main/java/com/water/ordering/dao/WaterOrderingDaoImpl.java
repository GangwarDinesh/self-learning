package com.water.ordering.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

import com.water.ordering.entity.Order;
import com.water.ordering.entity.StatusEnum;
import com.water.ordering.repository.OrderEnumRepository;
import com.water.ordering.repository.WaterOrderingRepository;

@Repository
public class WaterOrderingDaoImpl implements WaterOrderingDao {
	
	Logger logger = LoggerFactory.getLogger(WaterOrderingDaoImpl.class);

	@Autowired
	private WaterOrderingRepository waterOrderingRepository;
	
	@Autowired
	private OrderEnumRepository orderEnumRepository;
	
	@Override
	public Order createOrder(Order order) {
		try {
			return waterOrderingRepository.save(order);
		}catch (DataIntegrityViolationException e) {
			order.setStatus(-1);
		}
		catch (Exception e) {
			logger.error(e.getMessage());
		}
		return order;
	}

	@Override
	public Order cancelOrder(String orderId) {
		Order order = waterOrderingRepository.findByOrderIdAndStatus(orderId);
		if(order != null) {
			order.setStatus(4);
			order = waterOrderingRepository.save(order);
			logger.info("New water order for farm {0} created. {}",order.getFarmId());
		}
		return order;
	}

	@Override
	public List<Order> getOrdersDetail(String farmId) {
		return waterOrderingRepository.findByFarmId(farmId);
	}

	@Override
	public List<StatusEnum> getStatusDetails() {
		return orderEnumRepository.findAll();
	}

}

package com.water.ordering.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.water.ordering.constants.Constants;
import com.water.ordering.dao.WaterOrderingDao;
import com.water.ordering.dto.OrderDto;
import com.water.ordering.entity.Order;
import com.water.ordering.entity.StatusEnum;
import com.water.ordering.repository.OrderEnumRepository;
import com.water.ordering.utils.CommonUtils;

@Service
public class WaterOrderingServiceImpl implements WaterOrderingService {
	
	@Autowired
	private WaterOrderingDao waterOrderingDao;
	
	@Autowired
	private OrderEnumRepository orderEnumRepository;
	
	@Autowired
	private CommonUtils commonUtils;

	@Override
	public ResponseEntity<Map<String,Object>> createOrder(OrderDto orderDto) {
		ResponseEntity<Map<String,Object>> respMap = null;
		Map<String, Object> responseMap = new HashMap<>();
		String orderId = null;
		Order order = new Order();
		order.setFarmId(orderDto.getFarmId());
		order.setDuration(orderDto.getDuration());
		order.setStartDateTime(commonUtils.convertStringToDate(orderDto.getStartDateTime()));
		order.setStatus(1);
		order.setOrderId(commonUtils.generateOrderId(orderDto.getFarmId()));
		order = waterOrderingDao.createOrder(order);
		if(null != order) {
			if(order.getStatus()==-1) {
				responseMap.put(Constants.STATUS, "Error");
				responseMap.put(Constants.MESSAGE, "Duplicate order request for the given time.");
				return new ResponseEntity<>(responseMap, new HttpHeaders(),HttpStatus.EXPECTATION_FAILED);
			}else {
				orderId = order.getOrderId();
			}
		}
		Optional<StatusEnum> statusEnumOpt = orderEnumRepository.findAll().stream().filter(status->status.getStatusId()==1).findFirst();
		StatusEnum statusEnum = new StatusEnum();
		if(statusEnumOpt.isPresent()) {
			statusEnum = statusEnumOpt.get();
		}
		
		if(null != orderId) {
			responseMap.put(Constants.STATUS, statusEnum.getStatusCode());
			responseMap.put(Constants.MESSAGE, statusEnum.getStatusMessage()+" Your Order ID is "+orderId);
			respMap = new ResponseEntity<>(responseMap, new HttpHeaders(),HttpStatus.CREATED);
		}else {
			responseMap.put(Constants.STATUS, "Error");
			responseMap.put(Constants.MESSAGE, "Some technical error occurred! Please try after some time.");
			respMap = new ResponseEntity<>(responseMap, new HttpHeaders(),HttpStatus.EXPECTATION_FAILED);
		}
		return respMap;
	}

	@Override
	public Order cancelOrder(String orderId) {
		return waterOrderingDao.cancelOrder(orderId);
	}

	@Override
	public List<OrderDto> getOrdersDetail(String farmId) {
		List<Order> ordersList = waterOrderingDao.getOrdersDetail(farmId);
		List<StatusEnum> statusEnumDetails = waterOrderingDao.getStatusDetails();
		return ordersList.stream().map(order->{
			OrderDto orderDto = new OrderDto();
			orderDto.setOrderId(order.getOrderId());
			orderDto.setFarmId(order.getFarmId());
			orderDto.setDuration(order.getDuration());
			orderDto.setStartDateTime(commonUtils.convertDateToString(order.getStartDateTime()));
			StatusEnum statusEnumObj = statusEnumDetails.stream().filter(statusEnum->statusEnum.getStatusId()==order.getStatus()).findAny().get();
			orderDto.setStatusCode(statusEnumObj.getStatusCode());
			orderDto.setStatusMessage(statusEnumObj.getStatusMessage());
			return orderDto;
		}).collect(Collectors.toList());
	}

}

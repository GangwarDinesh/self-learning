package com.water.ordering.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.water.ordering.constants.Constants;
import com.water.ordering.dto.OrderDto;
import com.water.ordering.entity.Order;
import com.water.ordering.service.WaterOrderingService;

@RestController
@RequestMapping("/api/orders")
public class WaterOrderingController {
	
	@Autowired
	private WaterOrderingService waterOrderingService;

	@PostMapping("/create")
	public ResponseEntity<Map<String,Object>> createOrder(@RequestBody OrderDto orderDto){
		return waterOrderingService.createOrder(orderDto);
	}
	
	@PutMapping("/cancel/{orderId}")
	public ResponseEntity<Map<String, String>> cancelOrder(@PathVariable("orderId") String orderId){
		Order order = waterOrderingService.cancelOrder(orderId);
		Map<String, String> respMap = new HashMap<>();
		if(null != order) {
			respMap.put(Constants.STATUS, "Success");
			respMap.put(Constants.MESSAGE, "Your order "+orderId+" has been cancelled successfuly.");
			return new ResponseEntity<>(respMap, new HttpHeaders(),HttpStatus.ACCEPTED);
		}else {
			respMap.put(Constants.STATUS, "Failed");
			respMap.put(Constants.MESSAGE, "Your order "+orderId+" is already cancelled or delivered.");
			return new ResponseEntity<>(respMap, new HttpHeaders(),HttpStatus.NOT_ACCEPTABLE);
		}
	}
	
	@GetMapping("/details/{farmId}")
	public ResponseEntity<List<OrderDto>> getOrdersDetail(@PathVariable("farmId") String farmId ){
		return new ResponseEntity<>(waterOrderingService.getOrdersDetail(farmId), new HttpHeaders(),HttpStatus.OK);
	}
}

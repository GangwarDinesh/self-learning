package com.water.ordering.dto;

import java.io.Serializable;
import java.util.Date;

public class OrderDto implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String orderId;
	
	private String farmId;
	
	private int duration;
	
	private String statusCode;
	
	private String statusMessage;
	
	private String startDateTime;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getFarmId() {
		return farmId;
	}

	public void setFarmId(String farmId) {
		this.farmId = farmId;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatusMessage() {
		return statusMessage;
	}

	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}

	public String getStartDateTime() {
		return startDateTime;
	}

	public void setStartDateTime(String startDateTime) {
		this.startDateTime = startDateTime;
	}

	@Override
	public String toString() {
		return "OrderDto [orderId=" + orderId + ", farmId=" + farmId + ", duration=" + duration + ", statusCode="
				+ statusCode + ", statusMessage=" + statusMessage + ", startDateTime=" + startDateTime + "]";
	}

	
}

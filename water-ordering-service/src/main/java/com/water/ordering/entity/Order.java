package com.water.ordering.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "ORDERS", uniqueConstraints = @UniqueConstraint(columnNames = {
		"FARM_ID","START_DATE_TIME","STATUS"
}))
public class Order implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "AUTO_ID")
	private int autoId;
	
	@Column(name = "ORDER_ID", unique = true)
	private String orderId;
	
	@Column(name = "FARM_ID")
	private String farmId;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "START_DATE_TIME")
	private Date startDateTime;
	
	@Column(name = "DURATION")
	private int duration;
	
	@Column(name = "STATUS")
	private int status;

	public int getAutoId() {
		return autoId;
	}

	public void setAutoId(int autoId) {
		this.autoId = autoId;
	}

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

	public Date getStartDateTime() {
		return startDateTime;
	}

	public void setStartDateTime(Date startDateTime) {
		this.startDateTime = startDateTime;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}
	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Order [autoId=" + autoId + ", orderId=" + orderId + ", farmId=" + farmId + ", startDateTime="
				+ startDateTime + ", duration=" + duration + ", status=" + status + "]";
	}

		
}

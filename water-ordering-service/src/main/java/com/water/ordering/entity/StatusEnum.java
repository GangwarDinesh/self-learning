package com.water.ordering.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "STATUS_ENUM")
public class StatusEnum implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "AUTO_ID")
	private int autoId;
	
	@Column(name = "STATUS_ID")
	private int statusId;
	
	@Column(name = "STATUS_CODE")
	private String statusCode;
	
	@Column(name = "STATUS_MESSAGE")
	private String statusMessage;

	public int getAutoId() {
		return autoId;
	}

	public void setAutoId(int autoId) {
		this.autoId = autoId;
	}

	public int getStatusId() {
		return statusId;
	}

	public void setStatusId(int statusId) {
		this.statusId = statusId;
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

	@Override
	public String toString() {
		return "StatusEnum [autoId=" + autoId + ", statusId=" + statusId + ", statusCode=" + statusCode
				+ ", statusMessage=" + statusMessage + "]";
	}

	

}

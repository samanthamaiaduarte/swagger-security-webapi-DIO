package com.smd.webapi.handler;

import java.time.LocalDate;

public class ResponseError {
	private LocalDate timeStamp;
	private String status;
	private Integer statusCode;
	private String error;
	
	public ResponseError() { }
	
	public ResponseError(String error) {
		this.timeStamp = LocalDate.now();
		this.status = "Error";
		this.statusCode = 400;
		this.error = error;
	}

	public LocalDate getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(LocalDate timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

}

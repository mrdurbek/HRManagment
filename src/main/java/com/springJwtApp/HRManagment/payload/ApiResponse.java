package com.springJwtApp.HRManagment.payload;

public class ApiResponse {
	private String message;
	private boolean result;
	
	public ApiResponse(String message, boolean result) {
		super();
		this.message = message;
		this.result = result;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public boolean isResult() {
		return result;
	}
	public void setResult(boolean result) {
		this.result = result;
	}
	
	
}

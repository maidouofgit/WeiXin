package com.maidou.weixin.bean;

import java.util.List;


/*
 * 快递对象
 */
public class Express {

	private String resultcode;
	private String reason;
	private String status;
	private String error_code;
	
	public ExpressSingle getExpressSingle() {
		return expressSingle;
	}

	public void setExpressSingle(ExpressSingle expressSingle) {
		this.expressSingle = expressSingle;
	}

	private ExpressSingle expressSingle;
	
	public String getResultcode() {
		return resultcode;
	}

	public String getReason() {
		return reason;
	}

	public String getStatus() {
		return status;
	}

	public String getError_code() {
		return error_code;
	}



	public void setResultcode(String resultcode) {
		this.resultcode = resultcode;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setError_code(String error_code) {
		this.error_code = error_code;
	}



	
}

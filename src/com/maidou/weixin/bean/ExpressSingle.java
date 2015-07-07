package com.maidou.weixin.bean;

import java.util.List;

/*
 * 快递返回数据组信息
 */
public class ExpressSingle {
	 private String company;
	 private String com;
	 private String no;
	 private String status;
	 private List<ExpressSingleList> expressSingleList;
	 
	 public String getCompany() {
		return company;
	}
	public String getCom() {
		return com;
	}
	public String getNo() {
		return no;
	}
	public String getStatus() {
		return status;
	}
	public List<ExpressSingleList> getExpressSingleList() {
		return expressSingleList;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public void setCom(String com) {
		this.com = com;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public void setExpressSingleList(List<ExpressSingleList> expressSingleList) {
		this.expressSingleList = expressSingleList;
	}
	
	
}

package com.maidou.weixin.util;

import java.util.List;

public class Page<T> {
	private List<T> list;
	private Integer totalCount;
	private Integer pageSize;
	private Integer pageIndex;
	private List<Integer> pageArray;
	
	
	public List<Integer> getPageArray() {
		return pageArray;
	}

	public void setPageArray(List<Integer> pageArray) {
		this.pageArray = pageArray;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}

	public Integer getTotalPages(){
		return (totalCount + pageSize -1)/pageSize;
	}
	
	public Integer getTopPageNo(){
		return 1;
	}
	
	public Integer getPreviousPageNo(){
		if(this.pageIndex <= 2){
			return 1;
		}
		return this.pageIndex - 1;
	}
	
	public Integer getNextPageNo(){
		if(this.pageIndex >= getButtomPageNo()){
			return getButtomPageNo();
		}
		return this.pageIndex + 1;
	}
	
	public Integer getButtomPageNo(){
		return getTotalPages();
	}
	
	public List<Integer> initPageArray(){
		Integer totalPage = this.getTotalPages();
		for(int i = 1 ; i <= totalPage ; i ++){
			this.pageArray.add(i);
		}
		return this.pageArray;
	}
	
	
}

package com.djb.art.model;

public class BookOption {
	
	private String bookNameOrBarcode;
	
	private int borrowTimes;
	
	private int createdTime;
	
	private int bookType;

	private int page;
	
	private int userId;
	
	private int del_flag;

	public String getBookNameOrBarcode() {
		return bookNameOrBarcode;
	}

	public void setBookNameOrBarcode(String bookNameOrBarcode) {
		this.bookNameOrBarcode = bookNameOrBarcode;
	}

	public int getBorrowTimes() {
		return borrowTimes;
	}

	public void setBorrowTimes(int borrowTimes) {
		this.borrowTimes = borrowTimes;
	}

	public int getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(int createdTime) {
		this.createdTime = createdTime;
	}

	public int getBookType() {
		return bookType;
	}

	public void setBookType(int bookType) {
		this.bookType = bookType;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getDel_flag() {
		return del_flag;
	}

	public void setDel_flag(int del_flag) {
		this.del_flag = del_flag;
	}
	
}

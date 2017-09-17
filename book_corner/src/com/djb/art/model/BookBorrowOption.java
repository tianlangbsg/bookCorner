package com.djb.art.model;

public class BookBorrowOption {
	
	// 图书ID
	private Integer bookId;
	
	// 用户ID
	private Integer userId;
	
	// 还书状态（0：全部/1：已还/2：待还）
	private Integer returnStatus;
	
	// 起始时间（8位char）
	private String startDate;
	
	// 结束时间
	private String endDate;

	public Integer getReturnStatus() {
		return returnStatus;
	}

	public void setReturnStatus(Integer returnStatus) {
		this.returnStatus = returnStatus;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public Integer getBookId() {
		return bookId;
	}

	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

}

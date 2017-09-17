package com.djb.art.model;

import java.util.Date;

public class BookBorrow {
	// 借阅ID
	private Integer borrowId;
	// 图书ID
	private Integer bookId;
	// 图书名字
	private String bookName;
	// 用户ID
	private Integer userId;
	// 用户姓名
	private String userName;
	// 借阅时间
	private String borrowDate;
	// 计划归还时间
	private String planReturnDate;
	// 归还时间
	private String returnDate;
	// 创建时间
	private Date createdAt;
	// 更新时间
	private Date updatedAt;

	public Integer getBorrowId() {
		return borrowId;
	}

	public void setBorrowId(Integer borrowId) {
		this.borrowId = borrowId;
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

	public String getBorrowDate() {
		return borrowDate;
	}

	public void setBorrowDate(String borrowDate) {
		this.borrowDate = borrowDate;
	}

	public String getPlanReturnDate() {
		return planReturnDate;
	}

	public void setPlanReturnDate(String planReturnDate) {
		this.planReturnDate = planReturnDate;
	}

	public String getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(String returnDate) {
		this.returnDate = returnDate;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public String toString() {
		return "BookBorrow [borrowId=" + borrowId + ", bookId=" + bookId + ", userId=" + userId + ", borrowDate="
				+ borrowDate + ", planReturnDate=" + planReturnDate + ", returnDate=" + returnDate
				+ ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + "]";
	}


}

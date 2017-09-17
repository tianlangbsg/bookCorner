package com.djb.art.model;

import java.util.Date;

public class BookPraise {
	// 点赞记录ID
	private Integer praiseId;
	// 图书ID
	private Integer bookId;
	// 用户ID
	private Integer userId;
	// 点赞时间
	private Date createdAt;
	// 状态更新时间
	private Date updatedAt;

	public Integer getPraiseId() {
		return praiseId;
	}

	public void setPraiseId(Integer praiseId) {
		this.praiseId = praiseId;
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

}

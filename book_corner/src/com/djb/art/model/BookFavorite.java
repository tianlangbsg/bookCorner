package com.djb.art.model;

import java.util.Date;

public class BookFavorite {
	//收藏ID
	private Integer favoriteId;
	//图书ID
	private Integer bookId;
	//用户ID
	private Integer userId;
	//记录是否被删除的FLAG
	private String delFlag;
	//收藏记录创建时间
	private Date createdAt;
	//收藏记录更新时间
	private Date updatedAt;

	public Integer getFavoriteId() {
		return favoriteId;
	}

	public void setFavoriteId(Integer favoriteId) {
		this.favoriteId = favoriteId;
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

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
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

	@Override
	public String toString() {
		return "BookFavorite [favoriteId=" + favoriteId + ", bookId=" + bookId + ", userId=" + userId
				+ ", delFlag=" + delFlag + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + "]";
	}

}

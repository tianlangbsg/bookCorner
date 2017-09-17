package com.djb.art.model;

import java.util.Date;

public class BookType {
	// 图书类型ID
	private Integer bookTypeId;
	// 图书类型名称
	private String bookTypeName;
	// 图书类型颜色
	private String bookTypeColor;
	// 删除FLAG
	private String delFlag;
	// 记录创建时间
	private Date createdAt;
	// 记录更新时间
	private Date updatedAt;

	public Integer getBookTypeId() {
		return bookTypeId;
	}

	public void setBookTypeId(Integer bookTypeId) {
		this.bookTypeId = bookTypeId;
	}

	public String getBookTypeName() {
		return bookTypeName;
	}

	public void setBookTypeName(String bookTypeName) {
		this.bookTypeName = bookTypeName;
	}

	public String getBookTypeColor() {
		return bookTypeColor;
	}

	public void setBookTypeColor(String bookTypeColor) {
		this.bookTypeColor = bookTypeColor;
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

}

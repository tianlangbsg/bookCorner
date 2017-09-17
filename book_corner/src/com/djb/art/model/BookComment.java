package com.djb.art.model;

import java.util.Date;

public class BookComment {
	// 评论ID
	private Integer commentId;
	// 图书ID
	private Integer bookId;
	// 书名
	private String bookName;
	// 用户ID
	private Integer userId;
	// 用户名
	private String userName;
	// 用户评分
	private String commentGrade;
	// 匿名标签
	private String anonymouFlag;
	// 评论内容
	private String comment;
	// 是否删除标签
	private String delFlag;
	// 记录生成日期
	private Date createdAt;
	// 记录更新日期
	private Date updatedAt;
	public Integer getCommentId() {
		return commentId;
	}
	public void setCommentId(Integer commentId) {
		this.commentId = commentId;
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
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getCommentGrade() {
		return commentGrade;
	}
	public void setCommentGrade(String commentGrade) {
		this.commentGrade = commentGrade;
	}
	public String getAnonymouFlag() {
		return anonymouFlag;
	}
	public void setAnonymouFlag(String anonymouFlag) {
		this.anonymouFlag = anonymouFlag;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
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
	public String getBookName() {
		return bookName;
	}
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

}

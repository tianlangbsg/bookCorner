package com.djb.art.model;

import java.util.Date;

public class UserReview {
	// 申请审核记录ID
	private Integer reviewId;
	// 微信OPENID
	private String openId;
	// 员工号
	private String userCode;
	// 用户名
	private String userName;
	// 申请日期
	private String applyAt;
	// 审核操作日期
	private String reviewAt;
	// 审核状态
	private String reviewFlag;
	// 审核操作者ID
	private Integer reviewUserId;
	// 登录时间
	private Date createdAt;
	// 更新时间
	private Date updatedAt;

	public Integer getReviewId() {
		return reviewId;
	}

	public void setReviewId(Integer reviewId) {
		this.reviewId = reviewId;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getApplyAt() {
		return applyAt;
	}

	public void setApplyAt(String applyAt) {
		this.applyAt = applyAt;
	}

	public String getReviewAt() {
		return reviewAt;
	}

	public void setReviewAt(String reviewAt) {
		this.reviewAt = reviewAt;
	}

	public String getReviewFlag() {
		return reviewFlag;
	}

	public void setReviewFlag(String reviewFlag) {
		this.reviewFlag = reviewFlag;
	}

	public Integer getReviewUserId() {
		return reviewUserId;
	}

	public void setReviewUserId(Integer reviewUserId) {
		this.reviewUserId = reviewUserId;
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

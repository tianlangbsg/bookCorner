package com.djb.art.model;

import java.util.Date;

public class User extends SystemManager{
	// 用户ID
	private Integer userId;
	// 员工号
	private String userCode;
	// 用户名
	private String userFullname;
	// 申请日期
	private String applyAt;
	// 加入日期
	private String joinAt;
	// 用户角色权限
	private String userAuthFlag;
	// 用户状态
	private String validFlag;
	// 微信OPENID
	private String openId;
	// 登录者ID
	private Integer createdUserId;
	// 登录时间
	private Date createdAt;
	// 更新者ID
	private Integer updatedUserId;
	// 更新时间
	private Date updatedAt;
	// 借阅历史总数
	private Integer borrowCount;
	// 未还总数
	private Integer staystillCount;
	// 收藏总数
	private Integer favouriteCount;
	// 点赞总数
	private Integer praiseCount;
	// 评论总数
	private Integer commentCount;
	//最新借阅时间
	private String lastBorrowDate;
	// 点对点消息总数
	private Integer p2pMsgCount;
	//强转为管理员类时的名字
	private String managerName;
	//强转为管理员类时的ID
	private Integer managerId;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getUserFullname() {
		return userFullname;
	}

	public void setUserFullname(String userFullname) {
		this.userFullname = userFullname;
	}

	public String getApplyAt() {
		return applyAt;
	}

	public void setApplyAt(String applyAt) {
		this.applyAt = applyAt;
	}

	public String getJoinAt() {
		return joinAt;
	}

	public void setJoinAt(String joinAt) {
		this.joinAt = joinAt;
	}

	public String getUserAuthFlag() {
		return userAuthFlag;
	}

	public void setUserAuthFlag(String userAuthFlag) {
		this.userAuthFlag = userAuthFlag;
	}

	public String getValidFlag() {
		return validFlag;
	}

	public void setValidFlag(String validFlag) {
		this.validFlag = validFlag;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public Integer getCreatedUserId() {
		return createdUserId;
	}

	public void setCreatedUserId(Integer createdUserId) {
		this.createdUserId = createdUserId;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Integer getUpdatedUserId() {
		return updatedUserId;
	}

	public void setUpdatedUserId(Integer updatedUserId) {
		this.updatedUserId = updatedUserId;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Integer getBorrowCount() {
		return borrowCount;
	}

	public void setBorrowCount(Integer borrowCount) {
		this.borrowCount = borrowCount;
	}

	public Integer getStaystillCount() {
		return staystillCount;
	}

	public void setStaystillCount(Integer staystillCount) {
		this.staystillCount = staystillCount;
	}

	public Integer getFavouriteCount() {
		return favouriteCount;
	}

	public void setFavouriteCount(Integer favouriteCount) {
		this.favouriteCount = favouriteCount;
	}

	public Integer getPraiseCount() {
		return praiseCount;
	}

	public void setPraiseCount(Integer praiseCount) {
		this.praiseCount = praiseCount;
	}

	public Integer getCommentCount() {
		return commentCount;
	}

	public void setP2pMsgCount(Integer p2pMsgCount) {
		this.p2pMsgCount = p2pMsgCount;
	}
	
	public Integer getP2pMsgCount() {
		return p2pMsgCount;
	}
	
	public void setCommentCount(Integer commentCount) {
		this.commentCount = commentCount;
	}

	public String getLastBorrowDate() {
		return lastBorrowDate;
	}

	public void setLastBorrowDate(String lastBorrowDate) {
		this.lastBorrowDate = lastBorrowDate;
	}

	public String getManagerName() {
		return userFullname;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public Integer getManagerId() {
		return userId;
	}

	public void setManagerId(Integer managerId) {
		this.managerId = managerId;
	}


	
}

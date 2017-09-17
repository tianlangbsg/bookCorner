package com.djb.art.model;

import java.math.BigDecimal;
import java.util.Date;

public class Book {
	// 图书ID
	private Integer bookId;
	// 图书条形码
	private String bookBarcode;
	// 图书名
	private String bookName;
	// 图书简介
	private String bookIntro;
	// 图书类型ID
	private Integer bookTypeId;
	// 图书类型
	private String bookType;
	// 图书类型颜色
	private String bookTypeColor;
	// 图书来源ID
	private Integer sourceId;
	// 图书来源名称
	private String sourceName;
	// 捐赠人ID
	private Integer contributorId;
	// 所属部门名称
	private String dept;
	// 图书标价
	private BigDecimal price;
	// 图书购入价
	private BigDecimal cost;
	// 图书路径
	private String picPath;
	// 描述图片1
	private String picture1;
	// 描述图片2
	private String picture2;
	// 描述图片3
	private String picture3;
	// 描述图片4
	private String picture4;
	// 描述图片5
	private String picture5;
	// 描述图片6
	private String picture6;
	// 描述图片7
	private String picture7;
	// 描述图片8
	private String picture8;
	// 描述图片9
	private String picture9;
	// 要删除的图片ID
	private Integer picId;
	// 是否可借
	private String borrowFlag;
	// 图书状态
	private String delFlag;
	// 登录者ID
	private Integer createdUser;
	// 登录时间
	private Date createdAt;
	// 更新者ID
	private Integer updatedUser;
	// 更新时间
	private Date updatedAt;
	// 借阅历史
	private Integer borrowCount;
	// 收藏数目
	private Integer favouriteCount;
	// 点赞数目
	private Integer praiseCount;
	// 评论数目
	private Integer commentCount;
	//当前的用户是否已借阅FLAG
	private Integer currentUserBorrow;
	
	public String getBookType() {
		return bookType;
	}

	public void setBookType(String bookType) {
		this.bookType = bookType;
	}
	
	public Integer getBookId() {
		return bookId;
	}

	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}

	public String getBookBarcode() {
		return bookBarcode;
	}

	public void setBookBarcode(String bookBarcode) {
		this.bookBarcode = bookBarcode;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getBookIntro() {
		return bookIntro;
	}

	public void setBookIntro(String bookIntro) {
		this.bookIntro = bookIntro;
	}

	public Integer getBookTypeId() {
		return bookTypeId;
	}

	public void setBookTypeId(Integer bookTypeId) {
		this.bookTypeId = bookTypeId;
	}

	public Integer getSourceId() {
		return sourceId;
	}

	public void setSourceId(Integer sourceId) {
		this.sourceId = sourceId;
	}

	public Integer getContributorId() {
		return contributorId;
	}

	public void setContributorId(Integer contributorId) {
		this.contributorId = contributorId;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getCost() {
		return cost;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

	public String getPicPath() {
		return picPath;
	}

	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}

	public String getPicture1() {
		return picture1;
	}

	public void setPicture1(String picture1) {
		this.picture1 = picture1;
	}

	public String getPicture2() {
		return picture2;
	}

	public void setPicture2(String picture2) {
		this.picture2 = picture2;
	}

	public String getPicture3() {
		return picture3;
	}

	public void setPicture3(String picture3) {
		this.picture3 = picture3;
	}

	public String getPicture4() {
		return picture4;
	}

	public void setPicture4(String picture4) {
		this.picture4 = picture4;
	}

	public String getPicture5() {
		return picture5;
	}

	public void setPicture5(String picture5) {
		this.picture5 = picture5;
	}

	public String getPicture6() {
		return picture6;
	}

	public void setPicture6(String picture6) {
		this.picture6 = picture6;
	}

	public String getPicture7() {
		return picture7;
	}

	public void setPicture7(String picture7) {
		this.picture7 = picture7;
	}

	public String getPicture8() {
		return picture8;
	}

	public void setPicture8(String picture8) {
		this.picture8 = picture8;
	}

	public String getPicture9() {
		return picture9;
	}

	public void setPicture9(String picture9) {
		this.picture9 = picture9;
	}

	public String getBorrowFlag() {
		return borrowFlag;
	}

	public void setBorrowFlag(String borrowFlag) {
		this.borrowFlag = borrowFlag;
	}

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	public Integer getCreatedUser() {
		return createdUser;
	}

	public void setCreatedUser(Integer createdUser) {
		this.createdUser = createdUser;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Integer getUpdatedUser() {
		return updatedUser;
	}

	public void setUpdatedUser(Integer updatedUser) {
		this.updatedUser = updatedUser;
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

	public Integer getFavouriteCount() {
		return favouriteCount;
	}

	public void setFavouriteCount(Integer favouriteCount) {
		this.favouriteCount = favouriteCount;
	}

	public Integer getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(Integer commentCount) {
		this.commentCount = commentCount;
	}

	public Integer getPraiseCount() {
		return praiseCount;
	}

	public void setPraiseCount(Integer praiseCount) {
		this.praiseCount = praiseCount;
	}

	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	public String getBookTypeColor() {
		return bookTypeColor;
	}

	public void setBookTypeColor(String bookTypeColor) {
		this.bookTypeColor = bookTypeColor;
	}

	public Integer getCurrentUserBorrow() {
		return currentUserBorrow;
	}

	public void setCurrentUserBorrow(Integer currentUserBorrow) {
		this.currentUserBorrow = currentUserBorrow;
	}

	public Integer getPicId() {
		return picId;
	}

	public void setPicId(Integer picId) {
		this.picId = picId;
	}

}

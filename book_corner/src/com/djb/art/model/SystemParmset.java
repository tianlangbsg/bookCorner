package com.djb.art.model;

import java.util.Date;

public class SystemParmset {
	// 设置项ID
	private Integer parmId;
	// 设置项标题
	private String parmTitle;
	// 设置项英文名称
	private String parmNameEn;
	// 设置项内容
	private String setValues;
	// 单位名称
	private String unitName;
	// 登录者ID
	private Integer createdUserId;
	// 登录时间
	private Date createdAt;
	// 更新者ID
	private Integer updatedUserId;
	// 更新时间
	private Date updatedAt;

	public Integer getParmId() {
		return parmId;
	}

	public void setParmId(Integer parmId) {
		this.parmId = parmId;
	}

	public String getParmTitle() {
		return parmTitle;
	}

	public void setParmTitle(String parmTitle) {
		this.parmTitle = parmTitle;
	}

	public String getParmNameEn() {
		return parmNameEn;
	}

	public void setParmNameEn(String parmNameEn) {
		this.parmNameEn = parmNameEn;
	}

	public String getSetValues() {
		return setValues;
	}

	public void setSetValues(String setValues) {
		this.setValues = setValues;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
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

}

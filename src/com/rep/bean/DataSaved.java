package com.rep.bean;

import com.alibaba.fastjson.JSON;
import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Table;

@Table(name = "dataSaved")
public class DataSaved extends EntityBase {
	@Column(column = "inDate")
	private String inDate;
	@Column(column = "userId")
	private String userId;
	@Column(column = "phone")
	private String phone;
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(column = "timeSpan")
	private String timeSpan;

	public String getInDate() {
		return inDate;
	}

	public void setInDate(String inDate) {
		this.inDate = inDate;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getTimeSpan() {
		return timeSpan;
	}

	public void setTimeSpan(String timeSpan) {
		this.timeSpan = timeSpan;
	}

	public String toString() {
		return JSON.toJSONString(this);
	}
}

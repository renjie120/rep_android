package com.rep.util;

import com.alibaba.fastjson.JSON;
import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Table;

@Table(name = "reminder")
public class Reminder extends EntityBase {
	// 有效
	public static final String VALID = "1";
	// 无效
	public static final String INVALID = "2";
	// 提醒过
	public static final String REMINDED = "3";
	@Column(column = "user")
	private String user;//用户
	@Column(column = "weekDate")
	private int weekDate;//周几
	@Column(column = "hour")
	private int hour;//提醒小时
	@Column(column = "minute")
	private int minute;//提醒分钟
	@Column(column = "flg")
	private String flg;//提醒状态
	@Column(column = "lastRemindedTime")
	private String lastRemindedTime;//上次提醒时间

	public String getLastRemindedTime() {
		return lastRemindedTime;
	}

	public void setLastRemindedTime(String lastRemindedTime) {
		this.lastRemindedTime = lastRemindedTime;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public int getWeekDate() {
		return weekDate;
	}

	public void setWeekDate(int weekDate) {
		this.weekDate = weekDate;
	}

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public int getMinute() {
		return minute;
	}

	public void setMinute(int minute) {
		this.minute = minute;
	}

	public String getFlg() {
		return flg;
	}

	public void setFlg(String flg) {
		this.flg = flg;
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
}

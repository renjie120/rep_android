package com.rep.bean;

import com.alibaba.fastjson.JSON;
import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Table;

@Table(name = "zhishi")
public class ZhishiBean extends EntityBase {
	@Column(column = "time")
	private String time;
	@Column(column = "content")
	private String content;

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String toString() {
		return JSON.toJSONString(this);
	}
}

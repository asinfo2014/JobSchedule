package com.bean;

import java.util.List;

public class FlowMenuBean {
	private long id;
	private String text;
	private String state;
	private List<FlowMenuBean> children;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public List<FlowMenuBean> getChildren() {
		return children;
	}

	public void setChildren(List<FlowMenuBean> children) {
		this.children = children;
	}
}

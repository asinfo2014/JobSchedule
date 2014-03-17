package com.bean;

import java.sql.Timestamp;
import java.util.List;

public class InstanceFlowBean {
	private long flowid;
	private String flowname;
	private Timestamp execyctime;
	private Timestamp begintime;
	private Timestamp endtime;
	private int status;
	private String description;
	
	private List<InstanceTaskBean> tasks;

	public long getFlowid() {
		return flowid;
	}

	public void setFlowid(long flowid) {
		this.flowid = flowid;
	}

	
	public String getFlowname() {
		return flowname;
	}

	public void setFlowname(String flowname) {
		this.flowname = flowname;
	}

	public Timestamp getExecyctime() {
		return execyctime;
	}

	public void setExecyctime(Timestamp execyctime) {
		this.execyctime = execyctime;
	}

	public Timestamp getBegintime() {
		return begintime;
	}

	public void setBegintime(Timestamp begintime) {
		this.begintime = begintime;
	}

	public Timestamp getEndtime() {
		return endtime;
	}

	public void setEndtime(Timestamp endtime) {
		this.endtime = endtime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<InstanceTaskBean> getTasks() {
		return tasks;
	}

	public void setTasks(List<InstanceTaskBean> tasks) {
		this.tasks = tasks;
	}

	


}
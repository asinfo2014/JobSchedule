package com.bean;

import java.sql.Timestamp;

public class InstanceTaskBean {
	private long taskid;
	private long flowid;
	private Timestamp execyctime;
	private Timestamp begintime;
	private Timestamp endtime;
	private int status;
	private String log;
	private int execcnt;
	private String taskname;
	private String flowname;
	
	
	
	public InstanceTaskBean() {
		this.flowid = 0;
		this.taskid = 0;
	}

	public InstanceTaskBean(long taskid, long flowid) {
		this.flowid = flowid;
		this.taskid = taskid;
	}
	public long getTaskid() {
		return taskid;
	}
	public void setTaskid(long taskid) {
		this.taskid = taskid;
	}
	public long getFlowid() {
		return flowid;
	}
	public void setFlowid(long flowid) {
		this.flowid = flowid;
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
	public String getLog() {
		return log;
	}
	public void setLog(String log) {
		this.log = log;
	}
	public int getExeccnt() {
		return execcnt;
	}
	public void setExeccnt(int execcnt) {
		this.execcnt = execcnt;
	}
	public String getTaskname() {
		return taskname;
	}
	public void setTaskname(String taskname) {
		this.taskname = taskname;
	}
	public String getFlowname() {
		return flowname;
	}
	public void setFlowname(String flowname) {
		this.flowname = flowname;
	}

	

}
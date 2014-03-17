package com.instance.manager;

import java.sql.Timestamp;

public class FlowDepTask{
	private long flowid;
	private long taskid;
	private Timestamp execyctime;
	private long tarmacid;
	private String execmd;
	private int failedRetryCnt;
	private Long deptaskid;
	
	
	public FlowDepTask() {
		super();
		this.flowid = 0;
		this.taskid = 0;
		this.execyctime = null;
		this.tarmacid = 0;
		this.execmd = "";
		this.failedRetryCnt = 1;
		this.deptaskid = null;
	}
	public long getFlowid() {
		return flowid;
	}
	public void setFlowid(long flowid) {
		this.flowid = flowid;
	}
	public long getTaskid() {
		return taskid;
	}
	public void setTaskid(long taskid) {
		this.taskid = taskid;
	}
	public Timestamp getExecyctime() {
		return execyctime;
	}
	public void setExecyctime(Timestamp execyctime) {
		this.execyctime = execyctime;
	}
	public long getTarmacid() {
		return tarmacid;
	}
	public void setTarmacid(long tarmacid) {
		this.tarmacid = tarmacid;
	}
	public String getExecmd() {
		return execmd;
	}
	public void setExecmd(String execmd) {
		this.execmd = execmd;
	}
	public int getFailedRetryCnt() {
		return failedRetryCnt;
	}
	public void setFailedRetryCnt(int failedRetryCnt) {
		this.failedRetryCnt = failedRetryCnt;
	}
	public Long getDeptaskid() {
		return deptaskid;
	}
	public void setDeptaskid(Long deptaskid) {
		this.deptaskid = deptaskid;
	}
	
}
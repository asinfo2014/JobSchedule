package com.instance.manager;

import java.sql.Timestamp;

public class TaskInfo{
	
	private long flowid;
	private long taskid;
	private int tstatus;
	private Timestamp execyctime;
	private long tarmacid;
	private String execmd;
	private int failedRetryCnt;
	public TaskInfo()
	{
		
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
	public int getTstatus() {
		return tstatus;
	}
	public void setTstatus(int tstatus) {
		this.tstatus = tstatus;
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
}
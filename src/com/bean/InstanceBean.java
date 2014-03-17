package com.bean;

import java.sql.Timestamp;

public class InstanceBean {

	private long taskid;
	private long flowid;
	private Timestamp execyctime;
	private long tarmacid;
	private String execmd;
	private int failedRetryCnt;
	private String paras;
	
	
	

	public String getParas() {
		return paras;
	}
	public void setParas(String paras) {
		this.paras = paras;
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

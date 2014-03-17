package com.bean;

import java.sql.Timestamp;

public class InstanceDepTaskBean {

	private long taskid;
	private long flowid;
	private long deptaskid;
	private long depflowid;
	private Timestamp depexecyctime;
	private int depkind;
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
	public long getDeptaskid() {
		return deptaskid;
	}
	public void setDeptaskid(long deptaskid) {
		this.deptaskid = deptaskid;
	}
	public long getDepflowid() {
		return depflowid;
	}
	public void setDepflowid(long depflowid) {
		this.depflowid = depflowid;
	}
	public Timestamp getDepexecyctime() {
		return depexecyctime;
	}
	public void setDepexecyctime(Timestamp depexecyctime) {
		this.depexecyctime = depexecyctime;
	}
	public int getDepkind() {
		return depkind;
	}
	public void setDepkind(int depkind) {
		this.depkind = depkind;
	}
	
	

}
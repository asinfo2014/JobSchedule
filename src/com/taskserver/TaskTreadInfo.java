package com.taskserver;

import com.bean.InstanceTaskBean;

public class TaskTreadInfo {

	private InstanceTaskBean taskinfo;
	
	private Long threadid;
	
	public TaskTreadInfo(InstanceTaskBean taskinfo, Long threadid) {
		super();
		this.taskinfo = taskinfo;
		this.threadid = threadid;
	}
	
	public InstanceTaskBean getTaskinfo() {
		return taskinfo;
	}
	public void setTaskinfo(InstanceTaskBean taskinfo) {
		this.taskinfo = taskinfo;
	}
	public Long getThreadid() {
		return threadid;
	}
	public void setThreadid(Long threadid) {
		this.threadid = threadid;
	}
}

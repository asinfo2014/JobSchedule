package com.taskserver;

import com.bean.InstanceTaskBean;


public class TaskSSHInfo {
	private InstanceTaskBean taskinfo;
	private String totalcmd;
	private String hostname;
	private String username;
	private String passwd;


	public InstanceTaskBean getTaskinfo() {
		return taskinfo;
	}

	public void setTaskinfo(InstanceTaskBean taskinfo) {
		this.taskinfo = taskinfo;
	}

	public TaskSSHInfo(String totalcmd, String hostname, String username, String passwd) {
		this.taskinfo = new InstanceTaskBean();
		this.totalcmd = totalcmd;
		this.hostname = hostname;
		this.username = username;
		this.passwd = passwd;
	}

	public String getTotalcmd() {
		return totalcmd;
	}

	public void setTotalcmd(String totalcmd) {
		this.totalcmd = totalcmd;
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
}

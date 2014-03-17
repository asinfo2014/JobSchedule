package com.taskserver;

public class HostInfo {
	private String hostname;
	private String username;
	private String passwd;
	public String getHostname() {
		return hostname;
	}
	
	
	public HostInfo(String hostname, String username, String passwd) {
		super();
		this.hostname = hostname;
		this.username = username;
		this.passwd = passwd;
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

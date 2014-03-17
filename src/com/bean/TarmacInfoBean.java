package com.bean;

public class TarmacInfoBean {

	private long tarmacid;
	private String nodeip;
	private int nodeport;
	private String username;
	private String passwd;
	private String sysEnv;
	
	public String getSysEnv() {
		return sysEnv;
	}
	public void setSysEnv(String sysEnv) {
		this.sysEnv = sysEnv;
	}
	public long getTarmacid() {
		return tarmacid;
	}
	public void setTarmacid(long tarmacid) {
		this.tarmacid = tarmacid;
	}
	public String getNodeip() {
		return nodeip;
	}
	public void setNodeip(String nodeip) {
		this.nodeip = nodeip;
	}
	public int getNodeport() {
		return nodeport;
	}
	public void setNodeport(int nodeport) {
		this.nodeport = nodeport;
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
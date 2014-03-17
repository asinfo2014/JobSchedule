package com.bean;

import java.sql.Timestamp;

public class DepFlowDescBean {
	private long flowid;
	private long depflowid;
	private int  kind ;
	private Timestamp depbegintime;
	private int deptimekind; // time dimension：0，1,2,3,4,5：一次任务，分钟，小时，天，月，年
	private int deptimeintv; // interval:timekind为>0时有效，表示执行的间隔,分别对应n分钟、n小时、n天、n月、n年
	private Timestamp deptime;

	public long getFlowid() {
		return flowid;
	}
	public void setFlowid(long flowid) {
		this.flowid = flowid;
	}
	public long getDepflowid() {
		return depflowid;
	}
	public void setDepflowid(long depflowid) {
		this.depflowid = depflowid;
	}
 
	public int getDeptimekind() {
		return deptimekind;
	}
	public void setDeptimekind(int deptimekind) {
		this.deptimekind = deptimekind;
	}
	public int getDeptimeintv() {
		return deptimeintv;
	}
	public void setDeptimeintv(int deptimeintv) {
		this.deptimeintv = deptimeintv;
	}
	public Timestamp getDeptime() {
		return deptime;
	}
	public void setDeptime(Timestamp deptime) {
		this.deptime = deptime;
	}

	public int getKind() {
		return kind;
	}
	public void setKind(int kind) {
		this.kind = kind;
	}
	public Timestamp getDepbegintime() {
		return depbegintime;
	}
	public void setDepbegintime(Timestamp depbegintime) {
		this.depbegintime = depbegintime;
	}
 
	
}

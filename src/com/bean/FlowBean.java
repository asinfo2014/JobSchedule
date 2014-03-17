package com.bean;

import java.sql.Timestamp;
import java.util.List;

/**
 * 流程1
 * 
 * @author wankun
 * 
 */
public class FlowBean {
	private long flowid;
	private Timestamp begintime;
	private Timestamp endtime;
	private int timekind; // time dimension：0，1,2,3,4,5：一次任务，分钟，小时，天，月，年
	private int timeintv; // interval:timekind为>0时有效，表示执行的间隔,分别对应n分钟、n小时、n天、n月、n年
	private String flowname;
	private String flowdesc; // 记录流程节点的拓扑图
	private int flowtype; // 默认为1，执行失败后后续流程不在执行
	private int flowstatus;// 1 可用(不可编辑) 2 不可用(可编辑)
	private int failedRetryCnt;
	private long dirid;
	private String dirname;
	private Timestamp execyctime;// 不提供修改，如果修改了begintime，则execyctime设置为begintime

	private List<FlowTaskBean> flowTaskList; // 流程下所有Task

	public List<FlowTaskBean> getFlowTaskList() {
		return flowTaskList;
	}

	public void setFlowTaskList(List<FlowTaskBean> flowTaskList) {
		this.flowTaskList = flowTaskList;
	}

	public long getFlowid() {
		return flowid;
	}

	public void setFlowid(long flowid) {
		this.flowid = flowid;
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

	public int getTimekind() {
		return timekind;
	}

	public void setTimekind(int timekind) {
		this.timekind = timekind;
	}

	public int getTimeintv() {
		return timeintv;
	}

	public void setTimeintv(int timeintv) {
		this.timeintv = timeintv;
	}

	public String getFlowname() {
		return flowname;
	}

	public void setFlowname(String flowname) {
		this.flowname = flowname;
	}

	public String getFlowdesc() {
		return flowdesc;
	}

	public void setFlowdesc(String flowdesc) {
		this.flowdesc = flowdesc;
	}

	public int getFlowtype() {
		return flowtype;
	}

	public void setFlowtype(int flowtype) {
		this.flowtype = flowtype;
	}

	public int getFlowstatus() {
		return flowstatus;
	}

	public void setFlowstatus(int flowstatus) {
		this.flowstatus = flowstatus;
	}

	public int getFailedRetryCnt() {
		return failedRetryCnt;
	}

	public void setFailedRetryCnt(int failedRetryCnt) {
		this.failedRetryCnt = failedRetryCnt;
	}

	public long getDirid() {
		return dirid;
	}

	public void setDirid(long dirid) {
		this.dirid = dirid;
	}

	public String getDirname() {
		return dirname;
	}

	public void setDirname(String dirname) {
		this.dirname = dirname;
	}

	public Timestamp getExecyctime() {
		return execyctime;
	}

	public void setExecyctime(Timestamp execyctime) {
		this.execyctime = execyctime;
	}

}

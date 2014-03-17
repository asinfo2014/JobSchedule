package com.bean;

import java.sql.Timestamp;
import org.apache.log4j.Logger;

public class ManageFlowBean {
	public static Logger logger = Logger.getLogger(ManageFlowBean.class);
	private long fflowid; // 防止数据库对应字段出现null
	private Timestamp begintime;
	private Timestamp endtime;
	private Integer timekind; 
	private Integer timeintv; 
	private Integer failedRetryCnt;
	private Timestamp execyctime;
	private long iflowid;




	public Long getFflowid() {
		return fflowid;
	}



	public void setFflowid(Long fflowid) {
		this.fflowid = fflowid;
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



	public Integer getTimekind() {
		return timekind;
	}



	public void setTimekind(Integer timekind) {
		this.timekind = timekind;
	}



	public Integer getTimeintv() {
		return timeintv;
	}



	public void setTimeintv(Integer timeintv) {
		this.timeintv = timeintv;
	}



	public Integer getFailedRetryCnt() {
		return failedRetryCnt;
	}



	public void setFailedRetryCnt(Integer failedRetryCnt) {
		this.failedRetryCnt = failedRetryCnt;
	}



	public Timestamp getExecyctime() {
		return execyctime;
	}



	public void setExecyctime(Timestamp execyctime) {
		this.execyctime = execyctime;
	}



	public long getIflowid() {
		return iflowid;
	}



	public void setIflowid(long iflowid) {
		this.iflowid = iflowid;
	}
	


	public void printString(){		
		logger.info(" fflowid:"+fflowid+",begtime:"+begintime+",endtime:"+endtime+",execyctime:"+execyctime+
				",timekind:"+timekind+",timeintv:"+timeintv+",failedRetryCnt:"+failedRetryCnt+",tflowid:"+iflowid);
		
	}


}

package com.instance.status;

import java.sql.Connection;
import java.sql.Timestamp;

import org.apache.log4j.Logger;

import com.common.dbmanage.SysDAOTool;

public class Status {
	static Logger logger = Logger.getLogger(Status.class);
	public static final int SUCCESS = 0;
	public static final int INIT = 1;
	public static final int EXECUTING = 2;
	public static final int FAILER = 3;
	public static final int DEPFAILER = 4; 
	public static final int WAITING = 5;
	public static final int HUNG = 6;
	public int status ;
	public Timestamp execyctime;
	
	public Status() {
		super();
		this.status = -1;
	}

	protected boolean updateStatus(String idname, long idvalue, String tablename, Connection conn, SysDAOTool sysdao)
	{
		if(-1 == status)
		{
			logger.error("id: " + idname + ": "+ idvalue + " 's status: " + status );
		}
		String sql = "update " + tablename + " set STATUS = " + status + " where " + idname + " = " + idvalue +" and EXECYCTIME = '" + execyctime + "'";
		logger.info(sql);
		try {
			if(true == sysdao.updateSQL(conn, sql))
			{
				return true;
			}
		} catch (Exception e) {
			logger.error("execute sql: " + sql + " failed");
		}
		return false;
	}
	
	protected boolean updateStatus(int flag, String idname, long idvalue, String tablename, Connection conn, SysDAOTool sysdao)
	{
		String timename;
		Timestamp time = new Timestamp(System.currentTimeMillis());
		if(0 == flag)
		{
			timename = "BEGINTIME";
		}
		else
		{
			timename = "ENDTIME";
		}
		if(-1 == status)
		{
			logger.error("id: " + idname + ": "+ idvalue + " 's status: " + status );
		}
		String sql = "update " + tablename + " set STATUS = " + status + "," + timename + " = '" + time + "' where " + idname + " = " + idvalue +" and EXECYCTIME = '" + execyctime + "'";
		logger.info(sql);
		try {
			if(true == sysdao.updateSQL(conn, sql))
			{
				return true;
			}
		} catch (Exception e) {
			logger.error("execute sql: " + sql + " failed");
		}
		return false;
	}
}

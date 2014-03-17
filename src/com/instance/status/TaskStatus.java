package com.instance.status;

import java.sql.Connection;

import com.common.dbmanage.SysDAOTool;

public class TaskStatus extends Status{
	public long taskid;
	public final String TABLENAME = "T_INSTANCE_TASK";
	public final String ID = "TASKID";
	
	public TaskStatus() {
		super();
		this.taskid = 0;
	}
	
	public boolean updateTaskStatus(Connection conn, SysDAOTool sysdao)
	{
		if(0 == taskid)
		{
			logger.error("taskid is empty");
			return false;
		}
		if(true == updateStatus(ID, taskid, TABLENAME, conn, sysdao))
		{
			return true;
		}
		logger.error("update taskid: " + taskid + " status failed");
		return false;
	}
	
	public boolean updateTaskStatus(Connection conn, SysDAOTool sysdao, int flag)
	{
		if(taskid == 0)
		{
			logger.error("flowid is empty");
			return false;
		}
		if(true == updateStatus(flag, ID, taskid, TABLENAME, conn, sysdao))
		{
			return true;
		}
		logger.error("update flowid: " + taskid + " status failed");
		return false;
	}


}

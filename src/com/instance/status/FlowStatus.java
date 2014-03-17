package com.instance.status;

import java.sql.Connection;

import com.common.dbmanage.SysDAOTool;

public class FlowStatus extends Status{
	public long flowid ;
	public final String TABLENAME = "T_INSTANCE_FLOW";
	public final String ID = "FLOWID";
	
	public FlowStatus() {
		super();
		this.flowid = 0;
	}
	
	public boolean updateFlowStatus(Connection conn, SysDAOTool sysdao)
	{
		if(flowid == 0)
		{
			logger.error("flowid is empty");
			return false;
		}
		if(true == updateStatus(ID, flowid, TABLENAME, conn, sysdao))
		{
			return true;
		}
		logger.error("update flowid: " + flowid + " status failed");
		return false;
	}
	
	public boolean updateFlowStatus(Connection conn, SysDAOTool sysdao, int flag)
	{
		if(flowid == 0)
		{
			logger.error("flowid is empty");
			return false;
		}
		if(true == updateStatus(flag, ID, flowid, TABLENAME, conn, sysdao))
		{
			return true;
		}
		logger.error("update flowid: " + flowid + " status failed");
		return false;
	}



}

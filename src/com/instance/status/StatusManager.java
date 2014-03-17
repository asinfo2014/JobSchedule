package com.instance.status;

import java.sql.Connection;

import com.common.dbmanage.SysDAOTool;

public class StatusManager {
	public FlowStatus getFlowstatus() {
		return flowstatus;
	}

	public void setFlowstatus(FlowStatus flowstatus) {
		this.flowstatus = flowstatus;
	}

	public TaskStatus getTaskstatus() {
		return taskstatus;
	}

	public void setTaskstatus(TaskStatus taskstatus) {
		this.taskstatus = taskstatus;
	}

	private FlowStatus flowstatus;
	private TaskStatus taskstatus;

	
	public StatusManager()
	{
		flowstatus = new FlowStatus();
		taskstatus = new TaskStatus();
	}

	public boolean updateFlowStatus(Connection conn, SysDAOTool sysdao)
	{
		if(true == flowstatus.updateFlowStatus(conn, sysdao))
		{
			return true;
		}
		return false;
	}
	
	public boolean updateTaskStatus(Connection conn, SysDAOTool sysdao)
	{
		if(true == taskstatus.updateTaskStatus(conn, sysdao))
		{
			return true;
		}
		return false;
	}
	
	public boolean updateStatus(Connection conn, SysDAOTool sysdao)
	{
		if(true == updateFlowStatus(conn, sysdao) && true == updateTaskStatus(conn, sysdao))
		{
			return true;
		}
		
		return false;
	}
	
}

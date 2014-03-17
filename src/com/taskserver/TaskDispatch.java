package com.taskserver;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.Map;

import org.apache.log4j.Logger;

import com.bean.InstanceBean;
import com.bean.InstanceTaskBean;
import com.common.dbmanage.SysDAOTool;
import com.instance.manager.TaskInfo;

public class TaskDispatch {
	public static Logger logger = Logger.getLogger(TaskDispatch.class);
	private Connection conn;
	private SysDAOTool sysdao;
	
	public TaskDispatch(Connection conn, SysDAOTool sysdao) {
		super();
		this.conn = conn;
		this.sysdao = sysdao;
	}
	
	public boolean dispachTask(SSHOperate sshoperate, InstanceBean taskinfo)
	{
		long tarmacid = taskinfo.getTarmacid();
		logger.debug("tarmacid: " + tarmacid);
		Map<String, String> conninfo = null;
		conninfo = getHostInfo(tarmacid);
		if(null == conninfo)
		{
			logger.error("can't get tarmacid to hostname info");
			return false;
		}
		
		HostInfo hostinfo = new HostInfo(conninfo.get("NODEIP"), conninfo.get("USERNAME"), conninfo.get("PASSWD"));
		
		InstanceTaskBean instancetask = new InstanceTaskBean(taskinfo.getTaskid(), taskinfo.getFlowid());
		instancetask.setBegintime(new Timestamp(System.currentTimeMillis()));
		instancetask.setExecyctime(taskinfo.getExecyctime());
		
		try {
			if(true == sshoperate.execute(instancetask, conninfo.get("SYSENV"), taskinfo.getExecmd(), taskinfo.getParas(), hostinfo))
			{
				logger.debug("ssh execute success" );
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		logger.error("put: " + taskinfo.getTaskid() + " failed");
		return false;
	}

	public Map<String, String> getHostInfo(long tarmacid)
	{
		String sql = "select * from T_TARMAC_INFO WHERE TARMACID = " + Long.toString(tarmacid);
		logger.debug("tarmacid to hostinfo: " + sql);
		Map<String, String> map = null;
		map = sysdao.queryOneSQL(conn, sql);
		if(null == map)
		{
			logger.debug("tarmacid is not exist");
			return null;
		}
		return map;
	}
	
	
	public boolean hungTask(TaskInfo taskinfo,SSHOperate sshoperate)
	{
		InstanceTaskBean instancetask = new InstanceTaskBean(taskinfo.getTaskid(), taskinfo.getFlowid());
		instancetask.setExecyctime(taskinfo.getExecyctime());
		if(true != sshoperate.writeLog(instancetask, conn, sysdao))
		{
			logger.error("update hung log failed");
		}
		return true;
	}
	
	/**
	 * @brief  查询T_INSTANCE_TASK表，是否已经有task执行的历史记录，如果有：更新，否则：插入
	 * @param  taskid 任务编号
	 * @param  flowid 流程编号
	 * @param  exectime 执行周期
	 * @param  conn 数据库连接
	 * @param  sysdao 数据库操作管理
	 * @return true: 对记录进行更新；否则：新增记录
	 */
	private boolean isNeedUpdate(long taskid, long flowid, Timestamp exectime, java.sql.Connection conn, SysDAOTool sysdao)
	{
		String sql = "select * from T_INSTANCE_TASK where TASKID = " + taskid + " and flowid = " + flowid + " and EXECYCTIME = '" + exectime + "'";
		logger.debug("query: " + sql);
		Map<String, String> map = null;
		map = sysdao.queryOneSQL(conn, sql);
		if(null == map)
		{
			return false;
		}
		return true;
	}
}

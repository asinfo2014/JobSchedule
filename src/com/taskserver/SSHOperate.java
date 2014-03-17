package com.taskserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.log4j.Logger;

import com.bean.InstanceTaskBean;
import com.common.dbmanage.SysDAOTool;
import com.instance.status.Status;

import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

public class SSHOperate {
	
	static Logger logger = Logger.getLogger(SSHOperate.class);
	public static ExecutorService exec = Executors.newCachedThreadPool();  
    public static Vector<Future<InstanceTaskBean>> results = new Vector<Future<InstanceTaskBean>>();
	public static final int SUCCESS = 0;
	public static final int FAILER = -1;

	public SSHOperate()
	{
	}
	
	/**
	 * 
	 * @param instancetask
	 * @param sshcmd
	 * @param paras
	 * @param hostinfo
	 * @return
	 * @throws Exception
	 */
	public boolean execute(InstanceTaskBean instancetask, String sysenv, String sshcmd, String paras, HostInfo hostinfo)throws Exception
	{
		//todo:
		//执行ssh命令，并将执行结果存放数据库
// 		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHH24mmss"); 
		String time = format.format(instancetask.getExecyctime());
		// source sysenv 没有同步到数据库脚本中，T_TARMAC_INFO 中没有字段sysenv，source sysenv用于使远程过去的客户端生效
		// String totalcmd = "source " + sysenv + "; " + sshcmd + " -t " + time + " " + paras;
		String totalcmd = " " + sshcmd + " -t " + time + " " + paras;
		logger.debug("sshcmd: " + totalcmd);
		//生成线程执行ssh
		TaskSSHInfo taskssh = new TaskSSHInfo(totalcmd, hostinfo.getHostname(), hostinfo.getUsername(), hostinfo.getPasswd());
		taskssh.setTaskinfo(instancetask); 
		Thread.currentThread().sleep(500);
		results.add(exec.submit(new ThreadQueue(taskssh)));
		
		return true;
	}
	
	/**
	 * @brief  ssh执行命令成功后，写日志到数据库
	 * @param  instancetask
	 * @param  conn 数据库连接句柄
	 * @param  sysdao 数据库管理操作类
	 * @return true: 写日志到数据库成功；否则：失败
	 * @remark 用于用户主动挂起流程
	 */
	public boolean writeLog(InstanceTaskBean instancetask, java.sql.Connection conn, SysDAOTool sysdao)
	{
		instancetask.setLog("hung by user");
		instancetask.setStatus(Status.HUNG);
		instancetask.setBegintime(new Timestamp(System.currentTimeMillis()));
		instancetask.setEndtime(new Timestamp(System.currentTimeMillis()));
		String sql = "";
		if(true == isNeedUpdate(instancetask.getTaskid(),instancetask.getFlowid(), instancetask.getExecyctime(), (java.sql.Connection)conn, sysdao))
		{
			sql = "update T_INSTANCE_TASK set ENDTIME = '" + instancetask.getEndtime()
					+ "', STATUS = " + instancetask.getStatus() + " where FLOWID = " + instancetask.getFlowid() + " and TASKID = " + instancetask.getTaskid() + " and EXECYCTIME = '" + instancetask.getExecyctime() + "'";
			logger.debug("update t_instance_task: " + sql);
		}
		sysdao.updateSQL(conn, sql);
		return true;
	}
	
	/**
	 * @brief  task执行后，将日志信息存入数据库
	 * @param  instancetask task的执行信息
	 * @param  sess ssh的连接会话
	 * @param  conn 数据库连接句柄
	 * @param  sysdao 数据库操作管理
	 * @return 0：写日志信息成功；-1：写日志信息失败
	 */
	private int writeLog(InstanceTaskBean instancetask, Session sess, java.sql.Connection conn, SysDAOTool sysdao)
	{
		StringBuffer log = new StringBuffer();
		String successflag = "-1";
		InputStream stdout = new StreamGobbler(sess.getStdout());
		BufferedReader br = new BufferedReader(new InputStreamReader(stdout));
		String line = "-1";
		try {
			while (true)
			{
				successflag = line;
				line = br.readLine();
				
				if (null == line)
				{
					break;
				}
				log.append("\n" + line);
			}
			br.close();
			logger.debug("execute log: " + log );
			instancetask.setLog(log.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(SUCCESS != Integer.parseInt(successflag))
		{
			instancetask.setStatus(Status.FAILER);
			sess.close();
		}
		else
		{
			instancetask.setStatus(Status.SUCCESS);
		}
		
		//执行完每个task，更新task状态
		String tasksql = "";
		String flowsql = "";
		if(true == isNeedUpdate(instancetask.getTaskid(),instancetask.getFlowid(), instancetask.getExecyctime(), (java.sql.Connection)conn, sysdao))
		{
			tasksql = "update T_INSTANCE_TASK set BEGINTIME = '" + instancetask.getBegintime() + "', ENDTIME = '" + instancetask.getEndtime()
					+ "', STATUS = " + instancetask.getStatus() + ", LOG = \"" + instancetask.getLog() + "\", EXECCNT = " + instancetask.getExeccnt()
					+ " where FLOWID = " + instancetask.getFlowid() + " and TASKID = " + instancetask.getTaskid() + " and EXECYCTIME = '" + instancetask.getExecyctime() + "'";
			logger.debug("tasksql: " + tasksql);
		}
		else
		{
			//第一次执行此task，需要新增此task的日志信息
			tasksql = "insert  into T_INSTANCE_TASK(TASKID, FLOWID, EXECYCTIME, BEGINTIME, ENDTIME, STATUS, LOG, EXECCNT) values("
					+ instancetask.getTaskid() + "," + instancetask.getFlowid() + ", '" + instancetask.getExecyctime() + "', '" 
					+ instancetask.getBegintime() + "', '" + instancetask.getEndtime() + "', " + instancetask.getStatus() + ", null, " 
					+ instancetask.getExeccnt();
			logger.debug("sql: " + tasksql);
			
		}
		sysdao.updateSQL(conn, tasksql);
		
		if(Status.SUCCESS != instancetask.getStatus())
		{
			flowsql = "update T_INSTANCE_FLOW set ENDTIME = '" + instancetask.getEndtime() + "' "
					+ ", STATUS = " + instancetask.getStatus()
					+ " where FLOWID = " + instancetask.getFlowid() 
					+ "   and EXECYCTIME = '" + instancetask.getExecyctime() + "'";
			logger.debug("flowsql: " + flowsql);
			sysdao.updateSQL(conn, flowsql);
			return FAILER;
		}
		
		return SUCCESS;
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

package com.instance.manager;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.bean.InstanceBean;
import com.bean.InstanceDepTaskBean;
import com.bean.InstanceFlowBean;
import com.bean.InstanceTaskBean;
import com.common.Tools;
import com.common.dbmanage.DBManage;
import com.common.dbmanage.SysDAOTool;
import com.instance.status.FlowStatus;
import com.instance.status.Status;
import com.instance.status.StatusManager;
import com.taskserver.SSHOperate;
import com.taskserver.TaskDispatch;

public class FlowManage {
	public static final int FIRST = 0;
	static Logger logger = Logger.getLogger(FlowManage.class);
	private StatusManager statusmng;
	private TaskDispatch taskdispatch;
	private Connection conn;
	private SysDAOTool sysdao;
	private SSHOperate sshoperate;

	public StatusManager getStatusmng() {
		return statusmng;
	}

	public void setStatusmng(StatusManager statusmng) {
		this.statusmng = statusmng;
	}

	public TaskDispatch getTaskdispatch() {
		return taskdispatch;
	}

	public void setTaskdispatch(TaskDispatch taskdispatch) {
		this.taskdispatch = taskdispatch;
	}

	public FlowManage()
	{
		conn = null;
		sysdao = new SysDAOTool();
		taskdispatch = null;
		statusmng = new StatusManager();
		sshoperate = new SSHOperate();
	}
	
	/**
	 * @brief  接收flowid, 启动流程
	 * @param  flowid 待启动的流程号
	 * @return true:启动成功，否则，启动失败
	 */
	public boolean launchFlow(long flowid, Timestamp execyctime, Map<Long, Timestamp> taskmap)
	{
		taskdispatch = new TaskDispatch(conn, sysdao);
		logger.info("*******flowid: " + flowid);
		
		//获取实例化成功的tasks
		List<InstanceBean> list = null;
		list = getTasks(flowid, execyctime);
		
		logger.info(Tools.printList(list, InstanceBean.class));
		if(list.size() == 0) 
		{
			logger.error("can't find flowid's instance");
			return false;
		}
		
		//获取当前流程的第一个task
		String firsttask = "select tins.* from T_INSTANCE tins " 
						+ "  where tins.TASKID not in " + "(  select distinct tdep.TASKID "
															+ " from T_INSTANCE_DEPTASK tdep "
															+ " where SUBSTRING(tdep.DEPTASKID, -3) <> 999 ) "
															+ "   and tins.FLOWID = " + flowid;
		logger.info("firsttask: " + firsttask);
		Map<String, String> map = null;
		map = sysdao.queryOneSQL(conn, firsttask);
		if(null == map)
		{
			logger.error("flowid: " + flowid + " has not first task");
			return false;
		}
		
		//遍历实例化成功的tasks，
		Iterator<InstanceBean> itlist = list.iterator();
		for(;itlist.hasNext();)
		{
			InstanceBean taskinfo = itlist.next();
			
			//判断当前task是否可以执行
			logger.debug("【start execute taskid】: " + taskinfo.getTaskid());
			if(true == canExtTask(taskinfo))
			{
				//判断当前task是否为流程的第一个task
				if(map.get("TASKID").equals(Long.toString(taskinfo.getTaskid())))
				{
					//设置流程执行状态和开始时间
					statusmng.getFlowstatus().status = Status.EXECUTING;
					statusmng.getFlowstatus().execyctime = taskinfo.getExecyctime();
					statusmng.getFlowstatus().flowid = taskinfo.getFlowid();
					if(true != statusmng.getFlowstatus().updateFlowStatus(conn, sysdao, 0))
					{
						logger.error("update flow: " + taskinfo.getFlowid() + " status failed");
						return false;
					}
				}
				
				//判断当前task是否为：000或者999,直接插入task的执行信息
				String taskid = Long.toString(taskinfo.getTaskid());
				String subtaskid = taskid.substring(taskid.length() - 3);
				logger.debug("subtaskid: " + subtaskid);
				if(subtaskid.equals("000") ||subtaskid.equals("999"))
				{
					Timestamp begintime = new Timestamp(System.currentTimeMillis());
					Timestamp endtime = begintime;
					String insertsql = "INSERT INTO T_INSTANCE_TASK(TASKID,FLOWID,EXECYCTIME, BEGINTIME,ENDTIME, STATUS ,  LOG , EXECCNT )" +
							"VALUES (" + taskinfo.getTaskid() + "," + taskinfo.getFlowid() + ",'" + taskinfo.getExecyctime() + "','"+ begintime + "','" + endtime + "'," + Status.SUCCESS + ", \"empty task \"" + " , 1)";
					
					logger.debug("insertsql: " + insertsql);
					if(true != sysdao.updateSQL(conn, insertsql))
					{
						logger.error("update task: " + taskinfo.getTaskid() + " failed");
						return false;
					}
					if(subtaskid.equals("999"))
					{
						FlowStatus flowstatus = new FlowStatus();
						flowstatus.status = Status.SUCCESS;
						flowstatus.flowid = taskinfo.getFlowid();
						flowstatus.execyctime = taskinfo.getExecyctime();
						
						if(true != flowstatus.updateFlowStatus(conn, sysdao, 1))
						{
							logger.error("update flow: " + taskinfo.getFlowid() + " failed");
						}
					}
					continue;
				}
				
				//判断当前账期的task是否为第一次执行
				if(null == taskmap || null == taskmap.get(taskinfo.getTaskid()))
				{
					//插入TASK 执行信息
					Timestamp begintime = new Timestamp(System.currentTimeMillis());
					String insertsql = "INSERT INTO T_INSTANCE_TASK(TASKID,FLOWID,EXECYCTIME, BEGINTIME,ENDTIME, STATUS ,  LOG , EXECCNT )" +
							"VALUES (" + taskinfo.getTaskid() + "," + taskinfo.getFlowid() + ",'" + taskinfo.getExecyctime() + "','"+ begintime + "',NULL," + Status.EXECUTING + ", \"\"" + " , 1)";
					logger.debug("insertsql: " + insertsql);
					
					if(true != sysdao.updateSQL(conn, insertsql))
					{
						logger.error("update task: " + taskinfo.getTaskid() + " failed");
						return false;
					}
				}
				/*else{
					statusmng.getTaskstatus().status = Status.EXECUTING;
					statusmng.getTaskstatus().execyctime = taskinfo.getExecyctime();
					statusmng.getTaskstatus().taskid = taskinfo.getTaskid();
					if(true != statusmng.updateTaskStatus(conn, sysdao))
					{
						logger.error("update taskid: " + taskinfo.getTaskid() + " failed" );
						return false;
					}
				}*/
				
				if(true == launchTask(taskinfo))
				{
					logger.info("flow: " + taskinfo.getFlowid() + " task: " + taskinfo.getTaskid() + " send success" );
				}
			}
		}
		return true;
	}

	/**
	 * @brief 用户挂起的流程，重新启动
	 * @param flowid
	 * @return true: 修改状态成功，等待定时器启动流程，否则，修改状态失败
	 * @remark 此函数属于外部接口，供外部功能模块调用
	 */
	@SuppressWarnings("unchecked")
	public boolean reLaunchFlow(long flowid, int locflag)
	{
		//建立数据库连接
		conn = DBManage.getConnection();
		
		
		//查询指定重启的流程是否存在
		String insFlowsql = "SELECT t1.* FROM T_INSTANCE t1 " +
				" LEFT JOIN T_INSTANCE_FLOW t2 " +
				" ON t1.FLOWID = t2.FLOWID " + 
				" AND t1.EXECYCTIME = t2.EXECYCTIME " + 
				" WHERE  t1.FLOWID = " + flowid ;
		Map<String, String> insFlowmap = null;
		insFlowmap = sysdao.queryOneSQL(conn, insFlowsql);
		if(null == insFlowmap)
		{
			logger.warn("flow：" + flowid + " has not instance");
			DBManage.close(conn);
			return true;
		}
		
		
		//查询指定重启的流程是否存在
		String flowsql = "select FLOWID, MAX(EXECYCTIME) EXECYCTIME " +
				" from T_INSTANCE_FLOW " +
				"where FLOWID = " + flowid + 
				"  and Status in (" + Status.HUNG + "," + Status.FAILER + ")";
		Map<String, String> flowmap = null;
		flowmap = sysdao.queryOneSQL(conn, flowsql);
		if(null == flowmap)
		{
			logger.warn("flow：" + flowid + " was running,can not relaunch");
			DBManage.close(conn);
			return true;
		}
		
		//从挂起处执行流程
		if(1 == locflag)
		{
			//判断是否有出错的任务
			String tasksql = "select * " +
					"  from T_INSTANCE_TASK " +
					" where FLOWID = " + flowid + 
					"   and EXECYCTIME = '" + flowmap.get("EXECYCTIME") + "'" +
					"  and Status <> " + Status.SUCCESS;  
			logger.debug("sql: " + tasksql);
			Map<String, String> taskmap = null;
			taskmap = sysdao.queryOneSQL(conn, tasksql);
			if(null == taskmap)
			{
				logger.warn("flowid: " + flowid + " has not instance");
				DBManage.close(conn);
				return true;
			}
			
			//删除task历史记录
			String deltask = "delete from T_INSTANCE_TASK "
					+ " where TASKID = " + taskmap.get("TASKID") + 
					"     and EXECYCTIME = '" + taskmap.get("EXECYCTIME") + "'";
			logger.debug("deltask:" + deltask);
			if(true != sysdao.updateSQL(conn, deltask))
			{
				DBManage.close(conn);
				logger.error("delete taskid: " + flowid + " 's tasks failed");
				return false;
			}
		}
		
		//从新开始执行流程, 修改整个流程的所有task状态为INIT
		if(0 == locflag)
		{
			//删除task的历史记录
			String delsql = "delete from T_INSTANCE_TASK "
					+ "where FLOWID = " + flowid 
					+ "  and EXECYCTIME = '" + flowmap.get("EXECYCTIME") + "'";
			logger.debug(" delsql: " + delsql);
			
			if(true != sysdao.updateSQL(conn, delsql))
			{
				DBManage.close(conn);
				logger.error("delete flowid: " + flowid + " 's tasks failed");
				return false;
			}
		}
		
		//更新流程状态
		String str = flowmap.get("EXECYCTIME");
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SS");
		Timestamp time = null;
		try {
			time = new Timestamp(format.parse(str).getTime());
		} catch (ParseException e) {
			logger.debug("time: " + time);
			e.printStackTrace();
		}
		statusmng.getFlowstatus().flowid = flowid;
		statusmng.getFlowstatus().status = Status.EXECUTING;
		statusmng.getFlowstatus().execyctime = time;

		if(true != statusmng.getFlowstatus().updateFlowStatus(conn, sysdao))
		{
			logger.error("Update flowid: " + flowid + " status failed");
			DBManage.close(conn);
			return false;
		}
		DBManage.close(conn);
		return true;
	}
	/**
	 * @brief  启动流程中的单个任务，
	 * @param  taskinfo 要启动task的详细信息
	 * @return true: 启动成功，否则，启动失败
	 */
	public boolean launchTask(InstanceBean taskinfo)
	{
		logger.info( "【 put 】:" + taskinfo.getTaskid());
		if(true == taskdispatch.dispachTask(sshoperate, taskinfo))
		{
			return true;
		}
		return false;
	}
	
	/**
	 * @brief  流程执行成功，删除流程实例
	 * @param  flowid 要删除的流程编号
	 * @param  execyctime 流程的执行周期
	 * @return true：删除成功；否则，删除失败
	 */
	public boolean deleteInstance(long flowid,  Timestamp execyctime)
	{
		String sqldelflow = "delete from T_INSTANCE_FLOW where FLOWID = " + flowid + " and EXECYCTIME = '" + execyctime + "'";
		String sqldeltasks = "delete from T_INSTANCE_TASK where FLOWID = " + flowid + " and EXECYCTIME = '" + execyctime + "'";
		
		try {
			if(true != sysdao.updateSQL(conn, sqldelflow, null))
			{
				logger.error("delete flow: " + flowid + " failed in T_INSTANCE_FLOW");
				DBManage.rollback(conn);
				return false;
			}
			if(true != sysdao.updateSQL(conn, sqldeltasks, null))
			{
				logger.error("delete flow: " + flowid + " failed in T_INSTANCE_TASK");
				DBManage.rollback(conn);
				return false;
			}
			
		} catch (SQLException e) {
			logger.error("delete instance flow or tasks failed");
			e.printStackTrace();
			return false;
		}
		DBManage.commit(conn);
		return true;
	}

	/**
	 * @brief 定时器调用此方法，查找可以执行的流程
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public boolean launchFlows()
	{		
		//获取所有可执行的流程：状态为INIT或者为EXECUTING, 且执行周期已经到了
		conn = DBManage.getConnection();
		logger.info("【start scan flows】");
		
		Timestamp  curtime = new Timestamp(System.currentTimeMillis());
		String initsql = "select * from T_INSTANCE_FLOW " +
				"where STATUS in (" + Status.INIT + "," + Status.EXECUTING + ")" +
				"  and EXECYCTIME <= '" + curtime + "'";
		
		List<InstanceFlowBean> initlist = null;
		try {
			initlist = sysdao.queryAll(conn, initsql, null, InstanceFlowBean.class); 
		} catch (SQLException e) {
			logger.error("获取可执行的流程失败");
			e.printStackTrace();
		}
		if(0 == initlist.size())
		{
			logger.info("haha: 当前周期没有可以执行的流程，或者当前周期的流程已经在执行还没结束");
			DBManage.close(conn);
			return false;
		}
		
		logger.info("【本周起可执行的流程】\n" + Tools.printList(initlist, InstanceFlowBean.class));
		Map<Long, Timestamp> map = canEXTTaskRecord(initlist);
		//从流程list中取出本周期能够执行的流程，开始执行
		Iterator<InstanceFlowBean> it = initlist.iterator();
		for(; it.hasNext();)
		{
			InstanceFlowBean flowinfo = it.next();
			logger.debug("【start execute flowid】: " + flowinfo.getFlowid());
			if(true != launchFlow(flowinfo.getFlowid(),flowinfo.getExecyctime(), map))
			{
				logger.error("flowid: " + flowinfo.getFlowid() + " execyctime: " + flowinfo.getExecyctime() + " failed ");
				DBManage.close(conn);
				return false;
			}
		}
		DBManage.close(conn);
		return true;
	}
	
	/**
	 * 
	 * @param flowid
	 * @return
	 */
	public boolean closeFlow(long flowid)
	{
		conn = DBManage.getConnection();
		taskdispatch = new TaskDispatch(conn, sysdao);
		
		//获取要挂起的流程信息
		String flowsql = "select * from T_INSTANCE_FLOW " +
				 " where FLOWID = " + flowid + 
				 "   and STATUS = " + Status.EXECUTING;
		
		Map<String, String> flowmap = null;
		flowmap = sysdao.queryOneSQL(conn, flowsql);
		if(null == flowmap)
		{
			logger.warn("flowid: " + flowid + " not exists");
			return true;
		}
		
		//更新流程状态为：挂起
		FlowStatus flowstatus = new FlowStatus();
		flowstatus.flowid = flowid;
		flowstatus.status = Status.HUNG;
		String str = flowmap.get("EXECYCTIME");
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SS");
		try {
			flowstatus.execyctime = new Timestamp(format.parse(str).getTime());
		} catch (ParseException e) {
			logger.debug("time: " + flowstatus.execyctime);
			e.printStackTrace();
		}
		
		if(true != flowstatus.updateFlowStatus(conn, sysdao, 1))
		{
			logger.error("hung flowid: " + flowid + " failed");
			DBManage.close(conn);
			return false;
		}
		DBManage.close(conn);
		return true;
	}

	/**
	 * @brief 查找T_INSTANCE表，并与T_INSTANCE_FLOW表关联，找出当前周期可以执行的tasks
	 * @param flowid 要执行的流程
	 * @param execyctime 要执行的流程的tasks的周期
	 * @return 
	 */
	@SuppressWarnings("unchecked")
	private List<InstanceBean> getTasks(long flowid, Timestamp execyctime)
	{	
		List<InstanceBean> list = new ArrayList();
		String sql = "select tins.* from T_INSTANCE tins " +
				"left join T_INSTANCE_FLOW tinf " +
				"       on tins.FLOWID = tinf.FLOWID " +
				"      and tins.EXECYCTIME = tinf.EXECYCTIME " +
				"    where tins.TASKID not in (select TASKID from T_INSTANCE_TASK " + 
											" where FLOWID = " + flowid + 
											"   and EXECYCTIME ='" + execyctime + "'" +
											"   and STATUS = " + Status.SUCCESS + ")" +
			    "      and tins.FLOWID = " + flowid + 
				"      and tins.EXECYCTIME = '" + execyctime + "'";
		logger.debug("query : " + sql );
		try {
			list = sysdao.queryAll(conn, sql, null, InstanceBean.class);
		} catch (SQLException e) {
			logger.error("execute sql: " + sql + " failed");
		}
		return list;
	}
	
	/**
	 * @brief  判断当前task依赖的task是否执行成功
	 * @param  flowid
	 * @param  taskid
	 * @param  execyctime
	 * @return
	 */
	private boolean canExtTask(InstanceBean taskinfo)
	{	
		//判断是否
		Map<String, String> status = null;
		String initsql = "select STATUS from T_INSTANCE_TASK "
				+ " where TASKID = " + taskinfo.getTaskid() 
				+ "   and EXECYCTIME = '" + taskinfo.getExecyctime() + "'";
		logger.debug("initsql: " + initsql);
		status = sysdao.queryOneSQL(conn, initsql);
		
		if(status != null)
		{
			if(status.get("STATUS").equals("2"))
			{
				return false;
			}
		}
		
		//依赖的任务个数
		Map<String, String> depmap = null;
		String depsql = "select count(*) count from T_INSTANCE_DEPTASK "
					  + " where TASKID = " + taskinfo.getTaskid() 
				      + "   and DEPEXECYCTIME = '" + taskinfo.getExecyctime() + "'";
		logger.debug("depsql: " + depsql);
		depmap = sysdao.queryOneSQL(conn, depsql);
		//当前task没有依赖
		if(null == depmap)
		{
			return true;
		}
		String depcount = depmap.get("count");
		
		//执行成功的依赖任务个数
		Map<String, String> deptaskmap = null;
		String deptasksql = "select count(*) count from T_INSTANCE_TASK "
					  + " where TASKID in (select DEPTASKID from T_INSTANCE_DEPTASK "
					  				    + " where TASKID = " + taskinfo.getTaskid() 
					  				    + "   and EXECYCTIME = '" + taskinfo.getExecyctime() + "') "
					  + "   and STATUS = " + Status.SUCCESS 
					  + "   and EXECYCTIME = '" + taskinfo.getExecyctime() + "'";
		logger.debug("depsql: " + deptasksql);	
		deptaskmap = sysdao.queryOneSQL(conn, deptasksql);
		if(null == deptaskmap)
		{
			return false;
		}
		String deptaskcount = deptaskmap.get("count");
		
		logger.debug("-----depcount: " + depcount + " deptaskcount: " + deptaskcount);
		if(depcount.equals(deptaskcount))
		{
			return true;
		}
		return false;
	}
	
	private Map<Long, Timestamp> canEXTTaskRecord(List<InstanceFlowBean> flowlist)
	{
		String flows = "" ;
		String times = "" ;
		Iterator<InstanceFlowBean> it = flowlist.iterator();
		for(; it.hasNext();)
		{
			InstanceFlowBean flowinfo = it.next();
			flows += flowinfo.getFlowid() + ","; 
			times += "'" + flowinfo.getExecyctime() + "'" + ",";	
		}
		flows = "(" + flows.substring(0, flows.length()-1 ) + ")";
		times = "(" + times.substring(0, times.length()-1 ) + ")";
		
		//获取当前周期的flow下的所有task记录
		String tasksql = "select * from T_INSTANCE_TASK"
					   + " where FLOWID in " + flows 
					   + "   and EXECYCTIME in " + times
					   + "   and STATUS <> " + Status.SUCCESS;
		logger.debug(" tasks: " + tasksql);
		
		List tasklist = null;
		try {
			tasklist = sysdao.queryAll(conn, tasksql, null, InstanceTaskBean.class);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(tasklist.size() == 0)
		{
			return null;
		}
		
		Map<Long, Timestamp> map = new HashMap();
		Iterator<InstanceTaskBean> ittask = tasklist.iterator();
		for(; ittask.hasNext();)
		{
			InstanceTaskBean taskinfo = ittask.next();
			map.put(taskinfo.getTaskid(), taskinfo.getExecyctime());
		}
		return map;
	}
	 
	
	public static void main(String []args)
	{
		FlowManage flowlaunch = new FlowManage();
		flowlaunch.launchFlows();
	}
	
}

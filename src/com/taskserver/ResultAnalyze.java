package com.taskserver;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.apache.log4j.Logger;

import com.bean.InstanceFlowBean;
import com.bean.InstanceTaskBean;
import com.common.dbmanage.DBManage;
import com.common.dbmanage.SysDAOTool;
import com.instance.status.FlowStatus;
import com.instance.status.Status;
import com.instance.status.TaskStatus;

public class ResultAnalyze {
	static Logger logger = Logger.getLogger(ResultAnalyze.class);
	static int tasknum = 0;
	static int curnum = 0;
	private Connection conn;
	private SysDAOTool sysdao;

	public ResultAnalyze() {
		this.conn = null;
		this.sysdao = null;
	}

	/**
	 * 
	 * @param taskinfo
	 * @return
	 */
	public boolean executeTaskSuccess(InstanceTaskBean taskinfo) {
		// 根据执行结果更新数据库状态
		// 更新task状态
		String tasksql = "";

		tasksql = "update T_INSTANCE_TASK set ENDTIME = '"
				+ taskinfo.getEndtime() + "', STATUS = " + taskinfo.getStatus()
				+ ",  LOG = CONCAT(LOG,\"" + taskinfo.getLog() + "\")"
				+ " where FLOWID = " + taskinfo.getFlowid()
				+ "   and TASKID = " + taskinfo.getTaskid()
				+ "   and EXECYCTIME = '" + taskinfo.getExecyctime() + "'";
		logger.debug("tasksql: " + tasksql);

		// debug info
		logger.info("taskinfo： " + taskinfo.getFlowid() + " "
				+ taskinfo.getTaskid() + " " + taskinfo.getExecyctime() + " "
				+ taskinfo.getStatus());
		// 更新task的状态
		if (true != sysdao.updateSQL(conn, tasksql)) {
			logger.error("update: " + taskinfo.getTaskid() + " failed");
		}

		// 如果任务执行失败更新流程状态为：FAILER
		if (Status.SUCCESS != taskinfo.getStatus()) {
			FlowStatus flowstatus = new FlowStatus();
			flowstatus.execyctime = taskinfo.getExecyctime();
			flowstatus.flowid = taskinfo.getFlowid();
			flowstatus.status = taskinfo.getStatus();

			if (true != flowstatus.updateFlowStatus(conn, sysdao, 1)) {
				logger.error("update: " + taskinfo.getFlowid() + " failed");
			}
		}
		return true;
	}

	public boolean resultScan() {
		Timestamp execyctime = null;
		logger.debug("result: " + SSHOperate.results.size());
		conn = DBManage.getConnection();
		sysdao = new SysDAOTool();

		// 如果用户挂起流程，执行此处理逻辑
		String hungsql = "select DISTINCT tinf.FLOWID "
				+ "  from T_INSTANCE_FLOW tinf join T_INSTANCE_TASK tint "
				+ "    on tinf.FLOWID = tint.FLOWID " + " where tinf.STATUS = "
				+ Status.HUNG + "   and tint.STATUS = " + Status.EXECUTING;
		List<InstanceFlowBean> flowlist = new ArrayList();
		try {
			flowlist = sysdao.queryAll(conn, hungsql, null,
					InstanceFlowBean.class);
		} catch (SQLException e1) {
			logger.error("get hung flows failes");
			e1.printStackTrace();
		}

		int len = SSHOperate.results.size();
		for (int index = len - 1; index >= 0; index--) {
			Future<InstanceTaskBean> fs = SSHOperate.results.get(index);

			if (flowlist.size() < 1) {
				if (fs.isDone()) {
					try {
						if (true != executeTaskSuccess(fs.get())) {
							logger.error("update: " + fs.get().getTaskid()
									+ " failed");
						}

					} catch (InterruptedException e) {
						e.printStackTrace();
					} catch (ExecutionException e) {
						e.printStackTrace();
					}
					SSHOperate.results.remove(index);
				}
			} else {
				// 遍历flowlist，当前任务是否为挂起流程的task
				Iterator<InstanceFlowBean> itflow = flowlist.iterator();
				for (; itflow.hasNext();) {
					long flowid = itflow.next().getFlowid();
					try {
						if (flowid == fs.get().getFlowid()) {
							    fs.cancel(true);
								TaskStatus taskstatus = new TaskStatus();
								taskstatus.taskid = fs.get().getTaskid();
								taskstatus.status = Status.HUNG;
								taskstatus.execyctime = fs.get()
										.getExecyctime();
								if (true != taskstatus.updateTaskStatus(conn,
										sysdao, 1)) {
									logger.error("update taskid: "
											+ taskstatus.taskid
											+ " status failed");
								}
								SSHOperate.results.remove(index);
								break;
							}
					} catch (InterruptedException e) {
						logger.error("get flowid: " + flowid + " failed");
						e.printStackTrace();
					} catch (ExecutionException e) {
						logger.error("get flowid: " + flowid + " failed");
						e.printStackTrace();
					}
				}
			}
		}

		DBManage.close(conn);
		return true;
	}

}

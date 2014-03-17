package com.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bean.DepFlowDescBean;
import com.bean.FlowBean;
import com.bean.FlowTaskBean;
import com.common.ServiceException;
import com.common.dbmanage.SysDAOTool;

public class FlowDao {

	SysDAOTool sysdao = new SysDAOTool();

	public FlowBean getFlowByFlowid(Connection conn, long flowid)
			throws ServiceException {
		String sql = " SELECT t.BEGINTIME,t.DIRID,t.ENDTIME,t.EXECYCTIME,t.FAILED_RETRY_CNT,t.FLOWDESC "
				+ ",t.FLOWID,t.FLOWNAME,t.FLOWSTATUS,t.FLOWTYPE,t.TIMEINTV,t.TIMEKIND "
				+ ",t1.DIRNAME DIRNAME "
				+ " FROM T_FLOW t JOIN T_FLOW_DIRECTORY t1 ON t1.DIRID = t.DIRID where t.FLOWID = ? ";
		List paras = new ArrayList();
		paras.add(flowid);
		FlowBean fBean;
		try {
			fBean = (FlowBean) sysdao.querySimpleObj(conn, sql, paras,
					FlowBean.class);
		} catch (SQLException e) {
			throw new ServiceException("getFlowByFlowid 查询出错", e);
		}

		sql = "SELECT * FROM T_FLOW_TASK t WHERE t.FLOWID=?";
		List<FlowTaskBean> ftbeans;
		try {
			ftbeans = sysdao.queryAll(conn, sql, paras, FlowTaskBean.class);
		} catch (SQLException e) {
			throw new ServiceException("根据flowid查询flow task", e);
		}
		fBean.setFlowTaskList(ftbeans);
		return fBean;
	}

	public List getAllFlow(Connection conn) throws ServiceException {
		String sql = "SELECT t.BEGINTIME,t.DIRID,t.ENDTIME,t.EXECYCTIME,t.FAILED_RETRY_CNT,t.FLOWDESC "
				+ ",t.FLOWID,t.FLOWNAME,t.FLOWSTATUS,t.FLOWTYPE,t.TIMEINTV,t.TIMEKIND "
				+ ",t1.DIRNAME DIRNAME "
				+ " FROM T_FLOW t JOIN T_FLOW_DIRECTORY t1 ON t1.DIRID = t.DIRID";
		try {
			return sysdao.queryAll(conn, sql, null, FlowBean.class);
		} catch (SQLException e) {
			throw new ServiceException("查询所有流程失败", e);
		}
	}

	public boolean updateFlowStatus(Connection conn, long flowid, int status)
			throws ServiceException {

		SysDAOTool sysdao = new SysDAOTool();
		// 需要确认FLOW
		String sql = "update  T_FLOW t set t.flowstatus = ? where t.flowid = ? ";
		List paras = new ArrayList();
		paras.add(status);
		paras.add(flowid);
		try {
			return sysdao.updateSQL(conn, sql, paras);
		} catch (SQLException e) {
			throw new ServiceException("更新流程状态失败", e);
		}
	}

	public Boolean editFlow(Connection conn, FlowBean flowBean)
			throws ServiceException {
		String sql = "UPDATE T_FLOW               "
				+ "SET  BEGINTIME = ?,         "
				+ "  ENDTIME = ?,              "
				+ "  TIMEKIND = ?,             "
				+ "  TIMEINTV = ?,             "
				+ "  FLOWNAME = ?,             "
				+ "  FLOWDESC = ?,             "
				+ "  FLOWTYPE = ?,             "
				+ "  FLOWSTATUS = ?,           "
				+ "  FAILED_RETRY_CNT = ?,     "
				+ "  DIRID = ?,                "
				+ "  EXECYCTIME = ?            "
				+ "WHERE FLOWID = ?            ";

		List paras = new ArrayList();
		paras.add(flowBean.getBegintime());
		paras.add(flowBean.getEndtime());
		paras.add(flowBean.getTimekind());
		paras.add(flowBean.getTimeintv());
		paras.add(flowBean.getFlowname());
		paras.add(flowBean.getFlowdesc());
		paras.add(flowBean.getFlowtype());
		paras.add(flowBean.getFlowstatus());
		paras.add(flowBean.getFailedRetryCnt());
		paras.add(flowBean.getDirid());
		paras.add(flowBean.getExecyctime());// 这个配置时不可修改

		paras.add(flowBean.getFlowid());
        
		try {
			return sysdao.updateSQL(conn, sql, paras);
		} catch (SQLException e) {
			throw new ServiceException("更新流程信息失败", e);
		}
	}

	/**
	 * 删除流程
	 * 
	 * @param conn
	 * @param flowid
	 *            流程id
	 * @return
	 * @throws SQLException
	 */
	public boolean deleteFlow(Connection conn, long flowid)
			throws ServiceException {
		String sql = " DELETE from T_FLOW where FLOWID = ?";
		List paras = new ArrayList();
		paras.add(flowid);
		try {
			return sysdao.updateSQL(conn, sql, paras);
		} catch (SQLException e) {
			throw new ServiceException("删除流程失败", e);
		}
	}

	/**
	 * 删除流程的依赖流程记录
	 * 
	 * @param conn
	 * @param flowid
	 * @return
	 * @throws SQLException
	 */
	public boolean deleteDepFlow(Connection conn, long flowid)
			throws ServiceException {
		String sql = " DELETE from T_DEPFLOW where FLOWID = ?";
		List paras = new ArrayList();
		paras.add(flowid);
		try {
			return sysdao.updateSQL(conn, sql, paras);
		} catch (SQLException e) {
			throw new ServiceException("删除依赖流程", e);
		}
	}
	
	/**
	 * 删除流程内任务依赖关系信息表
	 * 
	 * @param conn
	 * @param flowid
	 * @return
	 * @throws SQLException
	 */
	public boolean deleteFlowDepTask(Connection conn, long flowid)
			throws ServiceException {
		String sql = " DELETE from T_FLOW_DEPTASK where FLOWID = ?";
		List paras = new ArrayList();
		paras.add(flowid);
		try {
			return sysdao.updateSQL(conn, sql, paras);
		} catch (SQLException e) {
			throw new ServiceException("删除FLOW_DEPTASK", e);
		}
	}
	
	
	
	/**
	 * 删除流程任务
	 * 
	 * @param conn
	 * @param flowid
	 * @return
	 * @throws SQLException
	 */
	public boolean deleteFlowTask(Connection conn, long flowid)
			throws ServiceException {
		String sql = " DELETE from T_FLOW_TASK where FLOWID = ?";
		List paras = new ArrayList();
		paras.add(flowid);
		try {
			return sysdao.updateSQL(conn, sql, paras);
		} catch (SQLException e) {
			throw new ServiceException("删除flow_task", e);
		}
	}
	

	

	public List<DepFlowDescBean> queryDepFlowDesc(Connection conn, List paras)
			throws SQLException {
		String sql = " select tdflow.flowid flowid, tdflow.depflowid depflowid , tdflow.kind kind, "
				+ " tflow.begintime depbegintime ,tflow.timekind deptimekind , tflow.timeintv deptimeintv "
				+ "from T_FLOW tflow "
				+ "join T_DEPFLOW tdflow on tflow.flowid = tdflow.depflowid "
				+ "where tdflow.flowid = ?";

		List<DepFlowDescBean> depflows = sysdao.queryAll(conn, sql, paras,
				DepFlowDescBean.class);
		return depflows;
	}

	public boolean updateFlowExetime(Connection conn, List paras)
			throws SQLException {
		String sql = " update T_FLOW " + "set execyctime =? "
				+ "where flowid =?";
		return sysdao.updateSQL(conn, sql, paras);
	}

}

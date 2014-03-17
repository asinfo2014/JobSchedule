package com.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.bean.FlowDirBean;
import com.bean.InstanceFlowBean;
import com.bean.InstanceTaskBean;
import com.bean.ManageFlowBean;
import com.common.ServiceException;
import com.common.dbmanage.SysDAOTool;

public class InstanceFlowDao {
	static Logger logger = Logger.getLogger(InstanceFlowDao.class);
	SysDAOTool sysdao = new SysDAOTool();

	public List getRunningInstanceFlow(Connection conn) throws ServiceException {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT t1.FLOWNAME flowname, t.BEGINTIME, t.DESCRIPTION, t.ENDTIME, t.EXECYCTIME, t.FLOWID, t.STATUS   ");
		sql.append(" FROM T_INSTANCE_FLOW t JOIN T_FLOW t1                                                                  ");
		sql.append(" ON t.FLOWID = t1.FLOWID                                                                                ");
		sql.append(" WHERE t.EXECYCTIME =                                                                                   ");
		sql.append("       (SELECT  MAX(tt.EXECYCTIME)                                                                      ");
		sql.append("        FROM  T_INSTANCE_FLOW tt                                                                        ");
		sql.append("        WHERE t.FLOWID = tt.FLOWID                                                                      ");
		sql.append("        AND tt.status IN (0, 2, 3, 4, 6))                                                               ");
		sql.append(" AND ( t.status IN (0, 2, 3, 4, 6)  OR DATEDIFF(NOW(), t.begintime) < 1 )                               ");

		try {
			return sysdao.queryAll(conn, sql.toString(), null,
					InstanceFlowBean.class);
		} catch (Exception e) {
			throw new ServiceException("获取正在运行流程失败", e);
		}
	}

	public List getInstanceFlowByDir(Connection conn, long flowDir)
			throws ServiceException {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT t1.FLOWNAME flowname, t.BEGINTIME, t.DESCRIPTION, t.ENDTIME, t.EXECYCTIME, t.FLOWID, t.STATUS   ");
		sql.append(" FROM T_INSTANCE_FLOW t JOIN T_FLOW t1                                                                  ");
		sql.append(" ON t.FLOWID = t1.FLOWID                                                                                ");
		sql.append(" WHERE t.EXECYCTIME =                                                                                   ");
		sql.append("       (SELECT  MAX(tt.EXECYCTIME)                                                                      ");
		sql.append("        FROM  T_INSTANCE_FLOW tt                                                                        ");
		sql.append("        WHERE t.FLOWID = tt.FLOWID                                                                      ");
		sql.append("        AND tt.status IN (0, 2, 3, 4, 6))                                                               ");
		sql.append(" AND ( t.status IN (0, 2, 3, 4, 6)  OR DATEDIFF(NOW(), t.begintime) < 1 )                               ");
		sql.append(" AND t.flowid in ( select flowid from T_FLOW t,T_FLOW_DIRECTORY fd                                      ");
		sql.append("                   where  t.dirid = fd.DIRID                                                            ");
		sql.append("                   AND    (fd.DIRID=? OR fd.DEPDIRID=? ))                                               ");

		List paras = new ArrayList();
		paras.add(flowDir);
		paras.add(flowDir);
		try {
			return sysdao.queryAll(conn, sql.toString(), paras,
					InstanceFlowBean.class);
		} catch (Exception e) {
			throw new ServiceException("获取正在运行流程失败", e);
		}
	}

	public List getAllFlowDir(Connection conn) throws ServiceException {
		String sql = "select * from T_FLOW_DIRECTORY t";

		try {
			return sysdao.queryAll(conn, sql, null, FlowDirBean.class);
		} catch (Exception e) {
			throw new ServiceException("获取所有目录失败", e);
		}
	}

	public List getrunningInstanceTask(Connection conn, String flowid,
			Timestamp execyctime) throws ServiceException {
		String sql = " SELECT T.TASKID,T2.TASKNAME taskname "
				+ ",T.FLOWID,T1.FLOWNAME flowname "
				+ ",T.BEGINTIME,T.ENDTIME,T.EXECCNT,T.EXECYCTIME,T.LOG,T.STATUS "
				+ "FROM T_INSTANCE_TASK T JOIN T_FLOW_TASK T2 "
				+ " ON T.TASKID = T2.TASKID "
				+ " JOIN T_FLOW T1 "
				+ " ON T.FLOWID = T1.FLOWID WHERE T.FLOWID = ? AND T.EXECYCTIME=? ORDER BY T.TASKID";

		List paras = new ArrayList();
		paras.add(flowid);
		paras.add(execyctime);
		try {
			return sysdao.queryAll(conn, sql, paras, InstanceTaskBean.class);
		} catch (Exception e) {
			throw new ServiceException("获取流程task失败", e);
		}
	}

	public InstanceFlowBean getInsFlowByFlowid(Connection conn, long flowid)
			throws ServiceException {
		String sql = "select * from  T_INSTANCE_FLOW t where t.flowid = ?";

		List paras = new ArrayList();
		paras.add(flowid);

		try {
			return (InstanceFlowBean) sysdao.querySimpleObj(conn, sql, paras,
					InstanceFlowBean.class);
		} catch (Exception e) {
			throw new ServiceException("获取流程对象失败", e);
		}
	}

	public List<ManageFlowBean> queryValidFlow(Connection conn)
			throws SQLException {
		String sql = "select IFNULL(tflow.flowid,0) fflowid,tflow.begintime begintime ,tflow.endtime endtime ,"
				+ "tflow.execyctime execyctime,tflow.timekind timekind ,tflow.timeintv timeintv ,tflow.failed_retry_cnt failedRetryCnt , IFNULL(tins.flowid,-1) iflowid "
				+ "from  T_FLOW tflow "
				+ "left join   T_INSTANCE_FLOW tins on (tflow.flowid=tins.flowid and  UNIX_TIMESTAMP(tflow.execyctime)=UNIX_TIMESTAMP(tins.execyctime))  "
				+ "where tflow.flowstatus = 1 and (tflow.endtime is null or UNIX_TIMESTAMP(tflow.execyctime) < UNIX_TIMESTAMP(tflow.endtime)) and (tins.flowid is null or tins.status=0 )";

		List<ManageFlowBean> flows = sysdao.queryAll(conn, sql, null,
				ManageFlowBean.class);
		return flows;
	}

	public boolean addInstanceFlowTask(Connection conn, List paras)
			throws SQLException {
		String sql = "insert into T_INSTANCE(taskid,flowid,execyctime,tarmacid,execmd,failed_retry_cnt,paras) "
				+ "select taskid,flowid,?,tarmacid,execmd,?,paras "
				+ "from T_FLOW_TASK " + "where flowid =?";

		return sysdao.updateSQL(conn, sql, paras);

	}

	public boolean addInstanceFlowDepTask(Connection conn, List paras)
			throws SQLException {
		String sql = "insert into T_INSTANCE_DEPTASK(taskid,flowid,deptaskid,depflowid,depexecyctime,depkind) "
				+ "select nexttaskid,flowid,taskid,flowid,?,1 "
				+ "from T_FLOW_DEPTASK " + "where flowid=?";

		return sysdao.updateSQL(conn, sql, paras);
	}

	/*
	 * public boolean addInstanceFlowDepTask2(Connection conn,List paras)throws
	 * SQLException { String sql =
	 * "insert into T_INSTANCE_DEPTASK(taskid,flowid,deptaskid,depflowid,depexecyctime,depkind) "
	 * + "select nexttaskid,flowid,taskid,flowid,?,1 " + "from T_FLOW_TASK " +
	 * "where flowid=?";
	 * 
	 * return sysdao.updateSQL(conn, sql, paras); }
	 */
	public boolean addInstanceDepTask(Connection conn, List paras)
			throws SQLException {
		String sql = "insert into T_INSTANCE_DEPTASK(taskid,flowid,deptaskid,depflowid,depexecyctime,depkind) "
				+ " values(?,?,?,?,?,?) ";

		return sysdao.updateSQL(conn, sql, paras);
	}

	public boolean addInstanceFlow(Connection conn, List paras)
			throws SQLException {
		String sql = "insert into T_INSTANCE_FLOW(flowid,execyctime,begintime,endtime,status,description) "
				+ " values(?,?,?,?,?,?) ";

		return sysdao.updateSQL(conn, sql, paras);
	}

	public boolean preInstanceFlow(Connection conn, List paras)
			throws SQLException {
		String sql = "insert into T_INSTANCE_FLOW(flowid,execyctime,begintime,endtime,status,description) "
				+ " values(?,?,null,null,?,'') ";

		return sysdao.updateSQL(conn, sql, paras);
	}

	public boolean updateInstanceFlowStatus(Connection conn, List paras)
			throws SQLException {
		String sql = "update T_INSTANCE_FLOW  " + "set status =? "
				+ "where flowid =? and execyctime =?";

		return sysdao.updateSQL(conn, sql, paras);
	}

	public boolean deleteInstanceFlowTask(Connection conn, List paras)
			throws SQLException {
		String sql = "delete from T_INSTANCE where flowid =?";

		return sysdao.updateSQL(conn, sql, paras);
	}

	public boolean deleteInstanceDepFlowTask(Connection conn, List paras)
			throws SQLException {
		String sql = "delete from T_INSTANCE_DEPTASK where flowid =?";

		return sysdao.updateSQL(conn, sql, paras);
	}

	public boolean deleteInstanceFlow(Connection conn, List paras)
			throws SQLException {
		String sql = "delete from T_INSTANCE_FLOW where  flowid =? and execyctime =?";

		return sysdao.updateSQL(conn, sql, paras);
	}

}

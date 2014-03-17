package com.service;

import java.sql.Connection;
import java.sql.Timestamp;

import org.apache.log4j.Logger;

import com.bean.*;
import com.common.dbmanage.DBManage;
import com.dao.FlowDao;
import com.dao.InstanceFlowDao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ManageInstanceService {
	static Logger logger = Logger.getLogger(ManageInstanceService.class);
	InstanceFlowDao fdao = new InstanceFlowDao();

	public List<ManageFlowBean> getPreInstanceFlow() throws SQLException {
		Connection conn = DBManage.getConnection();
		List<ManageFlowBean> flows = null;
		if (conn != null) {
			try {
				flows = fdao.queryValidFlow(conn);
			} catch (SQLException e) {
				throw e;
			} finally {
				DBManage.close(conn);
			}
		}
		return flows;
	}

	// 增加实例流程任务的初始化信息
	public boolean addInstanceFlowTask(Long flowid, Timestamp nexecyctime,
			int failedRetryCnt) throws SQLException {
		Connection conn = DBManage.getConnection();
		List paras = new ArrayList();
		paras.add(nexecyctime);
		paras.add(failedRetryCnt);
		paras.add(flowid);
		if (conn != null) {
			try {
				if (fdao.addInstanceFlowTask(conn, paras) == true) {
					DBManage.commit(conn);
					return true;
				}
			} catch (SQLException e) {
				DBManage.rollback(conn);
				throw e;
			} finally {
				DBManage.close(conn);
			}
		}

		return false;
	}

	// 增加流程任务间依赖
	public boolean addInstanceFlowDepTask(Long flowid, Timestamp depexecyctime)
			throws SQLException {
		Connection conn = DBManage.getConnection();
		List paras = new ArrayList();
		paras.add(depexecyctime);
		paras.add(flowid);
		if (conn != null) {
			try {
				if (fdao.addInstanceFlowDepTask(conn, paras) == true) {
					DBManage.commit(conn);
					return true;
				}
			} catch (SQLException e) {
				DBManage.rollback(conn);
				throw e;
			} finally {
				DBManage.close(conn);
			}
		}

		return false;
	}

	// 增加流程间依赖
	public boolean addInstanceFlowDepFlow(List<DepFlowDescBean> deplist)
			throws SQLException {
		Connection conn = DBManage.getConnection();
		if (conn == null) {
			logger.error(" connection is null !");
			return false;
		}
		try {
			for (DepFlowDescBean depbean : deplist) {
				/* 如果流程间依赖关系中，返回的依赖流程执行时间为null，则实际上认为不存在依赖（可能配置错误、时间设置有误等） */
				if (depbean.getDeptime() == null) {
					continue;
				}
				List paras = new ArrayList();
				paras.add(depbean.getFlowid() * 1000);
				paras.add(depbean.getFlowid());
				paras.add(depbean.getDepflowid() * 1000 + 999);
				paras.add(depbean.getDepflowid());
				paras.add(depbean.getDeptime());
				paras.add(2);
				if (!fdao.addInstanceDepTask(conn, paras)) {
					logger.error(" addInstanceDepTask(flowid:"
							+ depbean.getFlowid() + ","
							+ depbean.getDepflowid() + ") return false !");
					return false;
				}
			}
			DBManage.commit(conn);
		} catch (SQLException e) {
			DBManage.rollback(conn);
			throw e;
		} finally {
			DBManage.close(conn);
		}
		return true;
	}

	public boolean preInstanceFlow(Long flowid, Timestamp nexecyctime)
			throws SQLException {
		Connection conn = DBManage.getConnection();
		List paras = new ArrayList();
		paras.add(flowid);
		paras.add(nexecyctime);
		paras.add(5);
		if (conn != null) {
			try {
				if (fdao.preInstanceFlow(conn, paras) == true) {
					DBManage.commit(conn);
					return true;
				}
			} catch (SQLException e) {
				DBManage.rollback(conn);
				throw e;
			} finally {
				DBManage.close(conn);
			}
		}

		return false;
	}

	public boolean initInstanceFlowStatus(Long flowid, Timestamp nexecyctime)
			throws SQLException {
		Connection conn = DBManage.getConnection();
		List paras = new ArrayList();
		paras.add(1);
		paras.add(flowid);
		paras.add(nexecyctime);

		if (conn != null) {
			try {
				if (fdao.updateInstanceFlowStatus(conn, paras) == true) {
					DBManage.commit(conn);
					return true;
				}
			} catch (SQLException e) {
				DBManage.rollback(conn);
				throw e;
			} finally {
				DBManage.close(conn);
			}
		}
		return false;
	}

	public boolean deleteInstanceFlowTask(Long flowid) throws SQLException {
		Connection conn = DBManage.getConnection();
		List paras = new ArrayList();
		paras.add(flowid);
		if (conn != null) {
			try {
				if (fdao.deleteInstanceFlowTask(conn, paras)) {
					DBManage.commit(conn);
					return true;
				}
			} catch (SQLException e) {
				DBManage.rollback(conn);
				throw e;
			} finally {
				DBManage.close(conn);
			}
		}
		return false;
	}

	public boolean deleteInstanceDepFlowTask(Long flowid) throws SQLException {
		Connection conn = DBManage.getConnection();
		List paras = new ArrayList();
		paras.add(flowid);
		if (conn != null) {
			try {
				if (fdao.deleteInstanceDepFlowTask(conn, paras)) {
					DBManage.commit(conn);
					return true;
				}
			} catch (SQLException e) {
				DBManage.rollback(conn);
				throw e;
			} finally {
				DBManage.close(conn);
			}
		}
		return false;
	}

	public boolean deleteInstanceFlow(Long flowid, Timestamp nexecyctime)
			throws SQLException {
		Connection conn = DBManage.getConnection();
		List paras = new ArrayList();
		paras.add(flowid);
		paras.add(nexecyctime);
		if (conn != null) {
			try {
				if (fdao.deleteInstanceFlow(conn, paras)) {
					DBManage.commit(conn);
					return true;
				}
			} catch (SQLException e) {
				DBManage.rollback(conn);
				throw e;
			} finally {
				DBManage.close(conn);
			}
		}
		return false;
	}

	public boolean deleteInstance(Long flowid) throws SQLException {
		Connection conn = DBManage.getConnection();
		List paras = new ArrayList();
		paras.add(flowid);
		if (conn != null) {
			try {
				if (!fdao.deleteInstanceFlowTask(conn, paras)) {
					DBManage.rollback(conn);
					return false;
				}
				if (!fdao.deleteInstanceDepFlowTask(conn, paras)) {
					DBManage.rollback(conn);
					return false;
				}
				DBManage.commit(conn);
				return true;
			} catch (SQLException e) {
				DBManage.rollback(conn);
				throw e;
			} finally {
				DBManage.close(conn);
			}
		}
		return false;
	}

	public boolean makeInstanceFlow(ManageFlowBean mfbean,
			Timestamp nexecyctime, List<DepFlowDescBean> deplist)
			throws SQLException {
		Connection conn = DBManage.getConnection();
		if (conn != null) {
			try {
				logger.info("=================makeInstanceFlow =======================");
				/*3.2.3.1  删除已经存在的流程实例信息*/
				/*3.2.3.1.1  删除流程任务实例化信息 */
				List paras = new ArrayList();
				paras.add(mfbean.getFflowid());
				if (fdao.deleteInstanceFlowTask(conn, paras) == false) {
					logger.error("deleteInstanceFlowTask failed!");
					DBManage.rollback(conn);
					return false;
				}
				/*3.2.3.1.2  删除流程任务(流程内、流程间)实例依赖关系信息 */
				if (fdao.deleteInstanceDepFlowTask(conn, paras) == false) {
					logger.error("deleteInstanceDepFlowTask failed!");
					DBManage.rollback(conn);
					return false;
				}
 
				/*3.2.3.2  增加实例化流程库初始状态记录，状态为待执行  */
				List paras1 = new ArrayList();
				paras1.add(mfbean.getFflowid());
				paras1.add(nexecyctime);
				paras1.add(1);
				if (fdao.preInstanceFlow(conn, paras1) == false) {
					logger.error("preInstanceFlow failed!");
					DBManage.rollback(conn);
					return false;
				}

				/*3.2.4  实例化流程任务库  */
				List paras2 = new ArrayList();
				paras2.add(nexecyctime);
				paras2.add(mfbean.getFailedRetryCnt());
				paras2.add(mfbean.getFflowid());
				if (fdao.addInstanceFlowTask(conn, paras2) == false) {
					logger.error("addInstanceFlowTask failed!");
					DBManage.rollback(conn);
					return false;
				}

				/*3.2.5  实例化流程内任务依赖库  */
				List paras3 = new ArrayList();
				paras3.add(nexecyctime);
				paras3.add(mfbean.getFflowid());
				if (fdao.addInstanceFlowDepTask(conn, paras3) == false) {
					logger.error("addInstanceFlowDepTask failed!");
					DBManage.rollback(conn);
					return false;
				}
                
				
				/*3.2.6  实例化流程间依赖库  */
				for (DepFlowDescBean depbean : deplist) {
					/* 如果流程间依赖关系中，返回的依赖流程执行时间为null，则实际上认为不存在依赖（可能配置错误、时间设置有误等） */
					if (depbean.getDeptime() == null) {
						continue;
					}
					List paras4 = new ArrayList();
					paras4.add(depbean.getFlowid() * 1000);
					paras4.add(depbean.getFlowid());
					paras4.add(depbean.getDepflowid() * 1000 + 999);
					paras4.add(depbean.getDepflowid());
					paras4.add(depbean.getDeptime());
					paras4.add(2);
					if (fdao.addInstanceDepTask(conn, paras4)==false) {
						logger.error(" addInstanceDepTask(flowid:"
								+ depbean.getFlowid() + ","
								+ depbean.getDepflowid() + ") return false !");
						DBManage.rollback(conn);
						return false;
					}
				}
				
				/*3.2.7 更新流程库配置库的执行账期时间   */ 
				List paras5 = new ArrayList();
				paras5.add(nexecyctime);
				paras5.add(mfbean.getFflowid());
				FlowDao fdao = new FlowDao();
				if(fdao.updateFlowExetime(conn, paras5) == false){
					logger.error("updateFlowExetime failed!");
							DBManage.rollback(conn);
							return false;
				}
				
				DBManage.commit(conn);
				return true;
			} catch (SQLException e) {
				DBManage.rollback(conn);
				throw e;
			} finally {
				DBManage.close(conn);
			}
		}
		return true;
	}
}

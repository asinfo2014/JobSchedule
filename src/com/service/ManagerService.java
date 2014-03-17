package com.service;

import java.sql.Connection;
import java.util.List;

import org.apache.log4j.Logger;

import com.actions.MonitorAction;
import com.bean.FlowBean;
import com.common.ServiceException;
import com.common.dbmanage.DBManage;
import com.dao.DepFlowDao;
import com.dao.FlowDao;
import com.dao.InstanceFlowDao;
import com.instance.manager.FlowManage;
import com.taskserver.ResultAnalyze;

public class ManagerService {

	/**
	 * 激活流程(被关闭流程)
	 * 
	 * @param flowid
	 *            流程号
	 * @return
	 */
	static Logger logger = Logger.getLogger(MonitorAction.class);

	public boolean activeFlow(long flowid, int flag) throws ServiceException {
		DepFlowDao depFlowDao = new DepFlowDao();
		// 数据库查询出流程对象
		FlowDao flowdao = new FlowDao();

		Connection conn = null;
		try {
			if (flag != 0 && flag != 1) {
				throw new ServiceException("操作标记flag必须为0或1");
			}

			conn = DBManage.getConnection();

			/*
			 * // 获取流程，且流程的状态为不可用，且流程的实例为挂起状态 FlowBean fbean =
			 * flowdao.getFlowByFlowid(conn, flowid); if (fbean == null ||
			 * fbean.getFlowid() <= 0 || fbean.getFlowstatus() != 2) { throw new
			 * ServiceException("流程状态可用，不能激活流程"); }
			 */
			// 查询流程依赖的流程实例状态为成功状态
			List<FlowBean> dflist = depFlowDao.getDepFlow(conn, flowid);
			for (FlowBean dfbean : dflist) {
				if (dfbean == null || dfbean.getFlowstatus() == 2) {
					throw new ServiceException("存在依赖的流程状态为不可用");
				}
			}
			// 更新t_flow流程为可用状态
			if (flowdao.updateFlowStatus(conn, flowid, 1) == false) {
				throw new ServiceException("更新流程状态为可用失败");
			}

			// 实例化执行该流程
			if (new FlowManage().reLaunchFlow(flowid, flag) == false) {
				throw new ServiceException("实例化执行该流程失败");
			}
			DBManage.commit(conn);
			return true;
		} catch (ServiceException e) {
			DBManage.rollback(conn);
			throw e;
		} finally {
			DBManage.close(conn);
		}
	}

	public boolean closeFlow(long flowid) throws ServiceException {

		DepFlowDao depFlowDao = new DepFlowDao();
		InstanceFlowDao ifdao = new InstanceFlowDao();
		// 数据库查询出流程对象
		FlowDao flowdao = new FlowDao();

		Connection conn = null;
		try {
			conn = DBManage.getConnection();

			// 1、 数据库查询出流程对象 判断当前流程状态是否为可用
			FlowBean fbean = flowdao.getFlowByFlowid(conn, flowid);
			if (fbean == null || fbean.getFlowid() <= 0) {
				throw new ServiceException("该流程不存在");
			}

			if (fbean.getFlowstatus() != 1) {
				throw new ServiceException("流程不可用不能关闭");
			}

			// 2、更新状态为不可用
			if (flowdao.updateFlowStatus(conn, flowid, 2) == false) {
				throw new ServiceException("更新流程状态为不可用失败");
			}

			// 3、调用(实例化(外-流程关闭))
			if (new FlowManage().closeFlow(flowid)  == false) {
				throw new ServiceException("实例化执行关闭流程失败");
			}
			DBManage.commit(conn);
			return true;
		} catch (ServiceException e) {
			DBManage.rollback(conn);
			throw e;
		} finally {
			DBManage.close(conn);
		}
	}

	// public static void main(String[] args) throws Exception {
	// System.out.println("activeFlow......");
	// ManagerService m = new ManagerService();
	// if (m.activeFlow(1010103)) {
	// System.out.println("activeFlow..is success....");
	// } else {
	// System.out.println("activeFlow..is failed....");
	// }
	// }
}

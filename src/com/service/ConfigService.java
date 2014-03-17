package com.service;

import java.sql.Connection;
import java.util.List;

import org.apache.log4j.Logger;

import com.bean.FlowBean;
import com.common.ServiceException;
import com.common.dbmanage.DBManage;
import com.dao.FlowDao;
import com.server.ManageInstanceIntf;

public class ConfigService {
	static Logger logger = Logger.getLogger(ConfigService.class);
	FlowDao dao = new FlowDao();

	// 获取所有流程
	public List<FlowBean> getAllFlow() throws ServiceException {
		Connection conn = null;
		List<FlowBean> list = null;
		try {
			conn = DBManage.getConnection();
			list = dao.getAllFlow(conn);
		} catch (ServiceException e) {
			throw e;
		} finally {
			DBManage.close(conn);
		}
		return list;
	}

	// 获取所有流程
	public FlowBean getFlowByFlowid(long flowid) throws ServiceException {
		Connection conn = null;
		FlowBean fbean = null;
		try {
			conn = DBManage.getConnection();
			fbean = dao.getFlowByFlowid(conn, flowid);
		} catch (ServiceException e) {
			throw e;
		} finally {
			DBManage.close(conn);
		}
		return fbean;
	}

	// 更新流程信息
	public Boolean editFlow(FlowBean flowBean)  throws ServiceException {
		Connection conn = null;
		try {
			conn = DBManage.getConnection();
            if (flowBean.getFlowstatus() == 1){
            	throw new ServiceException("流程可用不能修改");
            }
            
			if (dao.editFlow(conn,flowBean) == false) {
				throw new ServiceException("更新流程信息失败");
			}
			// 实例化管理(外-流程修改)
			ManageInstanceIntf modFlow = new ManageInstanceIntf();
			if(modFlow.ModFlowIntf(flowBean.getFlowid())!=0){
				
				throw new ServiceException("删除流程实例化信息失败");
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

	// 删除流程
	public boolean delete(long flowid) throws ServiceException {
		Connection conn = null;
		FlowBean fb = null;
		try {
			conn = DBManage.getConnection();
			fb = new FlowBean();
			fb = dao.getFlowByFlowid(conn, flowid);
			if (fb == null || fb.getFlowid() <= 0) {
				throw new ServiceException("获取流程失败");
			}
            if (fb.getFlowstatus() == 1){
            	throw new ServiceException("流程可用不能删除");
            }
			// 1、删除t_flow中流程信息
			if (dao.deleteFlow(conn, flowid) == false) {
				throw new ServiceException("删除流程信息表失败");
			}
			// 2、流程在表t_depflow中有记录同步删除
			if (dao.deleteDepFlow(conn, flowid) == false) {
				throw new ServiceException("删除依赖流程信息表失败");
			}
			// 3、删除流程ID任务依赖关系表在t_flow_deptask中所有任务信息
			if (dao.deleteFlowDepTask(conn, flowid) == false) {
				throw new ServiceException("删除流程内任务依赖关系信息表失败");
			}
			// 4、删除流程ID在t_flow_task中所有任务信息
			if (dao.deleteFlowTask(conn, flowid) == false) {
				throw new ServiceException("删除任务信息表失败");
			}
			
			// 实例化管理（外-删除流程）
			ManageInstanceIntf modFlow = new ManageInstanceIntf();
			if(modFlow.DelFlowIntf(flowid)==1){
				
				throw new ServiceException("删除流程实例化和执行信息表失败");
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

}

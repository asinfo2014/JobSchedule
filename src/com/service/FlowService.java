package com.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.bean.DepFlowDescBean;
import com.common.dbmanage.DBManage;
import com.dao.FlowDao;

public class FlowService {
	public FlowDao fdao = new FlowDao();
	public List<DepFlowDescBean> getDepFlowDesc(long flowid) throws SQLException {		
		Connection conn = DBManage.getConnection();
		List <DepFlowDescBean> depflows = null ;
		List paras =new ArrayList();
		paras.add(flowid); 
		if(conn != null){
			try{
				depflows = fdao.queryDepFlowDesc(conn, paras);	
			} catch (SQLException e) {
				throw e;
			} finally{
				DBManage.close(conn);
			}	
		}		
		return depflows;		 
	}
	
	public boolean updateFlowExetime(long flowid, Timestamp execyctime) throws SQLException {
		Connection conn = DBManage.getConnection();
		List paras =  new ArrayList();
		paras.add(execyctime);
		paras.add(flowid);
		if(conn != null){
			try {
				if(fdao.updateFlowExetime(conn, paras) == false){
					DBManage.rollback(conn);
					return false;
				}
				DBManage.commit(conn);
				return true;
			} catch (SQLException e) {
				DBManage.rollback(conn);
				throw e;
			} finally{
				DBManage.close(conn);
			}	
		}
		return false;
	}
}

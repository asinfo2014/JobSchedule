package com.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bean.DepFlowBean;
import com.bean.FlowBean;
import com.common.ServiceException;
import com.common.dbmanage.SysDAOTool;

public class DepFlowDao {

	SysDAOTool sysdao = new SysDAOTool();

	public List<FlowBean> getDepFlow(Connection conn, long flowid) throws ServiceException {
		String sql = "SELECT f.* FROM T_DEPFLOW df,T_FLOW f WHERE df.FLOWID = ? AND df.DEPFLOWID=f.FLOWID";
		List paras = new ArrayList();
		paras.add(flowid);
		List<FlowBean> dflist;
		try {
			dflist = sysdao.queryAll(conn, sql, paras,FlowBean.class);
		} catch (SQLException e) {
			throw new ServiceException(e);
		}
		return dflist;
	}

	public DepFlowBean getDepFlowBean(Connection conn, FlowBean bean)
			throws Exception {

		String sql = "SELECT * from T_DEPFLOW t where t.flowid = ?";

		List paras = new ArrayList();
		paras.add(bean.getFlowid());
		List list = sysdao.queryAll(conn, sql, paras, DepFlowBean.class);
		return new DepFlowBean();
	}

}

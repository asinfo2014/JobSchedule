package com.service;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;

import com.bean.FlowDirBean;
import com.bean.FlowMenuBean;
import com.bean.InstanceFlowBean;
import com.bean.InstanceTaskBean;
import com.common.ServiceException;
import com.common.dbmanage.DBManage;
import com.dao.InstanceFlowDao;

public class MonitorService {

	InstanceFlowDao ifdao = new InstanceFlowDao();

	// get flow instance
	public List<InstanceFlowBean> runningInstanceFlow(long flowDir)
			throws ServiceException {
		Connection conn = null;
		List<InstanceFlowBean> list = null;
		try {
			conn = DBManage.getConnection();
			if (flowDir != -1) {
				list = ifdao.getInstanceFlowByDir(conn, flowDir);
			} else {
				list = ifdao.getRunningInstanceFlow(conn);
			}
		} catch (ServiceException e) {
			throw e;
		} finally {
			DBManage.close(conn);
		}
		return list;
	}

	// get task instance
	public List<InstanceTaskBean> getInstanceTask(String flowid,
			long execyctime_l) throws ServiceException {
		Connection conn = null;
		List<InstanceTaskBean> list = null;
		try {
			conn = DBManage.getConnection();
			Timestamp ts = new Timestamp(execyctime_l);
			list = ifdao.getrunningInstanceTask(conn, flowid, ts);
		} catch (ServiceException e) {
			throw e;
		} finally {
			DBManage.close(conn);
		}
		return list;
	}

	/**
	 * 获取目录树的Json串
	 * 
	 * @return
	 */
	public String getMenuJson() {
		List<FlowMenuBean> fmlist1 = new ArrayList<FlowMenuBean>();
		Connection conn = null;
		try {
			conn = DBManage.getConnection();
			List<FlowDirBean> alldirs = ifdao.getAllFlowDir(conn);

			List<FlowDirBean> fdlist1 = this.getLevelDirs(alldirs, 1);
			for (FlowDirBean fadir : fdlist1) {
				FlowMenuBean fm1 = new FlowMenuBean();
				fm1.setId(fadir.getDirid());
				fm1.setText(fadir.getDirname());
				// fm1.setState("closed");
				List<FlowDirBean> fslist2 = this.getSubDirs(alldirs,
						fadir.getDirid());

				List<FlowMenuBean> fmlist2 = new ArrayList();
				for (FlowDirBean fd2 : fslist2) {
					FlowMenuBean fm2 = new FlowMenuBean();
					fm2.setId(fd2.getDirid());
					fm2.setText(fd2.getDirname());
					fmlist2.add(fm2);
				}
				fm1.setChildren(fmlist2);
				fmlist1.add(fm1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManage.close(conn);
		}

		JSONArray json = JSONArray.fromObject(fmlist1);
		return json.toString();
	}

	/**
	 * 获取指定级别的目录
	 * 
	 * @param alllist
	 * @param level
	 * @return
	 */
	private List<FlowDirBean> getLevelDirs(List<FlowDirBean> alldirs, int level) {
		List<FlowDirBean> nlist = new ArrayList<FlowDirBean>();
		for (FlowDirBean fd : alldirs) {
			if (fd.getDirlevel() == level)
				nlist.add(fd);
		}
		return nlist;
	}

	/**
	 * 获取指定目录的子目录
	 * 
	 * @param alllist
	 * @param level
	 * @return
	 */
	private List<FlowDirBean> getSubDirs(List<FlowDirBean> alldirs, long dirid) {
		List<FlowDirBean> nlist = new ArrayList<FlowDirBean>();
		for (FlowDirBean fd : alldirs) {
			if (fd.getDepdirid() == dirid)
				nlist.add(fd);
		}
		return nlist;
	}

}

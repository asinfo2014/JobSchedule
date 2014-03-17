package com.actions;

import java.util.ArrayList;
import java.util.List;

import com.bean.FlowBean;
import com.common.ServiceAction;
import com.common.ServiceException;
import com.service.ConfigService;
import com.service.ManagerService;

/**
 * 执行实例的动态管理
 * 
 * @author wankun
 * 
 */
public class ManagerAction extends ServiceAction {

	List<FlowBean> flist = new ArrayList<FlowBean>();
	ManagerService service = new ManagerService();
	ConfigService configservice = new ConfigService();
	long flowid = -1;
	int flag = -1;

	public List<FlowBean> getFlist() {
		return flist;
	}

	public void setFlist(List<FlowBean> flist) {
		this.flist = flist;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public long getFlowid() {
		return flowid;
	}

	public void setFlowid(long flowid) {
		this.flowid = flowid;
	}

	public String list() {
		try {

			flist = configservice.getAllFlow();
		} catch (ServiceException e) {
			se=e;
			return ERROR;
		}
		return "list";
	}

	public String activeFlow() {
		try {
			boolean opflag = service.activeFlow(flowid, flag);
			if (opflag == false)
				return ERROR;
			else
				return SUCCESS;
		} catch (ServiceException e) {
			se=e;
			return ERROR;
		}
	}

	public String closeFlow() {
		try {
			boolean opflag = service.closeFlow(flowid);
			if (opflag == false)
				return ERROR;
			else
				return SUCCESS;
		} catch (ServiceException e) {
			se=e;
			return ERROR;
		}
	}

}
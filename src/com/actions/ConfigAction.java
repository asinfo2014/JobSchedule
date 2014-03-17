package com.actions;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.bean.FlowBean;
import com.common.ServiceAction;
import com.common.ServiceException;
import com.service.ConfigService;

/**
 * 管理配置库
 * 
 * @author wankun
 * 
 */
public class ConfigAction extends ServiceAction {

	static Logger logger = Logger.getLogger(ConfigAction.class);
	ConfigService service = new ConfigService();
	List<FlowBean> flist = new ArrayList<FlowBean>();
	FlowBean fbean;
	Long flowid;
	Boolean editFlag;

	public Long getFlowid() {
		return flowid;
	}

	public void setFlowid(Long flowid) {
		this.flowid = flowid;
	}

	public FlowBean getFbean() {
		return fbean;
	}

	public void setFbean(FlowBean fbean) {
		this.fbean = fbean;
	}

	public List<FlowBean> getFlist() {
		return flist;
	}

	public void setFlist(List<FlowBean> flist) {
		this.flist = flist;
	}

	public String list() {
		try {

			flist = service.getAllFlow();
		} catch (ServiceException e) {
			se=e;
			return ERROR;
		}
		return "list";
	}

	public String toEdit() {
		try {
			fbean = service.getFlowByFlowid(flowid);
		} catch (ServiceException e) {
			se=e;
			return ERROR;
		}
		return "toEdit";
	}

	public String edit() {
		// t_flow表中信息更新
		try {
			service.editFlow(fbean) ;
		} catch (ServiceException e) {
			se=e;
			return ERROR;
		}
		return "view";
	}

	public String delete() {
		try {
			service.delete(flowid);
		} catch (ServiceException e) {
			se=e;
			return ERROR;
		}
		return SUCCESS;

	}

	public String view() {
		try {
			fbean = service.getFlowByFlowid(flowid);
		} catch (ServiceException e) {
			se=e;
			return ERROR;
		}
		return "view";
	}
}

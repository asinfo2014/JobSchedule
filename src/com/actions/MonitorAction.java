package com.actions;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.bean.FlowBean;
import com.bean.FlowDirBean;
import com.bean.InstanceFlowBean;
import com.bean.InstanceTaskBean;
import com.common.ServiceAction;
import com.common.ServiceException;
import com.service.MonitorService;

/**
 * 监控流程
 * 
 * @author wankun
 * 
 */
public class MonitorAction extends ServiceAction {

	static Logger logger = Logger.getLogger(MonitorAction.class);
	MonitorService service = new MonitorService();

	private String errorMsg;
	private List<InstanceFlowBean> ifbeans;
	private FlowBean flowBean;
	private List<FlowDirBean> flowdirs;
	private List<InstanceTaskBean> itbeans;
	private String flowid;
	private long execyctime_l;

	public FlowBean getFlowBean() {
		return flowBean;
	}

	public void setFlowBean(FlowBean flowBean) {
		this.flowBean = flowBean;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public List<InstanceFlowBean> getIfbeans() {
		return ifbeans;
	}

	public void setIfbeans(List<InstanceFlowBean> ifbeans) {
		this.ifbeans = ifbeans;
	}

	public List<FlowDirBean> getFlowdirs() {
		return flowdirs;
	}

	public void setFlowdirs(List<FlowDirBean> flowdirs) {
		this.flowdirs = flowdirs;
	}

	public List<InstanceTaskBean> getItbeans() {
		return itbeans;
	}

	public void setItbeans(List<InstanceTaskBean> itbeans) {
		this.itbeans = itbeans;
	}

	public String getFlowid() {
		return flowid;
	}

	public void setFlowid(String flowid) {
		this.flowid = flowid;
	}

	public long getExecyctime_l() {
		return execyctime_l;
	}

	public void setExecyctime_l(long execyctime_l) {
		this.execyctime_l = execyctime_l;
	}

	public String index() {
		// 查询t_instance_flow显示正在执行的流程信息
		try {
			ifbeans = service.runningInstanceFlow(flowBean == null ? -1
					: flowBean.getDirid());
		} catch (ServiceException e) {
			return ERROR;
		}
		return "index";
	}

	public void flowdirs() throws IOException {
		HttpServletResponse response = ServletActionContext.getResponse();
		// 以下代码从JSON.java中拷过来的
		response.setContentType("text/json");
		PrintWriter out = response.getWriter();
		// 将要被返回到客户端的对象
		String jsontree = service.getMenuJson();
		out.println(jsontree);
		out.flush();
		out.close();
	}

	/**
	 * 根据知道的流程的task执行情况
	 * 
	 * @return
	 * @throws Exception
	 */
	public String instanceTask() {
		if (flowid != null || flowid.length() > 0) {
			// 查询t_instance_flow显示正在执行的流程信息
			try {
				itbeans = service.getInstanceTask(flowid, execyctime_l);
			} catch (ServiceException e) {
				return ERROR;
			}
		}
		return "instancetask";

	}
}
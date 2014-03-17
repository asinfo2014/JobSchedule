package com.server;

import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import com.instance.manager.FlowManage;
import com.taskserver.ResultAnalyze;

class FlowScan extends TimerTask {

	static Logger logger = Logger.getLogger(FlowScan.class);
	private ServletContext context = null;


	public FlowScan(ServletContext context) {
		super();
		this.context = context;
		flowlaunch = new FlowManage();
	}

	private FlowManage flowlaunch;

	private boolean readConfig() {
		return true;
	}

	public void run() {
		try {
			FlowTimer.scancount[0]++;
			logger.debug("【 " + FlowTimer.scancount[0]+ "】*****【flows execute】");
			flowlaunch.launchFlows();
		} catch (Exception e) {
			logger.error("flowlaunch执行失败");
			e.printStackTrace();
		}
	}
}

class InstanceScan extends TimerTask {

	static Logger logger = Logger.getLogger(InstanceScan.class);
	private ServletContext context = null;

	public InstanceScan(ServletContext context) {
		super();
		this.context = context;
		manageInstance = new ManageInstance();
	}

	private ManageInstance manageInstance;

	public void run() {
		try {
			FlowTimer.scancount[1]++;
			logger.debug(" 【" + FlowTimer.scancount[1] + "】【instance start execute ============】");
			manageInstance.MakeInstance();
		} catch (Exception e) {
			logger.error("manageInstance执行失败");
			e.printStackTrace();
		}
	}
}

class ResultAnalyzeScan extends TimerTask {

	static Logger logger = Logger.getLogger(InstanceScan.class);
	private ServletContext context = null;
	private ResultAnalyze resultAnalyze;

	public ResultAnalyzeScan(ServletContext context) {
		super();
		this.context = context;
		resultAnalyze = new ResultAnalyze();
	}

	public void run() {
		try {
			FlowTimer.scancount[2]++;
			logger.debug("【" +FlowTimer.scancount[2] + "】【%%%%%%%%result start execute %%%%%%%%%%%%%%%%%%】");
			resultAnalyze.resultScan();
		} catch (Exception e) {
			logger.error("resultAnalyze执行失败");
			e.printStackTrace();
		}
	}
}

public class FlowTimer implements ServletContextListener {
	private Timer timer = null;
	private Timer MangeInstimer = null;
	private Timer resultAnalyzeTimer = null;
	
	static int scancount[] = {0, 0, 0};

	public void contextInitialized(ServletContextEvent event) {
		MangeInstimer = new Timer(true);
		MangeInstimer.schedule(new FlowScan(event.getServletContext()), 0,
				5000);// 0表示任务无延迟，5*1000表示每隔5秒执行任务，60*60*1000表示一个小时。
		event.getServletContext().log("实例管理功能定时器已启动");

		timer = new Timer(true);
		timer.schedule(new InstanceScan(event.getServletContext()), 0, 5000);// 0表示任务无延迟，5*1000表示每隔5秒执行任务，60*60*1000表示一个小时。
		event.getServletContext().log("实例执行功能定时器已启动");

		resultAnalyzeTimer = new Timer(true);
		resultAnalyzeTimer.schedule(
				new ResultAnalyzeScan(event.getServletContext()), 0, 5000);// 0表示任务无延迟，5*1000表示每隔5秒执行任务，60*60*1000表示一个小时。
		event.getServletContext().log("多线程执行结果分析定时器已启动");

	}

	public void contextDestroyed(ServletContextEvent event) {
		MangeInstimer.cancel();
		event.getServletContext().log("实例管理功能定时器已关闭");
		timer.cancel();
		event.getServletContext().log("实例执行功能时器已关闭");
		resultAnalyzeTimer.cancel();
		event.getServletContext().log("多线程执行结果分析定时器已关闭");
	}
}

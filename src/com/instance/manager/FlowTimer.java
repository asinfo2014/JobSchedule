package com.instance.manager;

import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Logger;

class FlowScan extends TimerTask {
	static Logger logger = Logger.getLogger(FlowScan.class);
	private FlowManage flowlaunch;

	private boolean readConfig() {
		return true;
	}

	public FlowScan() {
		super();
		flowlaunch = new FlowManage();
	}

	public void run() {
		try {
			flowlaunch.launchFlows();
		} catch (Exception e) {
			logger.error("flowlaunch执行失败");
			e.printStackTrace();
		}
	}
}

public class FlowTimer {

	public static void main(String[] args) {

		FlowScan flowscan = new FlowScan();
		Timer timer = new Timer();
		timer.schedule(flowscan, 0, 10000);
		System.out.println("main end");
	}

}

package com.server;

//import com.instance.manager.FlowManage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

import com.service.ManageInstanceService;

public class ManageInstanceIntf {
	static Logger logger = Logger.getLogger(ManageInstanceIntf.class);
	ManageInstanceService miservice = new ManageInstanceService(); 
//	FlowManage flowMange = new FlowManage();
	public int ModFlowIntf(long flowid)
	{
		if(flowid<1 ){
			logger.error("the parameter[flowid:"+flowid+"] is invalid !");
			return 2;
		}
		/* 删除流程的实例化信息：1. t_instance 信息  2.t_instance_deptask 信息*/
		try {
			if(miservice.deleteInstance(flowid)){
				return 0;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return 1;		
		
	}
 
	public int DelFlowIntf(long flowid )
	{
		if(flowid<1 ){
			logger.error(" the parameter[flowid:"+flowid+"] is invalid !");
			return 2;
		}
		/* 删除流程的实例化信息：1. t_instance 信息  2.t_instance_deptask 信息*/
		try {
			if(!miservice.deleteInstance((Long)flowid)){
				return 1;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		/* 删除流程的执行信息：1. t_instance_flow 信息  2.  t_instance_task 信息*/
		/* 需要调用实例执行功能模块*/
		//if(!flowMange.DeleteInstance(flowid, execyctime)){
		//	return 1;
		//}
		return 0;	
		
	}
	 
	public static void main(String[] args)
	{
		try
		{
			String url="jdbc:mysql://192.168.197.128/mydb";
			String user="mydb";
			String pwd="mydb";
			  
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection conn = DriverManager.getConnection(url,user, pwd);
			  
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select * from student");
			 
			while (rs.next())
			{
				String name = rs.getString("name");
				logger.debug(name);
			}
			rs.close();
			conn.close();
		}
		catch (Exception ex)
		{
			logger.error("Error : " + ex.toString());
		}
	}
}

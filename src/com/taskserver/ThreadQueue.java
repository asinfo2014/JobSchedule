package com.taskserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.util.concurrent.Callable;

import org.apache.log4j.Logger;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

import com.bean.InstanceTaskBean;
import com.instance.status.Status;

public class ThreadQueue implements Callable<InstanceTaskBean>{
	static Logger logger = Logger.getLogger(ThreadQueue.class);
	static long sshcount = 0;
	private TaskSSHInfo taskssh;
	private long threadid;
	
	public ThreadQueue(TaskSSHInfo taskssh) {
		this.taskssh = taskssh;
	}

	public InstanceTaskBean call() {
		
		//ssh 建立连接以及鉴权
		boolean isAuthSuccess = false;
		Connection sshconn = new Connection(taskssh.getHostname());
		try {
			sshcount++;
			synchronized (sshconn) {
				sshconn.connect(); 
			}
			logger.debug("【sshconnect】 : " + sshcount);
			isAuthSuccess = sshconn.authenticateWithPassword(taskssh.getUsername(), taskssh.getPasswd());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			sshconn.close();
		}	
		if(true != isAuthSuccess)
		{
			sshconn.close();
			return null;
		}
		
		//建立会话执行cmd
		Session sess = null;
		try{
			sess = sshconn.openSession();
			sess.execCommand(taskssh.getTotalcmd());
			
			//设置task结束时间
			taskssh.getTaskinfo().setEndtime(new Timestamp(System.currentTimeMillis()));
			
			//设置ssh的执行日志
			String log = getLog(sess);
			taskssh.getTaskinfo().setLog(log);
			String successflag ;
			if(log.trim().isEmpty())
			{
				successflag = "1";
			}
			else
			{
					successflag = log.trim().substring(log.trim().length()-1);
			}
			logger.debug("【successflag】: " + successflag);
			//设置task执行结果
			if(successflag.equals("0"))
			{
				taskssh.getTaskinfo().setStatus(Status.SUCCESS);
			}
			else
			{
				taskssh.getTaskinfo().setStatus(Status.FAILER);
			}
		}catch(Exception e)
		{
			e.printStackTrace();
			
		}finally
		{
			sess.close();
			sshconn.close();
		}
		
		return taskssh.getTaskinfo();
	}
	
	private String getString(InstanceTaskBean taskinfo)
	{
		StringBuffer strtask = new StringBuffer();
		return strtask.toString();
	}
	
	public InstanceTaskBean getObject(String strtask)
	{
		InstanceTaskBean taskinfo = new InstanceTaskBean();
		return taskinfo;
	}
	
	private String getLog(Session sess)
	{
		StringBuffer log = new StringBuffer();
		InputStream stdout = new StreamGobbler(sess.getStdout());
		InputStream stderr = new StreamGobbler(sess.getStderr());
		BufferedReader brstd = new BufferedReader(new InputStreamReader(stdout));
		BufferedReader brerr = new BufferedReader(new InputStreamReader(stderr));
		String line = "-1";
		try {
			while (true)
			{
				line = brstd.readLine();
				
				if (null == line)
				{
					break;
				}

				log.append("\n" + line);
			}
			brstd.close();
			
			//获取标准错误
			while(true)
			{
				line = brerr.readLine();
				if(null == line)
				{
					break;
				}
				log.append("\t" + line);
			}
			brerr.close();
			logger.debug("execute log: " + log );
			taskssh.getTaskinfo().setLog(log.toString());
		} catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//替换字符串中的双引号
		return log.toString().replaceAll("\"", "\\\\\"");
	}
	
	
	

}

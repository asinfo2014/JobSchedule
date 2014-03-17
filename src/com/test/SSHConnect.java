package com.test;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.Callable;

import com.bean.InstanceTaskBean;

import ch.ethz.ssh2.ChannelCondition;
import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

public class SSHConnect implements Callable<InstanceTaskBean> {
	
	private static final int TIME_OUT = 1000 * 5 * 60;
	public Connection conn;
	private Session sess = null;
	private String cmd;

public String getCmd() {
		return cmd;
	}
	public void setCmd(String cmd) {
		this.cmd = cmd;
	}
public SSHConnect() {
		super();
	}
	public boolean login(String host, String username, String password)
	{
		conn = new Connection(host);
		boolean isAuthSuccess = false;
		try {
			conn.connect();
			isAuthSuccess = conn.authenticateWithPassword(username , password);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return isAuthSuccess;
		
		
	}
	public void execute(String cmd)
	{
		int ret = -1;
		long begintime = System.currentTimeMillis();
		try {
			sess = conn.openSession();
			
			//屏蔽执行过程，只算连接时间
			/*
			sess.execCommand(cmd);
			getLog(sess);
			ret = sess.getExitStatus();
			System.out.println("result:" + ret);
			*/
			
			//sess.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long endtime = System.currentTimeMillis();
		
		System.out.println("sshtotaltime: " + (endtime - begintime ));
	}


	public void getLog(Session sess)
	{
		StringBuffer log = new StringBuffer();
		InputStream stdout = new StreamGobbler(sess.getStdout());
		InputStream stderr = new StreamGobbler(sess.getStderr());
		sess.waitForCondition(ChannelCondition.EXIT_STATUS, TIME_OUT);
		BufferedReader brstd = new BufferedReader(new InputStreamReader(stdout));
		BufferedReader brerr = new BufferedReader(new InputStreamReader(stderr));
		String line;
		log.append("log-stdout: ");
		try {
			//获取标准输出
			while (true)
			{
				line = brstd.readLine();
				
				if (null == line)
				{
					break;
				}
				log.append("\t" + line);
			}
			brstd.close();
			log.append(" stderr:" );
			
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
			System.out.println(log);
		} catch (IOException e) {
			System.out.println("getlog failed");
			e.printStackTrace();
		}
	}
	
	@Override
	public InstanceTaskBean call() throws Exception {
		String hostname = "192.168.250.183";
		String username = "root";
		String password = "its123";
		login(hostname, username, password);
		execute(cmd);
		return null;
	}
	
	public static void main(String []args)
	{
		//设置登陆信息
		String hostname = "132.228.55.43";
		String username = "etl";
		String password = "etl";
		
		SSHConnect sshconn = new SSHConnect();
		if(true != sshconn.login(hostname, username, password))
		{
			System.out.println("connect host failed");
			System.exit(0);
		}

		String cmd = "";
		sshconn.execute(cmd);
		System.out.println("ssh end");
		try {
			Thread.currentThread().sleep(40000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sshconn.conn.close();
	}

		
}




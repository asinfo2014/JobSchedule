package com.test;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import ch.ethz.ssh2.ChannelCondition;
import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

public class SSHTest {
	
	private static final int TIME_OUT = 1000 * 5 * 60;
	private Connection conn;
	private String host;
	private String username;
	private String password;
	private Session sess = null;

public SSHTest(String host, String username, String password) {
		super();
		this.host = host;
		this.username = username;
		this.password = password;
	}
	public boolean login()
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
			sess.execCommand(cmd);
			getLog(sess);
			ret = sess.getExitStatus();
			System.out.println("result:" + ret);
			sess.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long endtime = System.currentTimeMillis();
		
		System.out.println("totaltime: " + (endtime - begintime ));
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
	
	
	public static void main(String []args)
	{
		String hostname = "132.228.55.43";
		String username = "etl";
		String password = "etl";
		SSHTest sshtest = new SSHTest(hostname,username, password);
		
		if(true != sshtest.login())
		{
			System.exit(0);
		}
		String etlexport = ". /home/etl/.bash_profile; etl_export -v 0 -u 0 -t 20130812010003 -x CZ_SPC_AREA ";
		String etlload = ". /home/etl/.bash_profile; etl_load -v 0 -u 0 -t 20130812010003 -x CZ_SPC_AREA ";
		String etldss = ". /home/etl/.bash_profile; /home/etl/ailk/bin/etl_dss -v 0 -u 0 -t 20130812   -s TC_RES_PHY -x 33 -o 0";
		
		String cmd = etlexport;
		sshtest.execute(cmd);
		System.out.println("ssh end");
		try {
			Thread.currentThread().sleep(40000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sshtest.conn.close();
	}
		
}




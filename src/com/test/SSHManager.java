package com.test;

import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.bean.InstanceTaskBean;

public class SSHManager {
	public static int count = 0;
	public static ExecutorService exec = Executors.newCachedThreadPool();  
    public static Vector<Future<InstanceTaskBean>> results = new Vector<Future<InstanceTaskBean>>();

    
    public void execute()
    {
    	Long begintime = System.currentTimeMillis();
    	
    	System.out.println("begintime: " + begintime); 
    	results.add(exec.submit(new SSHConnect()));
    	System.out.println("endtime: " + System.currentTimeMillis()); 
    	
    	System.out.println("totaltime: " + System.currentTimeMillis());
    	
    	System.out.println("count:" + (count++));
    }
    
    public static void main(String []args)
    {
    	SSHManager sshmng = new SSHManager();
    	for(int i = 0; i < 500; i++)
    	{
    		try {
				Thread.currentThread().sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		sshmng.execute();
    	}
    }
}

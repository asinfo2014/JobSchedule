package com.server;
 
 
import java.sql.SQLException;
import java.sql.Timestamp;




import org.apache.log4j.Logger;

import com.bean.DepFlowDescBean;
import com.bean.ManageFlowBean;
import com.common.Tools;
import com.service.FlowService;
import com.service.ManageInstanceService;

import java.util.Calendar;
import java.util.List;
 
public class ManageInstance {
	static Logger logger = Logger.getLogger(ManageInstance.class);
	ManageInstanceService miservice = new ManageInstanceService();
	FlowService fservice = new FlowService();
	
	private Boolean instancerunning = false;
	public static long millisecondtosecond = 1000;
	public static long millisecondtominite = 60*millisecondtosecond;
	public static long millisecondtohour = 60*millisecondtominite;
	public static long millisecondtoday = 24*60*millisecondtominite;
	
	 
	public int MakeInstance() throws SQLException
	{
		synchronized(instancerunning){	
			Timestamp  nexecyctime = null ;
			Timestamp  execyctime = null ;
			Timestamp  currenttime = new Timestamp(System.currentTimeMillis()); ;
			/*1. 确保当前只有一个线程在做实例化 */
			if(!instancerunning){
				instancerunning = true;				
				try {
					/*2. 获取流程表中符合实例化条件的流程信息集合 */
					List<ManageFlowBean> list = miservice.getPreInstanceFlow();
					if(list != null){ 
						logger.info("符合实例化条件的流程：" + Tools.printList(list, ManageFlowBean.class));
						for(ManageFlowBean mfbean : list){
							/*3. 处理一次流程实例化程序*/
							//mfbean.printString();
							/*3.1 如果如果上一次执行周期execyctime在实例化流程库中没有记录(tflowid 是 -1(null))，表明上次执行周期没有执行，实例化从上次执行周期开始 */
							execyctime = mfbean.getExecyctime() == null ? mfbean.getBegintime():mfbean.getExecyctime() ;
							if (mfbean.getIflowid() < 0)
							{
								nexecyctime = execyctime;								
							}
							else
							{
								/*3.1.1 如果有记录，且是 一次任务(异常或成功)，则该流程不需要执行 */
								if(mfbean.getTimekind() == 0){								 
									continue;
								}
								nexecyctime = getNextCyctime(mfbean.getTimekind(),mfbean.getTimeintv(),execyctime);	
							}
							/*3.2 对流程mfbean.flowid 的 nexecyctime账期进行实例化*/
							if(nexecyctime == null){
								continue;
							}
							int hour =(int) ((nexecyctime.getTime()- currenttime.getTime())/millisecondtohour);
							/*3.2.1 如果下一个执行周期不在当前时间之后的1个小时以内，则不执行 */
							if(hour >= 1 )
							{
								continue;
							}							
							/*3.2.2  获取流程间依赖的关系  */
							List<DepFlowDescBean> deplist = fservice.getDepFlowDesc(mfbean.getFflowid());
							logger.info("\nflowid: " + mfbean.getFflowid() + " " + Tools.printList(deplist, DepFlowDescBean.class));
							for(DepFlowDescBean depbean:deplist){
								/*3.2.2.1  计算距离当前实例化的流程时间nexecyctime 最近的上一次依赖流程执行时间  */
								Timestamp deptime =  getLastestTime(nexecyctime,depbean.getDepbegintime(),depbean.getDeptimekind(),depbean.getDeptimeintv());
								depbean.setDeptime(deptime);
							}
							
							/*3.2.3   增加实例化流程库初始状态记录，状态为正在实例化  */
							if(miservice.makeInstanceFlow(mfbean,nexecyctime, deplist)==false){
								logger.error("实例化失败");
								continue;
							}
//							boolean flag = true;
//							do{
//								/*3.2.3   增加实例化流程库初始状态记录，状态为正在实例化  */
//
//								/*3.2.3.1  删除已经存在的流程实例信息*/
//								/*3.2.3.1.1  删除流程任务实例化信息 */
//								if(!miservice.deleteInstanceFlowTask(mfbean.getFflowid())){
//									 System.out.println("[ERROR] rollback Instancing flow(task) failed");
//									 flag =  false;
//									 break;
//								}
//								/*3.2.3.1.2  删除流程任务(流程内、流程间)实例依赖关系信息 */
//								if(!miservice.deleteInstanceDepFlowTask(mfbean.getFflowid())){
//									 System.out.println("[ERROR] rollback Instancing flow(deptask) failed");
//									 flag = false;	
//									 break;
//								}
//								/*3.2.3.2  增加实例化流程库初始状态记录，状态为正在实例化  */
//								if(!miservice.preInstanceFlow(mfbean.getFflowid(),nexecyctime)){
//									flag = false;
//									break;
//								}
//								/*3.2.4  实例化流程任务库  */
//								if(!miservice.addInstanceFlowTask(mfbean.getFflowid(),nexecyctime,mfbean.getFailedRetryCnt())){
//									flag = false;
//									break;
//								}
//								/*3.2.5  实例化流程内任务依赖库  */
//								if(!miservice.addInstanceFlowDepTask(mfbean.getFflowid(),nexecyctime)){
//									flag = false;
//									break;
//								}								
//								/*3.2.6  实例化流程间依赖库  */
//								if(!miservice.addInstanceFlowDepFlow(deplist)){
//									flag = false;
//									break;
//								}
//								/*3.2.7 更新流程库配置库的执行账期时间   */ 
//								if(!fservice.updateFlowExetime(mfbean.getFflowid(),nexecyctime)){
//									flag = false;
//									break;
//								}
//								/*3.2.8 更新实例化流程库初始状态记录，正在执行  */ 		
//								if(!miservice.initInstanceFlowStatus(mfbean.getFflowid(),nexecyctime)){
//									flag = false;
//									break;
//								}
								
//							}while(false);			
							 
//							/*4. 异常情况下流程实例化回滚 */
//							if(!flag) {
//								logger.error(" instancing flow(flowid:"+mfbean.getFflowid()+") failed !");
//								if(!rollBackInstanceFlow(mfbean,nexecyctime)){
//									logger.error("rollback failed instancing flow(flowid:"+mfbean.getFflowid()+") failed !");
//								}
//							}
						}				 
					}		  
				} catch (SQLException e) {
					// TODO Auto-generated catch block	
					throw e;
//					e.printStackTrace();					 
				}			 
					
			}
			instancerunning = false;	
			return 0 ;
		}
		 
	}
	private boolean rollBackInstanceFlow(ManageFlowBean mfbean,Timestamp nexecyctime){
 		
		try {
			/*1. 将流程配置时间更新回上次执行时间 */
			if(!fservice.updateFlowExetime(mfbean.getFflowid(),mfbean.getExecyctime())){
				 logger.error(" rollback Instancing flow(updateExetime) failed");
				 return false;
			}
			/*2. 删除流程任务实例化信息 */
			if(!miservice.deleteInstanceFlowTask(mfbean.getFflowid())){
				 logger.error(" rollback Instancing flow(task) failed");
				 return false;
			}
			/*3. 删除流程任务(流程内、流程间)实例依赖关系信息 */
			if(!miservice.deleteInstanceDepFlowTask(mfbean.getFflowid())){
				 logger.error(" rollback Instancing flow(deptask) failed");
				 return false;
			}
			/*4. 删除流程实例化信息 */
			if(!miservice.deleteInstanceFlow(mfbean.getFflowid(),nexecyctime)){
				 logger.error(" rollback Instancing flow(flow) failed");
				 return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 
		return true;
	}
	/*计算距离当前执行时间currenttime最近的执行时间的依赖时间*/
	private Timestamp getLastestTime(Timestamp currenttime,Timestamp begintime, int kind,int interval){
		Timestamp lasttime = null;
		if(currenttime.before(begintime)){
			lasttime = null;
			return lasttime;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(begintime);
		long offset = 0;
		long multiple = 0;
		switch (kind){
		case 0:
			lasttime =  begintime;
			break;
		case 1:
			multiple = (currenttime.getTime() - begintime.getTime())/(interval*millisecondtominite);
			offset =  multiple*interval*millisecondtominite;
			lasttime = new Timestamp(calendar.getTimeInMillis()+ offset);	
			break;
		case 2:
			multiple = (currenttime.getTime() - begintime.getTime())/(interval*millisecondtohour);
			offset =  multiple*interval*millisecondtohour;
			lasttime = new Timestamp(calendar.getTimeInMillis()+ offset);	
			break;
		case 3:
			multiple = (currenttime.getTime() - begintime.getTime())/(interval*millisecondtoday);
			offset =  multiple*interval*millisecondtoday;
			lasttime = new Timestamp(calendar.getTimeInMillis()+ offset);	
			break;
		case 4:
			/*两个时间相差的月份*/
			Calendar mCalendar = Calendar.getInstance();
			mCalendar.setTime(currenttime);

			int year = mCalendar.get(Calendar.YEAR) - calendar.get(Calendar.YEAR);
			int month = mCalendar.get(Calendar.MONTH) - calendar.get(Calendar.MONTH);
	 		
			offset = year*12+month; 
			 
			calendar.add(Calendar.MONTH, (int) offset); 
			//如果比当前时间大，则需要使用前一个月的执行周期作为依赖时间
			if(mCalendar.compareTo(calendar) > 0){ // mCalendar < calendar
				calendar.add(Calendar.MONTH, (int) -1);
			}
			lasttime = new Timestamp(calendar.getTimeInMillis());
			
			break;
		case 5:
			/*两个时间相差的年份*/
			Calendar yCalendar = Calendar.getInstance();
			yCalendar.setTime(currenttime);

			offset = yCalendar.get(Calendar.YEAR) - calendar.get(Calendar.YEAR);
			 			 
			calendar.add(Calendar.YEAR, (int) offset); 
			//如果比当前时间大，则需要使用前一个月的执行周期作为依赖时间
			if(yCalendar.compareTo(calendar) > 0){ // endCalendar < calendar
				calendar.add(Calendar.YEAR, (int) -1);
			}
			lasttime = new Timestamp(calendar.getTimeInMillis());
			
			break;
		default:
			logger.warn("kind is not mismatch !");
			lasttime = null;
			break;
		}
		
		return lasttime;
	}
	public Timestamp addTime( Timestamp time,int field ,int offset)
	{	  
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(time);
		switch(field){
		case Calendar.MINUTE :
			calendar.add(Calendar.MINUTE, offset);  
			break;
		case Calendar.HOUR :
			calendar.add(Calendar.HOUR, offset);  
			break;
		case Calendar.DATE:
			calendar.add(Calendar.DATE, offset);  
			break;
		case Calendar.MONTH:
			calendar.add(Calendar.MONTH, offset);  
			break;
		case Calendar.YEAR:
			calendar.add(Calendar.YEAR, offset);  
			break;
		default:
			logger.warn(" field is not mismatch !");
			break;
		}
 		Timestamp  destime= new Timestamp(calendar.getTimeInMillis());
		return destime; 	
		
	}
	
	Timestamp getNextCyctime(Integer kind,Integer intv , Timestamp execyctime){
		Timestamp  nexecyctime = null ;
		switch (kind){
		case 0:
			nexecyctime = execyctime;
			break;
		case 1:
			nexecyctime =addTime(execyctime, Calendar.MINUTE, intv) ;
			break;
		case 2:
			nexecyctime =addTime(execyctime, Calendar.HOUR, intv) ;
			break;
		case 3:
			nexecyctime =addTime(execyctime, Calendar.DATE, intv) ;
			break;
		case 4:
			nexecyctime =addTime(execyctime, Calendar.MONTH, intv) ;
			break;
		case 5:
			nexecyctime =addTime(execyctime, Calendar.YEAR, intv) ;
			break;
		default:
			logger.warn("kind is not mismatch !");
			nexecyctime = null;
			break;
		}
		
		return nexecyctime;
	}
	
}

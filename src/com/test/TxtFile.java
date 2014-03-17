package com.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class TxtFile {
	private static String path = "D:/";
	private static String filenameTemp;
	private static File file;
	private static OdsFileBean odsFile = new OdsFileBean();
    private static Random r=new Random();
	/**
	 * 创建文件
	 * 
	 * @throws IOException
	 */
	public static boolean creatTxtFile(String name) throws IOException {
		boolean flag = false;
		filenameTemp = path + name + ".txt";
		file = new File(filenameTemp);
		if (!file.exists()) {
			file.createNewFile();
			flag = true;
		}
		return flag;
	}

	public static boolean writeTxtFile2(OdsFileBean odsFile) throws IOException {
		// 先读取原有文件内容，然后进行写入操作
		boolean flag = false;
		String filein = odsFile.getSOURCE_TYPE() + ","
				+ odsFile.getRECORD_TYPE() + "," 
				+ odsFile.getNI_PDP() + ","
				+ odsFile.getMSISDN() + "," 
				+ odsFile.getIMSI_NUMBER() + ","
				+ odsFile.getSGSN() + "," 
				+ odsFile.getMSNC() + ","
				+ odsFile.getLAC() + "," 
				+ odsFile.getRA() + ","
				+ odsFile.getCELL_ID() + "," 
				+ odsFile.getCHARGING_ID() + ","
				+ odsFile.getGGSN() + "," 
				+ odsFile.getAPNNI() + ","
				+ odsFile.getAPNOI() + "," 
				+ odsFile.getPDP_TYPE() + ","
				+ odsFile.getSPA() + "," 
				+ odsFile.getSGSN_CHANGE() + ","
				+ odsFile.getCAUSE_CLOSE() + "," 
				+ odsFile.getRESULT() + ","
				+ odsFile.getHOME_AREA_CODE() + ","
				+ odsFile.getVISIT_AREA_CODE() + "," 
				+ odsFile.getUSER_TYPE()+ "," 
				+ odsFile.getFEE_TYPE() + "," 
				+ odsFile.getROAM_TYPE()+ "," 
				+ odsFile.getSERVICE_TYPE() + ","
				+ odsFile.getSTART_DATE() + "," 
				+ odsFile.getSTART_TIME() + ","
				+ odsFile.getCALL_DURATION() + "," 
				+ odsFile.getTARIFF1() + ","
				+ odsFile.getDATA_UP1() + "," 
				+ odsFile.getDATA_DOWN1() + ","
				+ odsFile.getDURATION1() + "," 
				+ odsFile.getTARIFF2() + ","
				+ odsFile.getDATA_UP2() + "," 
				+ odsFile.getDATA_DOWN2() + ","
				+ odsFile.getDURATION2() + "," 
				+ odsFile.getTARIFF3() + ","
				+ odsFile.getDATA_UP3() + "," 
				+ odsFile.getDATA_DOWN3() + ","
				+ odsFile.getDURATION3() + "," 
				+ odsFile.getTARIFF4() + ","
				+ odsFile.getDATA_UP4() + "," 
				+ odsFile.getDATA_DOWN4() + ","
				+ odsFile.getDURATION4() + "," 
				+ odsFile.getTARIFF5() + ","
				+ odsFile.getDATA_UP5() + "," 
				+ odsFile.getDATA_DOWN5() + ","
				+ odsFile.getDURATION5() + "," 
				+ odsFile.getTARIFF6() + ","
				+ odsFile.getDATA_UP6() + "," 
				+ odsFile.getDATA_DOWN6() + ","
				+ odsFile.getDURATION6() + "," 
				+ odsFile.getCFEE() + ","
				+ odsFile.getFEE1() + "," 
				+ odsFile.getFEE2() + ","
				+ odsFile.getFEE3() + "," 
				+ odsFile.getTOTAL_FEE() + ","
				+ odsFile.getDEAL_TIME() + "," 
				+ odsFile.getFILE_NO() + ","
				+ odsFile.getERROR_CODE() + "," 
				+ odsFile.getCUST_ID() + ","
				+ odsFile.getUSER_ID() + ","
				+ odsFile.getA_BRAND_ID() + ","
				+ odsFile.getPAY_MODE() + "," 
				+ odsFile.getA_SERV_TYPE() + ","
				+ odsFile.getCHAN_NO() + "," 
				+ odsFile.getOFFICE_CODE() + ","
				+ odsFile.getFREE_CODE() + "," 
				+ odsFile.getBILLING_CYCLE()+ "," 
				+ odsFile.getBILL_START_DAY() + ","
				+ odsFile.getREVISION() + "," 
				+ odsFile.getCDR_DAY() + ","
				+ odsFile.getFILE_PP_NAME() + "," 
				+ odsFile.getVISIT_COUNTY()+ "," 
				+ odsFile.getDEDUCT_REMARK() + ","
				+ odsFile.getDEDUCT_REMARK2() + "," 
				+ odsFile.getIMEI() + ","
				+ odsFile.getRECORDEXTENSION() + "," 
				+ odsFile.getBILLINFO()+ "," 
				+ odsFile.getRCDSEQNUM() + ","
				+ odsFile.getAPNNI_GROUPID() + "," 
				+ odsFile.getFREE_ITEM1()+ "," 
				+ odsFile.getFREE1() + "," 
				+ odsFile.getPRODUCT_ID1()+ "," 
				+ odsFile.getINSTANCE_ID1() + ","
				+ odsFile.getACCU_USER_ID1() + "," 
				+ odsFile.getFREE_ITEM2()+ "," 
				+ odsFile.getFREE2() + "," 
				+ odsFile.getPRODUCT_ID2()+ "," 
				+ odsFile.getINSTANCE_ID2() + ","
				+ odsFile.getACCU_USER_ID2() + "," 
				+ odsFile.getFREE_ITEM3()+ "," 
				+ odsFile.getFREE3() + "," 
				+ odsFile.getPRODUCT_ID3()+ "," 
				+ odsFile.getINSTANCE_ID3() + ","
				+ odsFile.getACCU_USER_ID3() + "," 
				+ odsFile.getFREE_ITEM4()+ "," 
				+ odsFile.getFREE4() + "," 
				+ odsFile.getPRODUCT_ID4()+ "," 
				+ odsFile.getINSTANCE_ID4() + ","
				+ odsFile.getACCU_USER_ID4() + "," 
				+ odsFile.getRATEMETHOD()+ "," 
				+ odsFile.getFREE_CODE1() + "," 
				+ odsFile.getFREE_FEE1()+ "," 
				+ odsFile.getRATE_ITEM_ID1() + ","
				+ odsFile.getFREE_CODE2() + "," 
				+ odsFile.getFREE_FEE2() + ","
				+ odsFile.getRATE_ITEM_ID2() + "," 
				+ odsFile.getFREE_CODE3()+ "," 
				+ odsFile.getFREE_FEE3() + ","
				+ odsFile.getRATE_ITEM_ID3() + "," 
				+ odsFile.getFREE_CODE4()+ "," 
				+ odsFile.getFREE_FEE4() + ","
				+ odsFile.getRATE_ITEM_ID4() + "," 
				+ odsFile.getBASE_TPREMARK()+ "," 
				+ odsFile.getTPREMARK() + ","
				+ odsFile.getRATE_FILE_NAME() + "," 
				+ odsFile.getRATE_TIME()+ "," 
				+ odsFile.getACC_FILE_NAME() + ","
				+ odsFile.getACC_TIME() + "," 
				+ odsFile.getDIS_FILE_NAME()+ "," 
				+ odsFile.getDIS_TIME() + "," 
				+ odsFile.getBUSI_DOMAIN()+ "," 
				+ odsFile.getBILL_END_DAY() + "\n";

		try {

			OutputStreamWriter write = new OutputStreamWriter(
					new FileOutputStream(file, true), "gbk");
			BufferedWriter writer = new BufferedWriter(write);
			writer.write(filein);
			writer.flush();
			writer.close();
			flag = true;
		} catch (IOException e1) {
			// TODO 自动生成 catch 块
			throw e1;
		}
		return flag;
	}
    
	private static Date randomDate(String beginDate, String endDate) {
	    try {
	        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	        Date start = format.parse(beginDate);// 开始日期
	        Date end = format.parse(endDate);// 结束日期
	        if (start.getTime() >= end.getTime()) {
	            return null;
	        }
	        long date = random(start.getTime(), end.getTime());
	        return new Date(date);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return null;
	}

	private static Date randomTime(String beginDate, String endDate) {
	    try {
	        SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss");
	        Date start = format.parse(beginDate);// 开始日期
	        Date end = format.parse(endDate);// 结束日期
	        if (start.getTime() >= end.getTime()) {
	            return null;
	        }
	        long date = random(start.getTime(), end.getTime());
	        return new Date(date);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return null;
	}

	private static long random(long begin, long end) {
	    long rtnn = begin + (long) (Math.random() * (end - begin));
	    if (rtnn == begin || rtnn == end) {
	        return random(begin, end);
	    }
	    return rtnn;
	  }

	
	public static void main(String[] args) throws Exception {
		System.out.println("activeFlow......");

		int j = 0;
		String partfilename = "gprs_"
				+ (new SimpleDateFormat("yyyyMMdd")).format(new Date()) + "_"
				+ String.format("%04d", j);
		
		creatTxtFile(partfilename);

		long filesize = new FileInputStream(file).available()/1024 ;// M
     
		for(j=0;j<6;j++)
		{
			while(true)
			{
			odsFile.setA_BRAND_ID((int) (Math.random() * 100));
			odsFile.setA_SERV_TYPE("13");
			odsFile.setACC_FILE_NAME(null);
			odsFile.setACC_TIME(null);
			odsFile.setACCU_USER_ID1(String.valueOf((long)(Math.random() *100000000) *  10000000));
			odsFile.setACCU_USER_ID2(String.valueOf((long)(Math.random() * 100000000) * 10000000));
			odsFile.setACCU_USER_ID3(String.valueOf((long)(Math.random() * 100000000 * 10000000)));
			odsFile.setACCU_USER_ID4(String.valueOf((long)(Math.random() * 100000000 * 10000000)));
			odsFile.setAPNNI("cmnet");
			odsFile.setAPNNI_GROUPID(String.valueOf((int)(Math.random() * 100)));
			odsFile.setAPNOI(null);
			odsFile.setBASE_TPREMARK("0|0000000000|2381:3144|;");
			odsFile.setBILL_END_DAY(20130531);// 账期结束时间 是否随机？
			odsFile.setBILL_START_DAY(20130501);
			odsFile.setBILLINFO(null);
			odsFile.setBILLING_CYCLE(String.valueOf(201305));
			odsFile.setBUSI_DOMAIN(null);
			odsFile.setCALL_DURATION((int) (Math.random() * 100000));
			odsFile.setCAUSE_CLOSE(String.valueOf((int)(Math.random() * 10)));// ////////////////////
			odsFile.setCDR_DAY(Integer.parseInt((new SimpleDateFormat("yyyyMMdd")).format(randomDate("2013-10-01", "2013-10-31"))));// 是否要随机
			odsFile.setCELL_ID(null);
			odsFile.setCFEE((long) ((int)(Math.random() * 1000000)));
			odsFile.setCHAN_NO("N001");// 是否需要随机
			odsFile.setCHARGING_ID(String.valueOf((long)(Math.random() * 100000000 * 10000000)));
			odsFile.setCUST_ID(null);
			odsFile.setDATA_DOWN1((long) ((int)(Math.random() * 1000000)));
			odsFile.setDATA_DOWN2((long) ((int)(Math.random() * 1000000)));
			odsFile.setDATA_DOWN3((long) ((int)(Math.random() * 1000000)));
			odsFile.setDATA_DOWN4((long) ((int)(Math.random() * 1000000)));
			odsFile.setDATA_DOWN5((long) ((int)(Math.random() * 1000000)));
			odsFile.setDATA_DOWN6((long) ((int)(Math.random() * 1000000)));
			odsFile.setDATA_UP1((long) ((int)(Math.random() * 1000000)));
			odsFile.setDATA_UP2((long) ((int)(Math.random() * 1000000)));
			odsFile.setDATA_UP3((long) ((int)(Math.random() * 1000000)));
			odsFile.setDATA_UP4((long) ((int)(Math.random() * 1000000)));
			odsFile.setDATA_UP5((long) ((int)(Math.random() * 1000000)));
			odsFile.setDATA_UP6((long) ((int)(Math.random() * 1000000)));
			odsFile.setDEAL_TIME(String.valueOf((int)(Math.random() * 100000000)));// 处理时间秒数
			odsFile.setDEDUCT_REMARK(null);
			odsFile.setDEDUCT_REMARK2("##");
			odsFile.setDIS_FILE_NAME("DGGP");
			odsFile.setDIS_TIME(null);
			odsFile.setDURATION1((int) (Math.random() * 1000000));
			odsFile.setDURATION2((int) (Math.random() * 1000000));
			odsFile.setDURATION3((int) (Math.random() * 1000000));
			odsFile.setDURATION4((int) (Math.random() * 1000000));
			odsFile.setDURATION5((int) (Math.random() * 1000000));
			odsFile.setDURATION6((int) (Math.random() * 1000000));
			odsFile.setERROR_CODE(String.valueOf((int)(Math.random() * 10000000)));
			odsFile.setFEE1((long) 0);
			odsFile.setFEE2(null);
			odsFile.setFEE3(null);
			odsFile.setFEE_TYPE(String.valueOf((int)Math.random() * 10));
			odsFile.setFILE_NO("0GGEA7105031355.3312");
			odsFile.setFILE_PP_NAME(null);
			odsFile.setFREE1((long) ((int)(Math.random() * 1000000)));
			odsFile.setFREE2((long) ((int)(Math.random() * 1000000)));
			odsFile.setFREE3((long) ((int)(Math.random() * 1000000)));
			odsFile.setFREE4((long) ((int)(Math.random() * 1000000)));
			odsFile.setFREE_CODE(String.valueOf((int)(Math.random() * 100000000)));
			odsFile.setFREE_CODE1(String.valueOf((int)(Math.random() * 100000000)));
			odsFile.setFREE_CODE2(String.valueOf((int)(Math.random() * 100000000)));
			odsFile.setFREE_CODE3(String.valueOf((int)(Math.random() * 100000000)));
			odsFile.setFREE_CODE4(String.valueOf((int)(Math.random() * 100000000)));
			odsFile.setFREE_FEE1(0);
			odsFile.setFREE_FEE2(0);
			odsFile.setFREE_FEE3(0);
			odsFile.setFREE_FEE4(0);
			odsFile.setFREE_ITEM1((int) (Math.random() * 10000));
			odsFile.setFREE_ITEM2((int) (Math.random() * 10000));
			odsFile.setFREE_ITEM3((int) (Math.random() * 10000));
			odsFile.setFREE_ITEM4((int) (Math.random() * 10000));
			odsFile.setGGSN("DDB18B82");
			odsFile.setHOME_AREA_CODE(String.valueOf((int)(Math.random() * 1000)));// 归属地
			odsFile.setIMEI(String.valueOf((long)(Math.random() * 100000000 * 100000000)));// IMEI
																					// 16位
			odsFile.setIMSI_NUMBER(String.valueOf((long)(Math.random() * 100000000 * 10000000)));// IMSI
																							// 15位
			odsFile.setINSTANCE_ID1(String.valueOf((long)(Math.random() * 100000000 * 100000)));
			odsFile.setINSTANCE_ID2(String.valueOf((long)(Math.random() * 100000000 * 1000000)));
			odsFile.setINSTANCE_ID3(String.valueOf((long)(Math.random() * 100000000 * 1000000)));
			odsFile.setINSTANCE_ID4(String.valueOf((long)(Math.random() * 100000000 * 1000000)));
			odsFile.setLAC(null);
			odsFile.setMSISDN(String.valueOf((long)(Math.random() * 100000000 * 1000)));// 手机号
			odsFile.setMSNC('2');
			odsFile.setNI_PDP('0');
			odsFile.setOFFICE_CODE(String.valueOf((int)(Math.random() * 10000)));// 归属地市
			odsFile.setPAY_MODE(null);//
			odsFile.setPDP_TYPE('1');
			odsFile.setPRODUCT_ID1(String.valueOf((long)(Math.random() * 100000000 * 1000000)));
			odsFile.setPRODUCT_ID2(String.valueOf((long)(Math.random() * 100000000 * 1000000)));
			odsFile.setPRODUCT_ID3(String.valueOf((long)(Math.random() * 100000000 * 1000000)));
			odsFile.setPRODUCT_ID4(String.valueOf((long)(Math.random() * 100000000 * 1000000)));
			odsFile.setRA(null);
			odsFile.setRATE_FILE_NAME(null);
			odsFile.setRATE_ITEM_ID1(String.valueOf(0));
			odsFile.setRATE_ITEM_ID2(String.valueOf(0));
			odsFile.setRATE_ITEM_ID3(String.valueOf(0));
			odsFile.setRATE_ITEM_ID4(String.valueOf(0));
			odsFile.setRATEMETHOD('1');
			odsFile.setRCDSEQNUM((int) (Math.random() * 100000000));
			odsFile.setRECORD_TYPE('1');
			odsFile.setRECORDEXTENSION("2000000002|0|3762|784|0");
			odsFile.setRESULT('1');
			odsFile.setREVISION((short)(int) (Math.random() * 10));
			odsFile.setROAM_TYPE('0');
			odsFile.setSERVICE_TYPE(String.valueOf((int)(Math.random() * 100)));
			odsFile.setSGSN("DDB1D690");
			odsFile.setSGSN_CHANGE(null);
			odsFile.setSOURCE_TYPE("M");
			odsFile.setSPA("0AF3CE0F");
			odsFile.setSTART_DATE(String.valueOf(odsFile.getCDR_DAY()));
			odsFile.setSTART_TIME((new SimpleDateFormat("hhmmss")).format(randomTime("00:00:00", "24:59:59")));
			odsFile.setTARIFF1('A');
			odsFile.setTARIFF2('B');
			odsFile.setTARIFF3('C');
			odsFile.setTARIFF4('D');
			odsFile.setTARIFF5('E');
			odsFile.setTARIFF6('F');
			odsFile.setTOTAL_FEE((long) 0);
			odsFile.setTPREMARK("31441|1|0|1#");
			odsFile.setUSER_ID((long) ((long)(Math.random() *100000000 *100000000)));
			odsFile.setUSER_TYPE(String.valueOf(0));
			odsFile.setVISIT_AREA_CODE(String.valueOf((int)(Math.random() * 1000)));// 到访地区号
			odsFile.setVISIT_COUNTY(null);
			writeTxtFile2(odsFile);
			filesize = new FileInputStream(file).available()/1024/1024;
//			System.out.println(filesize);
            
			
			if(filesize >= 10){
				partfilename = "gprs_"
						+ (new SimpleDateFormat("yyyyMMdd")).format(new Date()) + "_"
						+ String.format("%04d", j);

//				System.out.println(partfilename);
				creatTxtFile(partfilename);
//				filesize = new FileInputStream(file).available()/1024 ;
				break;
			}
			
			}
		}

		}
}
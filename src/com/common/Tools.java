package com.common;

import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class Tools {
	/**
	 * 功能：将数据库字段转变为Java变量，且第一个字母大写，如ORG_ID转化后为OrgId。
	 * 
	 * @param colname
	 * @return
	 */
	public static String datebaseToJava(String colname) {
		String tmp = "";
		colname = colname.toLowerCase();
		String[] tmpArr = colname.split("_");
		for (int i = 0; i < tmpArr.length; i++) {
			String innerTmp = tmpArr[i];
			innerTmp = innerTmp.substring(0, 1).toUpperCase()
					+ innerTmp.substring(1, innerTmp.length());
			tmp += innerTmp;
		}
		return tmp;
	}

	/**
	 * Timestamp 转换为String
	 * 
	 * @param ts
	 * @return
	 */
	public static String timestampToStr(Timestamp ts) {
		String tsStr = "";
		DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		try {
			tsStr = sdf.format(ts);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tsStr;
	}

	/**
	 * String 转换为Timestamp String的类型必须形如： yyyy-mm-dd hh:mm:ss[.f...]
	 * 这样的格式，中括号表示可选，否则报错！！！
	 * 
	 * @param tsStr
	 * @return
	 */
	public static Timestamp StrTotimestamp(String tsStr) {
		Timestamp ts = null;
		try {
			ts = Timestamp.valueOf(tsStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ts;
	}
	
	public static String printList(List list, Class clazz) {
		String classname = clazz.getName().substring(clazz.getName().lastIndexOf('.')+ 1);
		StringBuffer result = new StringBuffer();
		result.append("List element type:【" + classname + "】\n");
		int num = 0;
		for (Object obj : list) {
			result.append("\t【" +(++num) + "】: ");
			Method[] methods = obj.getClass().getMethods();
			try {
				for (int i = 0; i < methods.length; i++) {
					if (methods[i].getName().startsWith("get")) {
						// result.append(methods[i].getName().substring(3) +
						// "=");
						result.append(methods[i].invoke(obj));
						result.append("  ");
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			result.append("\n");
		}
		result.append("Element num: 【" + list.size() + "】\n");
		System.out.println(result);
		return result.toString();
	}
}

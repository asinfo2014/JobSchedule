package com.common.dbmanage;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.common.SysContants;
import com.common.Tools;

/**
 * 数据库工具类
 * 
 * @author wankun
 * 
 */
public class SysDAOTool {

	static Logger logger = Logger.getLogger(SysDAOTool.class);

	/**
	 * 
	 * @param conn
	 * @param sql
	 * @param args
	 * @return
	 */
	public Map<String, String> queryOneSQL(Connection conn, String sql,
			String... args) {
		Map<String, String> map = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(sql);
			if (args != null) {
				for (int i = 0; i < args.length; i++) {
					pstmt.setString(i + 1, args[i]);
				}
			}
			rs = pstmt.executeQuery();
			// 根据RS对象得到表中具体信息
			ResultSetMetaData rsmd = rs.getMetaData();
			// 得到表有多少列
			int columnCount = rsmd.getColumnCount();
			// 获取列的名称
			String[] columnName = new String[columnCount];
			for (int i = 0; i < columnCount; i++) {
				columnName[i] = rsmd.getColumnName(i + 1);
			}

			// 将结果集封装到对象中
			while (rs.next()) {
				// 封装一条数据
				map = new HashMap<String, String>();
				for (int i = 0; i < columnCount; i++) {
					map.put(columnName[i], rs.getString(i + 1));
				}
			}
		} catch (SQLException e) {
			logger.error("query database failed");
			e.printStackTrace();
		}finally
		{
			DBManage.close(rs);
			DBManage.close(pstmt);
		}
		return map;
	}

	/**
	 * 查询全部记录
	 * 
	 * @param conn
	 *            数据库连接
	 * @param sql
	 *            执行的SQL
	 * @param paras
	 *            SQL参数
	 * @param clazz
	 *            查询结果Bean
	 * @return
	 * @throws SQLException
	 */
	public List queryAll(Connection conn, String sql, List paras, Class clazz) throws SQLException {
		SysDAOTool.printSQL(sql, paras);// 打印sql,作测试用,发布时抹去
		List list = new ArrayList();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Object obj = null;
		try {
			BaseDBOperator bs = new BaseDBOperator();

			pstmt = conn.prepareStatement(sql);
			rs = bs.getResult(pstmt, sql, paras);

			String[] colNames = null;// 查询的列名数组
			colNames = bs.getColNames(rs);// 如果未被指定,默认取结果集中所有列

			while (rs.next()) {
				obj = this.getBean(rs, colNames, clazz);
				list.add(obj);
			}
		} catch (SQLException se) {
			logger.error(se);
			throw se;
		}
		return list;
	}

	/**
	 * 查询单条记录明细
	 * 
	 * @param sql
	 *            ,paras sql信息对象
	 * @param clazz
	 *            供数据赋值的bean类
	 * @return 单条记录
	 * @throws SQLException
	 */
	public Object querySimpleObj(Connection conn, String sql, List paras,
			Class clazz) throws SQLException {
		Object obj = null;

		List rslist = this.queryAll(conn, sql, paras, clazz);
		if (rslist != null && rslist.size() > 0) {
			obj = rslist.get(0);
		}
		return obj;
	}

	/**
	 * 
	 * @param conn
	 * @param sql
	 * @param args
	 * @return
	 */
	public boolean updateSQL(Connection conn, String sql) {
		PreparedStatement pstmt = null;
		boolean flag = false;
		try {
			pstmt = conn.prepareStatement(sql);
			if (pstmt.executeUpdate() > 0) {
				flag = true;
			}
			DBManage.commit(conn);
		} catch (SQLException e) {
			e.printStackTrace();
			DBManage.rollback(conn);
		}
		return flag;
	}

	/**
	 * 增加、修改、删除记录
	 * 
	 * @param conn
	 *            数据库连接
	 * @param sql
	 * @param paras
	 * @return
	 * @throws SQLException
	 */
	public boolean updateSQL(Connection conn, String sql, List paras) throws SQLException {
		SysDAOTool.printSQL(sql, paras);// 打印sql,作测试用,发布时抹去
		BaseDBOperator bs = new BaseDBOperator();
		int count = -1;
		try {
			count = bs.baseOp(conn, sql, paras);
		} catch (SQLException e) {
			logger.error(e);
			throw e;
		}
		return (count < 0) ? false : true;
	}

	/**
	 * 决断一个类是否是简单类型
	 * 
	 * @param clazz
	 *            类
	 * @return boolean值
	 */
	public boolean isSmpleType(Class clazz) {
		boolean flag = false;
		if (clazz.equals(boolean.class)) {
			flag = true;
		} else if (clazz.equals(int.class)) {
			flag = true;
		} else if (clazz.equals(double.class)) {
			flag = true;
		} else if (clazz.equals(short.class)) {
			flag = true;
		} else if (clazz.equals(long.class)) {
			flag = true;
		} else if (clazz.equals(char.class)) {
			flag = true;
		} else if (clazz.equals(float.class)) {
			flag = true;
		} else if (clazz.equals(String.class)) {
			flag = true;
		} else if (clazz.equals(Integer.class)) {
			flag = true;
		} else if (clazz.equals(BigDecimal.class)) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 把结果集中的一条记录赋值给bean
	 * 
	 * @param rs
	 *            结果集
	 * @param clazz
	 *            bean的类
	 * @param colNames
	 *            列名数组
	 * @return bean
	 * @throws SQLException
	 */
	private Object getBean(ResultSet rs, String[] colNames, Class clazz) {
		String columnName = null;
		Object obj = null;
		try {
			if (isSmpleType(clazz)) {
				// columnName = colNames[0];
				obj = rs.getObject(1);
			} else {
				obj = clazz.newInstance();
				Method[] method = obj.getClass().getMethods();
				for (int i = 0; i < colNames.length; i++) {
					columnName = colNames[i];
					String methodName = "set"
							+ Tools.datebaseToJava(columnName);
					for (int j = 0; j < method.length; j++) {
						if (methodName.equalsIgnoreCase(method[j].getName())) {
							Object[] arrayObject = new Object[1];
							Class[] srcParaType = method[j].getParameterTypes();
							Object tmp = rs.getObject(columnName);

							arrayObject[0] = tmp;
							// 将rs数据转换为set方法的参数实例，然后反射入obj对象中
							method[j].invoke(obj, arrayObject);
							break;
						}
					}
				}
			}
		}

		catch (SQLException se) {
			se.printStackTrace();
			logger.error("ResultSet数据反射入Bean失败" + se.getErrorCode());
		}

		catch (Exception se) {
			se.printStackTrace();
			if (!columnName.equals("")) {
				logger.error("取字段 " + columnName + " 出错 " + se.getMessage());
			}
		}
		return obj;
	}

	/**
	 * 辅助方法,帮助在后台打印Sql
	 * 
	 * @param sql
	 * @param paras
	 * @return
	 */
	private static String printSQL(String sql, List paras) {
		if (paras != null) {
			for (int i = 0; i < paras.size(); i++) {
				Object obj = paras.get(i);
				String value = "";
				if (obj instanceof Number) {
					value = String.valueOf(obj);
				} else {
					value = "'" + String.valueOf(obj) + "'";
				}

				sql = sql.replaceFirst("\\?", value);
			}
		}
		if (SysContants.PRINTSQL)
			logger.debug("【您执行的SQL】: " + sql);
		return sql;
	}

}

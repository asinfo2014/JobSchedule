package com.common.dbmanage;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.log4j.Logger;

public class DBManage {

	static Logger logger = Logger.getLogger(DBManage.class);
	private static Properties p = null;

	private static Properties getProperties() {
		if (p == null) {
			String configpath = Thread.currentThread().getContextClassLoader()
					.getResource("").getPath()
					+ "db.properties";
			p = new Properties();
			try {
				InputStream is = new FileInputStream(configpath);
				p.load(is);
			} catch (IOException e) {
				logger.error("db.properties文件读取失败");
				p = null;
				e.printStackTrace();
			}
		}
		return p;
	}

	public static Connection getConnection() {
		Connection conn = null;
		try {
			p = getProperties();
			Class.forName(p.getProperty("driverclass"));
			conn = DriverManager.getConnection(p.getProperty("url"),
					p.getProperty("username"), p.getProperty("password"));
			
			conn.setAutoCommit(false);
		} catch (SQLException se) {
			logger.error("取数据的连接出错" + se.getMessage());
			se.printStackTrace();
		} catch (ClassNotFoundException e) {
			logger.error("未加载到数据库连接驱动类");
			e.printStackTrace();
		}
		return conn;
	}

	public static void close(ResultSet rs) {

		if (rs != null) {
			try {
				rs.close();
			} catch (Exception se) {
				se.printStackTrace();
				logger.error("处理关ResultSet出错" + se.getMessage());
			}
		}
	}

	public static void close(PreparedStatement pstmt) {
		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (Exception se) {
				se.printStackTrace();
				logger.error("处理关PreparedStatement出错" + se.getMessage());
			}
		}
	}

	public static void close(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			} catch (Exception se) {
				se.printStackTrace();
				logger.error("处理关Connection出错" + se.getMessage());
			}
		}
	}

	public static void close(CallableStatement cs) {
		if (cs != null) {
			try {
				cs.close();
			} catch (Exception se) {
				se.printStackTrace();
				logger.error("处理关Connection出错" + se.getMessage());
			}
		}
	}

	public static void rollback(Connection conn) {
		if (conn != null) {
			try {
				conn.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			conn = null;
		}
	}

	public static void commit(Connection conn) {
		if (conn != null) {
			try {
				conn.commit();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
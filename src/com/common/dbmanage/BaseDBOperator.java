package com.common.dbmanage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

public class BaseDBOperator {

	static Logger logger = Logger.getLogger(BaseDBOperator.class);
	/**
	 * 得到数据库结果集
	 * 
	 * @param conn
	 *            数据库连接
	 * @param sql
	 *            预备查询语句
	 * @param paras
	 *            查询参数
	 * @return 结果集
	 * @throws SQLException
	 */
	public ResultSet getResult(PreparedStatement pstmt, String sql, List paras)
			throws SQLException {
		ResultSet rs = null;
		BaseDBOperator.setParas(pstmt, paras);// 设置占位符参数
		rs = pstmt.executeQuery();

		return rs;
	}

	/**
	 * 数据库增删改操作
	 * 
	 * @return
	 * @throws SQLException
	 */
	public int baseOp(Connection conn, String sql, List paras) throws SQLException
			 {
		int count = -1;
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			BaseDBOperator.setParas(pstmt, paras);// 设置占位符参数
			count = pstmt.executeUpdate();
		} catch (SQLException e) {
			throw e;
		} finally {
			DBManage.close(pstmt);
		}

		return count;
	}

	/**
	 * 设置占位符参数
	 * 
	 * @param pstmt
	 *            PreparedStatement对象
	 * @param paras
	 *            sql参数列表
	 * @throws SQLException
	 */
	private static void setParas(PreparedStatement pstmt, List paras)
			throws SQLException {
		if (paras != null && paras.size() != 0) {
			for (int i = 0; i < paras.size(); i++) {
				pstmt.setString(i + 1, String.valueOf(paras.get(i)));
			}
		}
	}

	/**
	 * 返回结果集中的列名数组
	 * 
	 * @param rs
	 *            结果集
	 * @return 列名数组
	 * @throws SQLException
	 */
	public String[] getColNames(ResultSet rs) throws SQLException {
		String[] cols = null;
		ResultSetMetaData md = rs.getMetaData();
		cols = new String[md.getColumnCount()];
		for (int i = 1; i <= md.getColumnCount(); i++) {
			cols[i - 1] = md.getColumnName(i);
		}
		return cols;
	}

}
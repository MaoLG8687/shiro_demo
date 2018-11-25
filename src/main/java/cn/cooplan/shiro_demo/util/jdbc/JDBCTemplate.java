package cn.cooplan.shiro_demo.util.jdbc;


import cn.cooplan.shiro_demo.exception.DataAccessException;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * JDBC模板
 * 
 * @author 李恒
 * 
 */
public class JDBCTemplate {

	/**
	 * 执行一个DML操作
	 * @param sql sql语句
	 * @param params sql语句中的参数
	 * @throws DataAccessException 数据访问失败异常，当此持久化操作未成功时，抛出此异常
	 */
	public void update(String sql,Object...params ) throws DataAccessException {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = JDBCUtil.getConnection();
			ps = con.prepareStatement(sql);
			if(params!=null){
				for (int i = 0; i < params.length; i++) {
					setParam(i + 1, params[i], ps);
				}
			}
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DataAccessException("数据访问失败", e);
		} finally {
			JDBCUtil.close(null, ps, null);
		}
	}
	
	/**
	 * 执行一个查询操作
	 * @param sql sql语句
	 * @param rm 回调接口，暴露ResultSet，并且对于ResultSet中的数据进行遍历，只需要对每一条结果进行处理
	 * @param params sql语句中的参数
	 * @return 根据RowMapper中的回调方法，返回一个封装了结果的集合，如果没有数据返回空集合
	 * @throws DataAccessException 数据访问失败异常，当此持久化操作未成功时，抛出此异常
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List query(String sql, RowMapper rm, Object...params) throws DataAccessException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List list = new ArrayList();
		try {
			con = JDBCUtil.getConnection();
			ps = con.prepareStatement(sql);
			if(params!=null){
				for (int i = 0; i < params.length; i++) {
					setParam(i + 1, params[i], ps);
				}
			}
			rs = ps.executeQuery();

			while (rs.next()) {

				// 用户调用方法时传的一段代码,将一行结果->一个对象
				Object obj = rm.mapRow(rs);

				list.add(obj);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new DataAccessException("数据访问失败", e);
		} finally {
			JDBCUtil.close(null, ps, rs);
		}

		return list;
	}

	/**
	 * 执行一个查询操作
	 * @param sql sql语句
	 * @param rse 回调接口，暴露ResultSet
	 * @param params sql语句中的参数
	 * @return 将ResultSetExtractor回调方法中的返回值返回
	 * @throws DataAccessException 数据访问失败异常，当此持久化操作未成功时，抛出此异常
	 */
	public Object query(String sql, ResultSetExtractor rse, Object...params) throws DataAccessException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Object obj = null;
		try {
			con = JDBCUtil.getConnection();
			ps = con.prepareStatement(sql);
			if(params!=null){
				for (int i = 0; i < params.length; i++) {
					setParam(i + 1, params[i], ps);
				}
			}
			rs = ps.executeQuery();

			obj = rse.extractData(rs);

		} catch (Exception e) {
			e.printStackTrace();
			throw new DataAccessException("数据访问失败", e);
		} finally {
			JDBCUtil.close(null, ps, rs);
		}

		return obj;
	}

	public void setParam(int index, Object param, PreparedStatement ps)
			throws Exception {
		if (param == null) {
			ps.setNull(index, java.sql.Types.VARCHAR);
		} else if (param instanceof String) {
			ps.setString(index, (String) param);
		} else if (param instanceof BigDecimal) {
			ps.setBigDecimal(index, (BigDecimal) param);
		} else if (param instanceof java.sql.Date) {
			Date data = (Date) param;
			ps.setDate(index, new java.sql.Date(data.getTime()));
		} else if (param instanceof java.sql.Time) {
			java.sql.Time data = (java.sql.Time) param;
			ps.setTime(index, new java.sql.Time(data.getTime()));
		} else if (param instanceof java.sql.Timestamp) {
			java.sql.Timestamp data = (java.sql.Timestamp) param;
			ps.setTimestamp(index, new java.sql.Timestamp(data.getTime()));
		} else if (param instanceof Date) {
			Date data = (Date) param;
			ps.setTimestamp(index, new java.sql.Timestamp(data.getTime()));
		} else {
			ps.setObject(index, param);
		}
	}

}

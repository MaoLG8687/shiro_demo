package cn.cooplan.shiro_demo.util.jdbc;

import java.sql.ResultSet;

/**
 * 回调接口，暴露Result，用于对结果集中的每一行记录处理
 * @author 李恒
 *
 */
public interface RowMapper {
	/**
	 * 结果集中每一行记录处理
	 * @param rs 结果集
	 * @return 结果集每一行记录处理的结果
	 * @throws Exception
	 */
	public Object mapRow(ResultSet rs)throws Exception;
}

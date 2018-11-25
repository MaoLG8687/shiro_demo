package cn.cooplan.shiro_demo.util.jdbc;

import java.sql.ResultSet;

/**
 * 回调接口，暴露Result，用于对结果集处理
 * @author 李恒
 *
 */
public interface ResultSetExtractor {
	/**
	 * 结果集处理
	 * @param rs 结果集
	 * @return 结果集处理的结果
	 * @throws Exception
	 */
	public Object extractData(ResultSet rs) throws Exception ;
}

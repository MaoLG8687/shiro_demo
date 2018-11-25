package cn.cooplan.shiro_demo.util.jdbc;

import cn.cooplan.shiro_demo.exception.ServiceException;

import java.sql.*;

/**
 * 访问数据库工具
 *
 * @Author MaoLG
 * @date 2018-11-23 23:23
 */
public class JDBCUtil {
    /**
     * 驱动注册
     */
    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("驱动注册失败");
        }
    }

    /**
     * 获取连接对象
     *
     * @return
     * @throws ServiceException
     */
    public static Connection getConnection() throws ServiceException {
        Connection connection = null;
        try {
            String url = "jdbc:mysql://localhost:3306/shequnke?useUnicode=true&characterEncoding=utf-8";
            String username = "root";
            String password = "root";
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServiceException("数据库连接对象获取失败");
        }
        return connection;
    }

    /**
     * 关闭数据库链接
     *
     * @param connection
     */
    static void close(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭资源
     *
     * @param con 连接
     * @param ps  操作对象
     * @param rs  结果集
     */
    public static void close(Connection con, PreparedStatement ps, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}

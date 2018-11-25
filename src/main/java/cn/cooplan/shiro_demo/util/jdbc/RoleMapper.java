package cn.cooplan.shiro_demo.util.jdbc;

import cn.cooplan.shiro_demo.pojo.Role;

import java.sql.ResultSet;

/**
 * @Author MaoLG
 * @date 2018-11-25 22:09
 */
public class RoleMapper implements RowMapper{
    @Override
    public Object mapRow(ResultSet rs) throws Exception {
        Role role = new Role();
        role.setName(rs.getString("role"));
        return role;
    }
}

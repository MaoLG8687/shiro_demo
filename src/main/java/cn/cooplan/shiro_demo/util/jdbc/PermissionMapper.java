package cn.cooplan.shiro_demo.util.jdbc;

import cn.cooplan.shiro_demo.pojo.Permission;

import java.sql.ResultSet;

/**
 * @Author MaoLG
 * @date 2018-11-25 22:03
 */
public class PermissionMapper implements RowMapper {
    @Override
    public Object mapRow(ResultSet rs) throws Exception {
        Permission permission = new Permission();
        permission.setName(rs.getString("name"));
        permission.setUrl(rs.getString("url"));
        return permission;
    }
}

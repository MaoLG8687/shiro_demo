package cn.cooplan.shiro_demo.dao;

import cn.cooplan.shiro_demo.pojo.Permission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * @Author MaoLG
 * @date 2018-11-20 22:31
 */
public interface PermissionDao extends BaseMapper<Permission> {

    /**
     * 根据username查找该用户下所有权限
     * @param username
     * @return
     */
    List<Permission> selectUserOwnPermission(String username);
}

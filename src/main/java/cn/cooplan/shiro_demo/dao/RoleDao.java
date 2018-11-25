package cn.cooplan.shiro_demo.dao;

import cn.cooplan.shiro_demo.pojo.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 *  权限
 * @Author MaoLG
 * @date 2018-11-23 22:35
 */
public interface RoleDao extends BaseMapper<Role> {
    /**
     * 查询用户拥有的角色
     * @param username
     * @return
     */
    List<Role> selectUserOwnRole(String username);
}

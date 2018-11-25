package cn.cooplan.shiro_demo.config.shiro;

import cn.cooplan.shiro_demo.dao.PermissionDao;
import cn.cooplan.shiro_demo.dao.RoleDao;
import cn.cooplan.shiro_demo.dao.UserDao;
import cn.cooplan.shiro_demo.pojo.Permission;
import cn.cooplan.shiro_demo.pojo.Role;
import cn.cooplan.shiro_demo.pojo.User;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 自定义realm
 *
 * @Author MaoLG
 * @Date 2018/11/17  13:03
 */
public class ShiroRealm extends AuthorizingRealm {
    //读取用户权限,我这里没有角色表, 具体根据自己实际需求编写
    @Autowired
    private PermissionDao permissionDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    /**
     * 授权
     * 实现授权具体业务
     * 备注, 只要将用户的权限, 角色在调用这个类的时候, 放入AuthorizationInfo对象中, 就可以访问
     * 拥有对应权限和角色的url
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //获得登录时提供的重要凭证, 这里获得的是登录账号
        String username = (String) principalCollection.getPrimaryPrincipal();
        //查询到该账号下所有的权限d
        List<Permission> permissions = permissionDao.selectUserOwnPermission(username);
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //把用户所有权限加在SimpleAuthorizationInfo 对象中, shiro自己来读取对比权限
        for (Permission permission : permissions) {
            info.addStringPermission(permission.getName());
        }
        //如果有角色的需要, 调用info.addRole("角色"); 同权限一个道理
        //添加角色
        List<Role> roles = roleDao.selectUserOwnRole(username);
        for (Role role: roles){
            info.addRole(role.getName());
        }
        System.out.println("授权");
        return info;
    }

    /**
     * 认证
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
            throws AuthenticationException {
        SimpleAuthenticationInfo info = null;
        try {
            //强转UsernamePasswordToken 类型
            UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
            //获得登录账号
            String username = token.getUsername();

            char[] pwd = token.getPassword();
            //获得登密码
            String password = String.valueOf(pwd);
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            QueryWrapper<User> queryWrapper = new QueryWrapper<>(user);
            User u = userDao.selectOne(queryWrapper);

            if (u == null) {
                throw new AuthenticationException("账号或密码错误");
            } else {
                //shiro默认配置回去找t_user表 来查询 , 也可根据自己实际业务编写
                info = new SimpleAuthenticationInfo(username, password, getName());
                System.out.println("认证");
            }
        } catch (AuthenticationException e) {
            throw e;
        }

        return info;
    }
}
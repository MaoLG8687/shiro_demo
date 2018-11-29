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
import java.util.concurrent.ConcurrentHashMap;

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

    //缓存存放用户权限的缓存  ConcurrentHashMap 实现并发线程安全
    private static ConcurrentHashMap<String, List<Permission>> permissionMap = new ConcurrentHashMap<>();

    /**
     * 删除缓存的权限, 当用户权限改变, 退出登录等调用该方法
     * @return
     */
    public static boolean deletePermissionCache(String username){
        permissionMap.remove(username + "permission");
        return true;
    }

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
        //从permissionMap取出当前权限的key
        String mapKey = username+"permission";

        //取出权限,  角色同该方法一样实现
        List<Permission> pers = permissionMap.get(mapKey);
        List<Permission> permissions = null;
        if(pers == null){
            //查询到该账号下所有的权限d
            permissions = permissionDao.selectUserOwnPermission(username);
            //放入缓存
            permissionMap.put(mapKey, permissions);
            System.out.println("新加权限");
        }else {
            permissions = pers;
            System.out.println("旧权限");
        }


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
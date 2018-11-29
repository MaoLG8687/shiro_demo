package cn.cooplan.shiro_demo.config.shiro;

import cn.cooplan.shiro_demo.exception.DataAccessException;
import cn.cooplan.shiro_demo.pojo.Permission;
import cn.cooplan.shiro_demo.util.jdbc.JDBCTemplate;
import cn.cooplan.shiro_demo.util.jdbc.PermissionMapper;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @Author MaoLG
 * @Date 2018/11/17  11:24
 */
@Configuration
public class ShiroConfig {

    /**
     * 设置cookie参数,
     * @return
     */
    @Bean
    public SimpleCookie remeberMeCookie(){
        //设置cookie名字
        SimpleCookie simpleCookie = new SimpleCookie("remeberMe");
        //设置cookie过期时间
        simpleCookie.setMaxAge(30);
        return simpleCookie;
    }

    /**
     * 记住我管理器, 这个要注入给securityManager
     * @return
     */
    @Bean
    public CookieRememberMeManager rememberMeManager(){
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        //设置cookie存储格式. 存储的key 和 有效期
        cookieRememberMeManager.setCookie(remeberMeCookie());
        //添加对cookie进行加密
        cookieRememberMeManager.setCipherKey(Base64.decode(String.valueOf(UUID.randomUUID())));
        return cookieRememberMeManager;
    }


    /**
     * ★必须 配置
     * ShiroRealm，这是个自定义的认证类，继承自AuthorizingRealm，
     * 负责用户的认证和权限的处理，可以参考JdbcRealm的实现。
     * 这个类是我自己定义的, 因为要根据自己实际需求来进行权限校验
     */
    @Bean
    public ShiroRealm shiroRealm() {
        ShiroRealm realm = new ShiroRealm();
        return realm;
    }

    /**
     * ★必须 配置
     * SecurityManager，权限管理，这个类组合了登陆，登出，权限，session的处理，是个比较重要的类。
     *
     * @return
     */
    @Bean
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(shiroRealm());
        //注入记住我管理器
        securityManager.setRememberMeManager(rememberMeManager());
        return securityManager;
    }

    /**
     * ★必须 配置
     * ShiroFilterFactoryBean，是个factorybean，为了生成ShiroFilter。
     * 它主要保持了三项数据，securityManager，filters，filterChainDefinitionManager。
     * 该类类似springmvc配置的访问权限设置
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean() throws DataAccessException {
        ShiroFilterFactoryBean shiroFilterFactoryBean = null;
        shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager());

        //url权限配置, url为controller设置的可访问的路径 ps:url可以使用*(通配符设置)
        Map<String, String> filterChainDefinitionManager = new LinkedHashMap<String, String>();

        //可以匿名访问的权限 anon:不登录也可访问
        filterChainDefinitionManager.put("/user/login", "anon");
        //必须登录的权限 authc:必须登录才能访问的权限
//        filterChainDefinitionManager.put("/user/add", "authc");
        filterChainDefinitionManager.put("/user/update", "authc");
        //退出登录 logout:退出登录, shiro帮我们实现, 该路径方法可不编写任何逻辑,可清楚shiro记录的认证
        filterChainDefinitionManager.put("/user/logout", "logout");

        //动态获取,  设置权限访问
        JDBCTemplate jdbcTemplate = new JDBCTemplate();
        String permissionSql = "select name , url from t_permission";
        List<Permission> permissions = jdbcTemplate.query(permissionSql, new PermissionMapper(), null);
        for (Permission permission: permissions){
            filterChainDefinitionManager.put(permission.getUrl(), "perms["+permission.getName()+"]");
        }

//        filterChainDefinitionManager.put("/user/add", "perms[add]"); //需要验证权限的必须先认证
//        filterChainDefinitionManager.put("/user/update", "perms[update]"); //需要验证权限的必须先认证
//        //添加角色
        filterChainDefinitionManager.put("/user/delete", "roles[admin]"); //需要验证权限的必须先认证

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionManager);
        shiroFilterFactoryBean.setLoginUrl("/login.html");
        shiroFilterFactoryBean.setUnauthorizedUrl("/401.html");
        return shiroFilterFactoryBean;
    }


}
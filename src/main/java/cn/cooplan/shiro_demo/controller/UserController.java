package cn.cooplan.shiro_demo.controller;

import cn.cooplan.shiro_demo.constant.Constant;
import cn.cooplan.shiro_demo.pojo.User;
import cn.cooplan.shiro_demo.util.ResultUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author MaoLG
 * @date 2018-11-20 22:43
 */
@RestController
@RequestMapping("/user/")
public class UserController {

    @RequestMapping("login")
    Object login(User user){
        try {
            UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());
            Subject subject = SecurityUtils.getSubject();
            subject.login(token);
        } catch (AuthenticationException e) {
            return new ResultUtil(Constant.ERROR, e.getMessage(),null);
        }
        return new ResultUtil(Constant.SUCCEED, "登录成功", null);
    }


    @RequestMapping("add")
    Object add(){
        return new ResultUtil(Constant.SUCCEED, "添加成功", null);
    }

    @RequestMapping("update")
    Object update(){
        return new ResultUtil(Constant.SUCCEED, "修改成功", null);
    }


    @RequestMapping("delete")
    Object delete(){
        return new ResultUtil(Constant.SUCCEED, "删除成功", null);
    }

    @RequestMapping("logout")
    Object logout(){
        return "退出成功";
    }

}

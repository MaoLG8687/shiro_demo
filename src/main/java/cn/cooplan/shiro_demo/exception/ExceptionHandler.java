package cn.cooplan.shiro_demo.exception;

import cn.cooplan.shiro_demo.constant.Constant;
import cn.cooplan.shiro_demo.util.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局的异常处理类
 * @Author MaoLG
 * @Date 2018/11/13  11:45
 */
@ControllerAdvice
@ResponseBody
public class ExceptionHandler {

    //日志对象
    private static final Logger logger = LoggerFactory.getLogger("log4j.properties");

    /**
     * 异常处理
     * @param request
     * @param exception
     * @return
     * @throws Exception
     */
    @org.springframework.web.bind.annotation.ExceptionHandler
    public Object allExceptionHandler(HttpServletRequest request,
                                      Exception exception) throws Exception{
        //将异常对象写入日志内
        logger.error(exception.getMessage(), exception);
        //返回异常信息, 直接返回给客户端
        return new ResultUtil(Constant.ERROR, null);
    }
}

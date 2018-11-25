package cn.cooplan.shiro_demo.util;

/**
 * @Author MaoLG
 * @Date 2018/11/10  13:50
 */
public class ResultUtil {

    private String code;

    private String msg;

    private Object obj;


    public String getCode() {
        return code;
    }

    public ResultUtil(String code, Object obj) {
        this.code = code;
        this.obj = obj;
    }

    public ResultUtil(String code, String msg, Object obj) {
        this.code = code;
        this.msg = msg;
        this.obj = obj;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }
}

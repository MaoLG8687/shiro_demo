package cn.cooplan.shiro_demo.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author MaoLG
 * @date 2018-11-20 22:38
 */
@TableName("t_user")
@Data
public class User implements Serializable {

    private Integer id;

    private String username;

    private String password;


}

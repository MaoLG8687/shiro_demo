package cn.cooplan.shiro_demo.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author MaoLG
 * @date 2018-11-21 22:52
 */
@Data
@TableName("t_role")
public class Role implements Serializable {

    private Integer id;

    private String name;

}

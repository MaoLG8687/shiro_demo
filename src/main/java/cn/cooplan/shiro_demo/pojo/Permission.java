package cn.cooplan.shiro_demo.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author MaoLG
 * @date 2018-11-20 22:26
 */
@TableName("t_permission")
@Data
public class Permission implements Serializable {

    private Integer id;

    private String name;

    private String url;
}

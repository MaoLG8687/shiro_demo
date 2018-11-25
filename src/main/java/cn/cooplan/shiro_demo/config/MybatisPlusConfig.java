package cn.cooplan.shiro_demo.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @Author MaoLG
 * @Date 2018/11/10  10:12
 */
@Configuration
@MapperScan("cn.cooplan.shiro_demo.dao")
public class MybatisPlusConfig {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    DataSource dataSource(){
        return new DruidDataSource();
    }
}

package com.ydhl.outsourcing.ts.finance;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by huangxinguang on 2017/5/11 下午5:08.
 */
//开启事物
//@EnableTransactionManagement
@SpringBootApplication
// mapper 接口类扫描包配置
@MapperScan("com.ydhl.outsourcing.ts.finance.dao")
public class Application {

    public static void main(String[] args) {

        // 程序启动入口
        // 启动嵌入式的 Tomcat 并初始化 Spring 环境及其各 Spring 组件
        SpringApplication.run(Application.class, args);
    }


}

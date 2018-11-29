package com.alpha;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 检验检查模块
 *
 */
@MapperScan(basePackages = {"com.alpha.**.mapper"}, sqlSessionTemplateRef = "sqlSessionTemplate")
@SpringBootApplication
@EnableScheduling
public class InspectionApp
{
    public static void main( String[] args )
    {
        SpringApplication.run(InspectionApp.class,args);
    }
}

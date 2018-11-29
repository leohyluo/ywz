package com.alpha;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
//@MapperScan(basePackages = {"com.alpha.**.mapper","com.alpha.commons.core.mapper"}, sqlSessionTemplateRef = "sqlSessionTemplate")
@MapperScan(basePackages = {"com.alpha.**.mapper","com.alpha.his.mapper"}, sqlSessionTemplateRef = "sqlSessionTemplate")
@ServletComponentScan(basePackages = {"com.alpha.**"})
@ComponentScan(basePackages = {"com.alpha"})
@EnableScheduling
@EnableHystrix //打开断路器
@EnableFeignClients(basePackages = {"com.alpha"})
@EnableDiscoveryClient
@SpringBootApplication
public class AlphaDataQuartzApplication {

	public static void main(String[] args) {
		SpringApplication.run(AlphaDataQuartzApplication.class, args);
	}

}

package com.alpha.api.gateway;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.ComponentScan;

@MapperScan("com.alpha.**.mapper")
@ServletComponentScan(basePackages={"com.alpha"})
@ComponentScan(basePackages={"com.alpha"})
@EnableDiscoveryClient
@SpringBootApplication
@EnableZuulProxy
public class AlphaApiGatewayStarter {
    public static void main(String[] args) {
        SpringApplication.run(AlphaApiGatewayStarter.class, args);
    }
}

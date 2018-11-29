package com.alpha.push;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

/**
 * Created by xc.xiong on 2017/8/31.
 */
@MapperScan(basePackages = {"com.alpha.**.mapper"}, sqlSessionTemplateRef = "sqlSessionTemplate")
@ServletComponentScan(basePackages = {"com.alpha.**"})
@ComponentScan(basePackages = {"com.alpha", "com.alpha.push.handler"})
@EnableFeignClients(basePackages = {"com.alpha.server.rpc.user","com.alpha.server.rpc.his"})
@EnableCircuitBreaker
@EnableRetry
@EnableDiscoveryClient
@EnableScheduling
@SpringBootApplication
public class PushApplication extends SpringBootServletInitializer {


    private static final Logger LOGGER = LoggerFactory.getLogger(PushApplication.class);


    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        LOGGER.info("SpringApplicationBuilder configure .... ");
        return builder.sources(PushApplication.class);
    }

    public static void main(String[] args) {
        LOGGER.info("SpringApplicationBuilder run .... ");
        SpringApplication.run(PushApplication.class, args);
    }

    @Autowired
    private RestTemplateBuilder builder;

    @Bean
    public RestTemplate restTemplate() {
        return builder.build();
    }
}

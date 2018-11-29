package com.alpha.his;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

@MapperScan(basePackages = "com.alpha.**.mapper", sqlSessionTemplateRef = "sqlSessionTemplate")
@ServletComponentScan(basePackages = {"com.alpha.**"})
@ComponentScan(basePackages = {"com.alpha"})
@EnableFeignClients(basePackages ={"com.alpha.server.rpc.user","com.alpha.server.rpc.his"})
@EnableCircuitBreaker
@EnableDiscoveryClient
@EnableScheduling
@SpringBootApplication
public class AlphaHisStarter {

    public static void main(String[] args) {
        SpringApplication.run(AlphaHisStarter.class);
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }
}

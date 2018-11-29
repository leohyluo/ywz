package com.alpha.wechar;

import com.alpha.commons.config.filter.CorsFilter;
import com.alpha.commons.config.pojo.FTPInfo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@EnableDiscoveryClient
@SpringBootApplication
@ServletComponentScan
@ComponentScan(basePackages = {"com.alpha.wechar"},basePackageClasses = {FTPInfo.class, CorsFilter.class})
public class WecharStarter {

    public static void main(String[] args) {
        SpringApplication.run(WecharStarter.class, args);
    }
}

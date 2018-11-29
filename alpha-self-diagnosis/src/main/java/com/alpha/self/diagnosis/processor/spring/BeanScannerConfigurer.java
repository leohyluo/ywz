package com.alpha.self.diagnosis.processor.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Created by vic on 2017/7/11.
 */
@Component
public class BeanScannerConfigurer implements BeanFactoryPostProcessor, ApplicationContextAware {
    private final static Logger s_logger = LoggerFactory.getLogger(BeanScannerConfigurer.class);
    private ApplicationContext applicationContext;

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        //获取spring上下文，初始化全局SpringContextHolder
        //SpringContextHolder.setApplicationContext(applicationContext);
        this.applicationContext = applicationContext;
    }

    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        s_logger.info("初始化 BasicAnswerProcessor  服务配置");
        ProcessorScanner scanner = new ProcessorScanner((BeanDefinitionRegistry) beanFactory);
        scanner.setResourceLoader(applicationContext);
        scanner.scan("com.alpha.self.diagnosis.processor.**");
        s_logger.info("初始化 BasicAnswerProcessor 完成");
    }
}

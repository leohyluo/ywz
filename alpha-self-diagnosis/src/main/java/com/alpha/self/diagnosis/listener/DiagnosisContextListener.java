package com.alpha.self.diagnosis.listener;

import com.alpha.commons.core.framework.SpringContextHolder;
import com.alpha.self.diagnosis.processor.BasicAnswerProcessorAdaptor;
import com.alpha.self.diagnosis.service.InitDataService;
import com.alpha.wechar.service.OfficalAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.annotation.Resource;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;


@WebListener
public class DiagnosisContextListener implements ServletContextListener {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private InitDataService initDataService;

	/*@Override
    public void contextInitialized(ServletContextEvent event) {
		
	}*/
    
    @Resource
    private OfficalAccountService officalAccountService;

    @Override
    public void contextInitialized(ServletContextEvent event) {
        logger.info("DiagnosisContextListener contextInitialized starting...");
        //获取spring上下文，初始化全局SpringContextHolder
        ApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(event.getServletContext());
        new SpringContextHolder().setApplicationContext(applicationContext);
        //初始化基础答案适配器
        BasicAnswerProcessorAdaptor.initial();
//        logger.info("创建公众号菜单");
//        officalAccountService.createMenu();
        logger.info("DiagnosisContextListener contextInitialized completed");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // TODO Auto-generated method stub

    }

}

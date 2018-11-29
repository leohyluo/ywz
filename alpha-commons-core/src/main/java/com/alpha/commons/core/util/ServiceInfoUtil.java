package com.alpha.commons.core.util;

import org.springframework.boot.context.embedded.EmbeddedServletContainerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Component
public class ServiceInfoUtil implements ApplicationListener<EmbeddedServletContainerInitializedEvent> {

    public String host = null;
    public int port;

    @Override
    public void onApplicationEvent(EmbeddedServletContainerInitializedEvent event) {
        this.port = event.getEmbeddedServletContainer().getPort();
        try {
            host = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }


}

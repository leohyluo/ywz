package com.alpha.self.diagnosis.utils;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AssignableTypeFilter;

import java.util.*;

/**
 * Created by lhkong on 2017/11/7.
 */
public class InstanceUtils {

    private InstanceUtils(){}


    static class InstanceException extends RuntimeException{

        private static final long serialVersionUID = -6019719862881215386L;

        public InstanceException(String message){
            super(message);
        }

        public InstanceException(String message, Throwable cause){super(message, cause);}
    }


    public static <T> List<T> getAllInstances(Class<T> clazz){
        ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(true);
        provider.addIncludeFilter(new AssignableTypeFilter(clazz));
        String packageName = clazz.getPackage().getName().replaceAll("\\.","/");
        Set<BeanDefinition> components = provider.findCandidateComponents(packageName);
        if(components.isEmpty()){
            return Collections.emptyList();
        }
        List<T> instances = new ArrayList<>();
        for (BeanDefinition definition : components) {
            try {
                Class<?> cls = Class.forName(definition.getBeanClassName());
                if(clazz.isAssignableFrom(cls)){
                    Object handle = cls.newInstance();
                    instances.add((T)handle);
                }
            } catch (ClassNotFoundException |IllegalAccessException | InstantiationException e) {
                throw new InstanceException("count not instance class "+ clazz.getName(),e);
            }
        }
        return instances;
    }

    public static <T> List<T> getAllInstances(Class<T> clazz, String... paths){
        ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(true);
        provider.addIncludeFilter(new AssignableTypeFilter(clazz));
        String packageName = clazz.getPackage().getName().replaceAll("\\.","/");
        Set<BeanDefinition> components = new HashSet<>();
        if(paths != null){
            List<String> packages = Arrays.asList(paths);
            packages.add(packageName);
            for (String path: packages) {
                components.addAll( provider.findCandidateComponents(path));
            }
        }
        if(components.isEmpty()){
            return Collections.emptyList();
        }
        List<T> instances = new ArrayList<>();
        for (BeanDefinition definition : components) {
            try {
                Class<?> cls = Class.forName(definition.getBeanClassName());
                if(clazz.isAssignableFrom(cls)){
                    Object handle = cls.newInstance();
                    instances.add((T)handle);
                }
            } catch (ClassNotFoundException |IllegalAccessException | InstantiationException e) {
                throw new InstanceException("count not instance class "+ clazz.getName(),e);
            }
        }
        return instances;
    }
}


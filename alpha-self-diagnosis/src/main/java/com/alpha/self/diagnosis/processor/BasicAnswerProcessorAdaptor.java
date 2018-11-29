package com.alpha.self.diagnosis.processor;

import com.alpha.commons.core.framework.SpringContextHolder;
import com.alpha.commons.util.StringUtils;
import com.alpha.self.diagnosis.annotation.BasicAnswerProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileFilter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.net.URLDecoder;
import java.util.*;
import java.util.stream.Stream;

@Service
public class BasicAnswerProcessorAdaptor {

    private Logger logger = LoggerFactory.getLogger(getClass());
    private static Map<String, AbstractBasicAnswerProcessor> processorMap;
    private static final String BASIC_ANSWERPROCESS_PATH = "com.alpha.self.diagnosis.processor";

    static {
        processorMap = new HashMap<>();
    }

    private BasicAnswerProcessorAdaptor() {
    }

    /**
     * 此方法在打成jar包运行时报文件路径未找到
     */
    public static void initial() {
        new BasicAnswerProcessorAdaptor().init();
    }

    public static void initial(List<String> beanNameList) {
        new BasicAnswerProcessorAdaptor().init(beanNameList);
    }

    public void init() {
        Set<Class<?>> classes = new HashSet<>();
        try {
//            scan(classes);
            maualScan(classes);
            register(classes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void init(List<String> beanNameList) {
        try {
            register(beanNameList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static AbstractBasicAnswerProcessor getProcessor(String questionCode) {
        AbstractBasicAnswerProcessor processor = null;
        for (String key : processorMap.keySet()) {
            Boolean exists = Stream.of(key.split(",")).filter(questionCode::equals).findAny().isPresent();
            if (exists) {
                processor = processorMap.get(key);
                return processor;
            }
        }
        return null;
    }

    private void scan(Set<Class<?>> classes) throws Exception {
        String originalPackPath = BASIC_ANSWERPROCESS_PATH;
        String packPath = originalPackPath.replace(".", File.separator);
        //URL url = Thread.currentThread().getContextClassLoader().getResource(packPath);
//        String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
        URL url = BasicAnswerProcessorAdaptor.class.getProtectionDomain().getCodeSource().getLocation();
        String filePath = URLDecoder.decode(url.getPath(), "UTF-8");
        filePath = filePath + File.separator + packPath;
        System.out.println("filePath=================" + filePath);

        File dir = new File(filePath);
        if (!dir.exists() || !dir.isDirectory()) {
            return;
        }
        File[] files = dir.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.getName().endsWith(".class");
            }
        });

        for (File file : files) {
            String fileName = file.getName();
            fileName = fileName.replace(".class", "");
            String className = originalPackPath + "." + fileName;
            Class<?> clazz = Class.forName(className);
            if (!clazz.isInterface() && !Modifier.isAbstract(clazz.getModifiers())) {
                Annotation[] annotations = clazz.getAnnotations();
                for (Annotation item : annotations) {
                    String str = item.toString();
                    System.out.println(str);
                }
                BasicAnswerProcessor processor = clazz.getAnnotation(BasicAnswerProcessor.class);
                if (processor != null && processor.enable() == true) {
                    classes.add(clazz);
                }
            }
        }
    }

    private void maualScan(Set<Class<?>> classes) throws Exception {
        String originalPackPath = BASIC_ANSWERPROCESS_PATH;
        /*String packPath = originalPackPath.replace(".", File.separator);
        URL url = Thread.currentThread().getContextClassLoader().getResource(packPath);
		String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
		File dir = new File(filePath);
		if(!dir.exists() || !dir.isDirectory()) {
			return;
		}*/

        List<String> fileList = new ArrayList<>();
        fileList.add("AbstractBasicAnswerProcessor");
        fileList.add("AllergicHistoryAnswerProcessor");
        fileList.add("BasicAnswerProcessorAdaptor");
        fileList.add("PastmedicalHistoryProcessor");
        //fileList.add("WeightProcessor");

        for (String fileName : fileList) {
            String className = originalPackPath + "." + fileName;
            Class<?> clazz = Class.forName(className);
            if (!clazz.isInterface() && !Modifier.isAbstract(clazz.getModifiers())) {
                Annotation[] annotations = clazz.getAnnotations();
                for (Annotation item : annotations) {
                    String str = item.toString();
                    System.out.println(str);
                }
                BasicAnswerProcessor processor = clazz.getAnnotation(BasicAnswerProcessor.class);
                if (processor != null && processor.enable() == true) {
                    classes.add(clazz);
                }
            }
        }
    }

    private void register(Set<Class<?>> classes) throws Exception {
        logger.info(classes.size() + " processor will be register.");
        String methodName = "setQuestionCode";
        for (Class<?> clazz : classes) {
            String className = clazz.getName();
            String beanName = getDefaultBeanName(className);
            Object obj = clazz.newInstance();
            Method method = clazz.getDeclaredMethod(methodName);
            Object objQuestionCode = method.invoke(obj);
            if (objQuestionCode != null) {
                Object processor = SpringContextHolder.getBean(beanName);
                String questionCode = objQuestionCode.toString();
                processorMap.put(questionCode, (AbstractBasicAnswerProcessor) processor);
            }
        }
    }

    private void register(List<String> beanNameList) throws Exception {
        logger.info(beanNameList.size() + " processor will be register.");
        for (String name : beanNameList) {
            Object object = SpringContextHolder.getBean(name);
            AbstractBasicAnswerProcessor processor = (AbstractBasicAnswerProcessor) object;
            String questionCode = processor.setQuestionCode();
            if (StringUtils.isNotEmpty(questionCode)) {
                processorMap.put(questionCode, processor);
            }
        }
    }

    private String getDefaultBeanName(String className) {
        String beanName = className;
        int lastIndex = className.lastIndexOf(".");
        if (lastIndex != -1) {
            String firstChar = String.valueOf(className.charAt(lastIndex + 1)).toLowerCase();
            beanName = firstChar + className.substring(lastIndex + 2);

        }
        return beanName;
    }
}

package com.alpha.self.diagnosis.processor.spring;

import com.alpha.self.diagnosis.annotation.BasicAnswerProcessor;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.context.annotation.*;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by vic on 2017/7/11.
 */
public final class ProcessorScanner extends ClassPathBeanDefinitionScanner {
    private static final Logger logger = LoggerFactory.getLogger(ProcessorScanner.class);
    private ResourcePatternResolver resourcePatternResolver;

    private MetadataReaderFactory metadataReaderFactory;
    private Environment environment;
    private ScopeMetadataResolver scopeMetadataResolver = new AnnotationScopeMetadataResolver();
    private BeanNameGenerator beanNameGenerator = new AnnotationBeanNameGenerator();


    public ProcessorScanner(BeanDefinitionRegistry registry) {
        super(registry);
        resourcePatternResolver = (ResourcePatternResolver) this.getResourceLoader();
        metadataReaderFactory = this.getMetadataReaderFactory();
        environment = this.getEnvironment();
    }

    public void registerDefaultFilters() {
        super.addIncludeFilter(new AnnotationTypeFilter(BasicAnswerProcessor.class));
    }

   /* @Override
    public Set<BeanDefinitionHolder> doScan(String... basePackages) {
        Set<BeanDefinition> candidates = openKeyScan(basePackages[0]);
        for (BeanDefinition candidate : candidates) {
            String classname = candidate.getBeanClassName();
            try {
//                Class clz=Class.forName(classname);
//                BasicAnswerProcessorAdaptor.register(clz);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return convert(candidates);
    }*/

    @Override
    public Set<BeanDefinitionHolder> doScan(String... basePackages) {
        Set<BeanDefinition> candidates = openKeyScan(basePackages[0]);
        Set<BeanDefinitionHolder> holderSet = convert(candidates);
        List<String> processorNames = new ArrayList<>();
        for (BeanDefinitionHolder item : holderSet) {
            processorNames.add(item.getBeanName());
            System.out.println(item.getBeanName());
        }
        //初始化基础问题处理器
        //BasicAnswerProcessorAdaptor.initial(processorNames);
        return holderSet;
    }

    /**
     * 取 控制器中第一个basePath
     *
     * @param pathObj
     * @return
     */
    private String basePath(Object pathObj) {
        if (pathObj instanceof String[]) {
            String[] pathArr = (String[]) pathObj;
            for (String path : pathArr) {
                if (StringUtils.isNotBlank(path)) {
                    return path;
                }
            }
        }
        return "";
    }

    /**
     * 用于 controller 的path 和方法的URL 合并  去除多余的"/"
     * 不支持url带入参数
     *
     * @param basePath
     * @param url
     * @return
     */

    private static String pathUrl(String basePath, String url) {
        if (basePath == null) basePath = "";
        if (url == null) url = "";
        return (basePath + "/" + url).replaceAll("\\*", "").replaceAll("//+", "/");
    }

    /*  @Override
     protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
          return super.isCandidateComponent(beanDefinition) && beanDefinition.getMetadata().hasAnnotation(OpenKey.class.getName());
      }*/
    public Set<BeanDefinition> openKeyScan(String basePackage) {
        Set<BeanDefinition> candidates = findCandidateComponents(basePackage);
        return candidates;
    }

    public Set<BeanDefinitionHolder> convert(Set<BeanDefinition> candidates) {
        Set<BeanDefinitionHolder> beanDefinitions = new LinkedHashSet<BeanDefinitionHolder>();
        for (BeanDefinition candidate : candidates) {
            ScopeMetadata scopeMetadata = this.scopeMetadataResolver.resolveScopeMetadata(candidate);
            candidate.setScope(scopeMetadata.getScopeName());
            String beanName = this.beanNameGenerator.generateBeanName(candidate, this.getRegistry());
            BeanDefinitionHolder definitionHolder = new BeanDefinitionHolder(candidate, beanName);
            definitionHolder = applyScopedProxyMode(scopeMetadata, definitionHolder, this.getRegistry());
            beanDefinitions.add(definitionHolder);
            registerBeanDefinition(definitionHolder, this.getRegistry());
        }
        return beanDefinitions;
    }

    public Set<BeanDefinitionHolder> openKeyScan(String... basePackages) {
        Assert.notEmpty(basePackages, "At least one base package must be specified");
        Set<BeanDefinitionHolder> beanDefinitions = new LinkedHashSet<BeanDefinitionHolder>();
        for (String basePackage : basePackages) {
            Set<BeanDefinition> candidates = findCandidateComponents(basePackage);
            for (BeanDefinition candidate : candidates) {
                ScopeMetadata scopeMetadata = this.scopeMetadataResolver.resolveScopeMetadata(candidate);
                candidate.setScope(scopeMetadata.getScopeName());
                String beanName = this.beanNameGenerator.generateBeanName(candidate, this.getRegistry());
                if (candidate instanceof AbstractBeanDefinition) {
                    postProcessBeanDefinition((AbstractBeanDefinition) candidate, beanName);
                }
                if (candidate instanceof AnnotatedBeanDefinition) {
                    AnnotationConfigUtils.processCommonDefinitionAnnotations((AnnotatedBeanDefinition) candidate);
                }
                //if (checkCandidate(beanName, candidate)) {
                if (true) {
                    BeanDefinitionHolder definitionHolder = new BeanDefinitionHolder(candidate, beanName);
                    definitionHolder = applyScopedProxyMode(scopeMetadata, definitionHolder, this.getRegistry());
                    beanDefinitions.add(definitionHolder);
                    registerBeanDefinition(definitionHolder, this.getRegistry());
                }
            }
        }
        return beanDefinitions;
    }

    public static BeanDefinitionHolder applyScopedProxyMode(
            ScopeMetadata metadata, BeanDefinitionHolder definition, BeanDefinitionRegistry registry) {

        ScopedProxyMode scopedProxyMode = metadata.getScopedProxyMode();
        if (scopedProxyMode.equals(ScopedProxyMode.NO)) {
            return definition;
        }
        boolean proxyTargetClass = scopedProxyMode.equals(ScopedProxyMode.TARGET_CLASS);
        return ScopedProxyCreator.createScopedProxy(definition, registry, proxyTargetClass);
    }

    @Override
    protected boolean isCandidateComponent(MetadataReader metadataReader) throws IOException {
        if (metadataReader.getAnnotationMetadata().hasAnnotation(BasicAnswerProcessor.class.getName())) {
            AnnotationMetadata metadata = metadataReader.getAnnotationMetadata();
            if (!metadata.isAnnotated(Profile.class.getName())) {
                return true;
            }
            AnnotationAttributes profile = MetadataUtils.attributesFor(metadata, Profile.class);
            return this.environment.acceptsProfiles(profile.getStringArray("value"));
        }
        return false;
    }

}

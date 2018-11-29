package com.alpha.self.diagnosis.processor.spring;

import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.MethodMetadata;

/**
 * Created by vic on 2017/7/12.
 */
public class MetadataUtils {
    public static AnnotationAttributes attributesFor(AnnotationMetadata metadata, Class<?> annoClass) {
        return attributesFor(metadata, annoClass.getName());
    }

    public static AnnotationAttributes attributesFor(AnnotationMetadata metadata, String annoClassName) {
        return AnnotationAttributes.fromMap(metadata.getAnnotationAttributes(annoClassName, false));
    }

    public static AnnotationAttributes attributesFor(MethodMetadata metadata, Class<?> targetAnno) {
        return AnnotationAttributes.fromMap(metadata.getAnnotationAttributes(targetAnno.getName()));
    }

}

/**
 * Project Name: 智慧药师大众版
 * File Name: RequestJsonParam.java
 * Package Name: com.zhys.server.controller.annotation
 * Date: 2016年4月8日下午2:01:33
 * Copyright (c) 2016, 深圳智慧药师股份有限公司  All Rights Reserved.
 */
package com.alpha.self.diagnosis.annotation;

import java.lang.annotation.*;

/**
 * TODO
 *
 * @author jianghu
 * @date 2016年4月8日 下午2:01:33 <br/>
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestJsonParam {

    /**
     * The name of the request parameter to bind to.
     */
    String value() default "";

    /**
     * Whether the parameter is required.
     * <p>Default is {@code true}, leading to an exception thrown in case
     * of the parameter missing in the request. Switch this to {@code false}
     * if you prefer a {@code null} in case of the parameter missing.
     * <p>Alternatively, provide a {@link #defaultValue() defaultValue},
     * which implicitly sets this flag to {@code false}.
     */
    boolean required() default true;

}

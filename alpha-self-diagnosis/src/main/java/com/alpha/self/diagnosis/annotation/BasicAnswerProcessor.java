package com.alpha.self.diagnosis.annotation;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BasicAnswerProcessor {

    boolean enable() default true;
}

package com.alpha.commons.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface JSON {

	Class<?> type();
	String include() default "";
	String exclude() default "startRow,endRow,prePage,nextPage,firstPage,isFirstPage,lastPage,isLastPage,hasPreviousPage,hasNextPage,navigatePages,navigatepageNums,navigateFirstPage,navigateLastPage";
}

package com.wangzhengzhen.commons.validator.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Length {

	int sortId() default -1;

	int[] groupId() default {};

	int fix() default -1;

	int max() default -1;

	int min() default -1;
	
	String failCode() default "";

	String failDesc() default "";

}

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
public @interface CheckValue {

	/**
	 * 用来排列验证的先后序，也可以用来标记在一个类中唯一属性
	 * @return
	 */
	int id() default -1;

	/**
	 * 组ID，可以在验证时指定验证某一组的字段
	 * @return
     */
	int[] groupId() default -1;

	String[] values();
	
	int failureDescResId() default -1;

	String failureCode() default "";

	String failureDesc() default "";
}
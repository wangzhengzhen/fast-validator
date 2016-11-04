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
public @interface Password {

	/**
	 * 用来排列验证的先后序，也可以用来标记在一个类中唯一属性
	 * @return
	 */
	int id() default -1;

	/**
	 * 组ID，可以在验证时指定验证某一组的字段
	 * @return
     */
	int[] groupId() default {};

	/**
	 * Android专用，resId指向string.xml中的资源，用于返回验证失败的信息
	 * 
	 * @return
	 */
	int failureDescResId() default -1;

	String failureCode() default "";
	
	String failureDesc() default "";

	/**
	 * 是否包含数字
	 * 
	 * @return
	 */
	boolean number() default false;

	/**
	 * 是否包含大写字母
	 * 
	 * @return
	 */
	boolean letterUpperCase() default false;

	/**
	 * 是否包含小写字母
	 * 
	 * @return
	 */
	boolean letterLowerCase() default false;

	/**
	 * 是否包含特殊字符
	 * 
	 * @return
	 */
	boolean otherCharacter() default false;

}

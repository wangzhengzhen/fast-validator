package com.wangzhengzhen.commons.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义验证性别的注解
 * @author wangzhengzhen
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Sex {

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

	String failCode() default "";
	
	String failDesc() default "";
	
	/**
	 * 性别取值，只能是 男 或 女
	 * @return
	 */
	String[] value() default {"男", "女"};
	
}

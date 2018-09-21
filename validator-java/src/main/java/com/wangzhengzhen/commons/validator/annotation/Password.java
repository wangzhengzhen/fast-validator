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

	int sortId() default -1;

	int[] groupId() default {};

	String failCode() default "";
	
	String failDesc() default "";

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

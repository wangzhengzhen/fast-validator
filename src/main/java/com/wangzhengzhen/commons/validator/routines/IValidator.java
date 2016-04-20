package com.wangzhengzhen.commons.validator.routines;

import java.lang.annotation.Annotation;

/**
 * 所有解析注解的类必须继承此接口
 * @author wangzhengzhen
 *
 */
public interface IValidator<T> {

	/**
	 *
	 * @return
     */
	T getAnnotation(Annotation anno);

	/**
	 *
	 * @return
     */
	int getId(Annotation anno);

	/**
	 *
	 * @param anno
	 * @return
     */
	int[] getGroupId(Annotation anno);

	/**
	 * 
	 * @param anno
	 * @param val
	 * @return
	 */
	boolean validate(Annotation anno, Object val);
	
	/**
	 * 
	 * @return
	 */
	String failureCode();
	
	/**
	 * 失败返回失败描述
	 * @return
	 */
	String failureDesc();
	
	/**
	 * 失败返回Android的资源ID
	 * @return
	 */
	int failureDescResId();
	
}

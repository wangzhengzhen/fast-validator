package com.wangzhengzhen.commons.validator;

import java.lang.annotation.Annotation;

/**
 * 所有解析注解的类必须继承此接口
 * @author wangzhengzhen
 *
 */
public interface Validator<T> {

	/**
	 *
	 * @return
     */
	T getAnnotation(Annotation anno);

	/**
	 *
	 * @return
     */
	int getSortId(Annotation anno);

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
	String failCode();
	
	/**
	 * 失败返回失败描述
	 * @return
	 */
	String failDesc();
	
}

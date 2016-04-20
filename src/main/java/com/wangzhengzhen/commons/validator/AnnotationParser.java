package com.wangzhengzhen.commons.validator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 注解解析器
 * 
 * @author wangzhengzhen
 *
 */
public class AnnotationParser {

	/**
	 * 解析注解列表
	 */
	private List<Class<?>> parserList;

	public AnnotationParser(List<Class<?>> list) {

		this.parserList = list;
	}

	public AnnotationParser(Class<?>... annos) {

		this.parserList = new ArrayList<Class<?>>();
		
		if (null != annos) {
			for (Class<?> a : annos) {
				this.parserList.add(a);
			}
		}
	}

	/**
	 * 获取字段在解析列表中的注解
	 * 
	 * @param field
	 * @return
	 */
	public List<Annotation> getFieldAnnotations(Field field) {
		
		List<Annotation> annoList = new ArrayList<Annotation>();

		// 获取字段所有注解
		Annotation[] annos = field.getDeclaredAnnotations();

		for (Annotation anno : annos) {
			Class<?> annoCls = anno.annotationType();
			if (parserList.contains(annoCls)) {
				// 将符合解析列表中的注解添加到返回结果中
				annoList.add(anno);
			}
		}

		return annoList;
	}
	
	public void addParser(Class<?> anno) {
		
		parserList.add(anno);
	}
	
	public void removeParser(Class<?> anno) {
		
		parserList.remove(anno);
	}

}

package com.wangzhengzhen.commons.validator;

import java.lang.annotation.Annotation;

/**
 * 验证性别
 * @author wangzhengzhen
 *
 */
public class SexValidator implements Validator<Sex> {

	private Sex sexAnno;
	
	public boolean validate(Annotation anno, Object val) {
		
		sexAnno = getAnnotation(anno);
		
		if (null == val) {
			
			return false;
		}
		
		String sex = val.toString();
		
		String[] value = sexAnno.value();
		boolean flag = false;
		for (String v : value) {
			if (sex.equals(v)) {
				flag = true;
				break;
			}
		}
		
		return flag;
	}
	
	public Sex getAnnotation(Annotation anno) {
		
		return (Sex) anno;
	}

	public int getSortId(Annotation anno) {
		
		Sex sexAnno = getAnnotation(anno);
		return sexAnno.id();
	}

	public int[] getGroupId(Annotation anno) {
		
		Sex sexAnno = getAnnotation(anno);
		return sexAnno.groupId();
	}

	public String failCode() {
		
		return sexAnno.failCode();
	}

	public String failDesc() {
		
		return sexAnno.failDesc();
	}

}

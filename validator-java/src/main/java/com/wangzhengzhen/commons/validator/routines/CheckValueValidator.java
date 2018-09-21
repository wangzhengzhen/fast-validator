package com.wangzhengzhen.commons.validator.routines;

import java.lang.annotation.Annotation;

import com.wangzhengzhen.commons.validator.Validator;
import com.wangzhengzhen.commons.validator.annotation.CheckValue;

/**
 * 
 * @author wangzhengzhen
 *
 */
public class CheckValueValidator implements Validator<CheckValue> {
	
	private CheckValue checkValAnno;

	public CheckValue getAnnotation(Annotation anno) {

		return (CheckValue) anno;
	}

	public int getSortId(Annotation anno) {

		CheckValue checkValAnno = getAnnotation(anno);

		if (null == checkValAnno) {
			return -1;
		}

		return checkValAnno.sortId();
	}

	public int[] getGroupId(Annotation anno) {

		CheckValue checkValAnno = getAnnotation(anno);

		if (null == checkValAnno) {
			return null;
		}

		return checkValAnno.groupId();
	}

	public boolean validate(Annotation anno, Object val) {

		checkValAnno = (CheckValue) anno;
		
		if (null == anno || null == val) {
			
			return false;
		}
		
		String str = val.toString();
		
		String[] values = checkValAnno.values();
		boolean flag = false;
		for (String v : values) {
			if (v.equals(str)) {
				flag = true;
				break;
			}
		}
		
		return flag;
	}

	public String failDesc() {
		
		return checkValAnno.failDesc();
	}
	
	public String failCode() {
		
		return checkValAnno.failCode();
	}

}

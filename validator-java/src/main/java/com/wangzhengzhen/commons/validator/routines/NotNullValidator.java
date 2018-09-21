package com.wangzhengzhen.commons.validator.routines;

import com.wangzhengzhen.commons.validator.Validator;
import com.wangzhengzhen.commons.validator.annotation.NotNull;

import java.lang.annotation.Annotation;

/**
 * 字段不能为 null，但可以为 ""
 * @author wangzhengzhen
 *
 */
public class NotNullValidator implements Validator<NotNull> {
	
	private NotNull notNullAnno;

	public NotNull getAnnotation(Annotation anno) {

		return (NotNull) anno;
	}

	public int getSortId(Annotation anno) {

		NotNull notNullAnno = getAnnotation(anno);

		if (null == notNullAnno) {
			return -1;
		}

		return notNullAnno.sortId();
	}

	public int[] getGroupId(Annotation anno) {

		NotNull notNullAnno = getAnnotation(anno);

		if (null == notNullAnno) {
			return null;
		}

		return notNullAnno.groupId();
	}

	public boolean validate(Annotation anno, Object val) {
		
		notNullAnno = (NotNull) anno;
		
		if (null == anno || null == val) {
			
			return false;
		}
		
		return true;
	}

	public String failDesc() {

		return notNullAnno.failDesc();
	}
	
	public String failCode() {
		
		return notNullAnno.failCode();
	}
}

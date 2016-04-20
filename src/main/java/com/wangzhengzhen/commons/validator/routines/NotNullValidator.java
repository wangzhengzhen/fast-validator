package com.wangzhengzhen.commons.validator.routines;

import com.wangzhengzhen.commons.validator.annotation.NotNull;

import java.lang.annotation.Annotation;

/**
 * 字段不能为 null，但可以为 ""
 * @author wangzhengzhen
 *
 */
public class NotNullValidator implements IValidator<NotNull> {
	
	private NotNull notNullAnno;

	public NotNull getAnnotation(Annotation anno) {

		return (NotNull) anno;
	}

	public int getId(Annotation anno) {

		NotNull notNullAnno = getAnnotation(anno);

		if (null == notNullAnno) {
			return -1;
		}

		return notNullAnno.id();
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

	public String failureDesc() {

		return notNullAnno.failureDesc();
	}

	public int failureDescResId() {

		return notNullAnno.failureDescResId();
	}
	
	public String failureCode() {
		
		return notNullAnno.failureCode();
	}
}

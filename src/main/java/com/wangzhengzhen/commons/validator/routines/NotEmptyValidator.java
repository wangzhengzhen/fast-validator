package com.wangzhengzhen.commons.validator.routines;

import java.lang.annotation.Annotation;

import com.wangzhengzhen.commons.validator.annotation.NotEmpty;

/**
 * 非空验证，字段不能为 null 或 ""
 * @author wangzhengzhen
 *
 */
public class NotEmptyValidator implements IValidator<NotEmpty> {
	
	private NotEmpty notEmptyAnno;

	public NotEmpty getAnnotation(Annotation anno) {

		return (NotEmpty) anno;
	}

	public int getId(Annotation anno) {

		NotEmpty notEmptyAnno = getAnnotation(anno);

		if (null == notEmptyAnno) {
			return -1;
		}

		return notEmptyAnno.id();
	}

	public int[] getGroupId(Annotation anno) {

		NotEmpty notEmptyAnno = getAnnotation(anno);

		if (null == notEmptyAnno) {
			return null;
		}

		return notEmptyAnno.groupId();
	}

	public boolean validate(Annotation anno, Object val) {
		
		notEmptyAnno = (NotEmpty) anno;
		
		if (null == anno || null == val) {

			return false;
		}
		
		if ("".equals(val.toString())) {
			
			return false;
		}
		
		return true;
	}

	public String failureDesc() {

		return notEmptyAnno.failureDesc();
	}

	public int failureDescResId() {

		return notEmptyAnno.failureDescResId();
	}
	
	public String failureCode() {
		
		return notEmptyAnno.failureCode();
	}
	
}

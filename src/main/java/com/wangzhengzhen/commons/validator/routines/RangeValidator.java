package com.wangzhengzhen.commons.validator.routines;

import java.lang.annotation.Annotation;

import com.wangzhengzhen.commons.validator.annotation.Range;

/**
 * 数值限制验证
 * @author wangzhengzhen
 *
 */
public class RangeValidator implements IValidator<Range> {
	
	private Range numAnno;

	public Range getAnnotation(Annotation anno) {

		return (Range) anno;
	}

	public int getId(Annotation anno) {

		Range numAnno = getAnnotation(anno);

		if (null == numAnno) {
			return -1;
		}

		return numAnno.id();
	}

	public int[] getGroupId(Annotation anno) {

		Range numAnno = getAnnotation(anno);

		if (null == numAnno) {
			return null;
		}

		return numAnno.groupId();
	}

	public boolean validate(Annotation anno, Object val) {
		
		numAnno = (Range) anno;
		
		if (null == anno || null == val) {

			return false;
		}

		int v = Integer.parseInt(val.toString());

		// 固定值验证
		int value = numAnno.value();
		if (value != -1 && value != v) {

			return false;
		}

		// 最小值验证
		int min = numAnno.min();
		if (min != -1 && value < min) {

			return false;
		}

		// 最大值验证
		int max = numAnno.max();
		if (max != -1 && value > max) {

			return false;
		}

		return true;
	}

	public String failureDesc() {

		return numAnno.failureDesc();
	}

	public int failureDescResId() {

		return numAnno.failureDescResId();
	}

	public String failureCode() {
		
		return numAnno.failureCode();
	}

}

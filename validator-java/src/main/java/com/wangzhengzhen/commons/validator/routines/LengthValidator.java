package com.wangzhengzhen.commons.validator.routines;

import com.wangzhengzhen.commons.validator.Validator;
import com.wangzhengzhen.commons.validator.annotation.Length;

import java.lang.annotation.Annotation;

/**
 * 长度限制验证
 * @author wangzhengzhen
 *
 */
public class LengthValidator implements Validator<Length> {
	
	private Length lenAnno;

	public Length getAnnotation(Annotation anno) {

		return (Length) anno;
	}

	public int getSortId(Annotation anno) {

		Length lenAnno = getAnnotation(anno);

		if (null == lenAnno) {
			return -1;
		}

		return lenAnno.sortId();
	}

	public int[] getGroupId(Annotation anno) {

		Length lenAnno = getAnnotation(anno);

		if (null == lenAnno) {
			return null;
		}

		return lenAnno.groupId();
	}

	public boolean validate(Annotation anno, Object val) {
		
		lenAnno = (Length) anno;
		
		if (null == anno || null == val) {

			return false;
		}

		String v = val.toString();
		int len = v.length();

		// 固定长度验证
		int fix = lenAnno.fix();
		if (fix != -1 && fix != len) {

			return false;
		}

		// 最小长度验证
		int min = lenAnno.min();
		if (min != -1 && len < min) {

			return false;
		}

		// 最大长度验证
		int max = lenAnno.max();
		if (max != -1 && len > max) {

			return false;
		}

		return true;
	}

	public String failDesc() {

		return lenAnno.failDesc();
	}

	public String failCode() {
		
		return lenAnno.failCode();
	}

}

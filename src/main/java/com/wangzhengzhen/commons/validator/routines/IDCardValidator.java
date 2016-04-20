package com.wangzhengzhen.commons.validator.routines;

import com.wangzhengzhen.commons.validator.annotation.IsIDCard;

import java.lang.annotation.Annotation;
import java.util.regex.Pattern;

/**
 * 验证中国居民身份证
 * @author wangzhengzhen
 *
 */
public class IDCardValidator implements IValidator<IsIDCard> {

	private IsIDCard idcardAnno;

	private Pattern pattern;

	{
		pattern = Pattern.compile("[a-z0-9A-Z]{15,18}");
	}

	public IsIDCard getAnnotation(Annotation anno) {

		return (IsIDCard) anno;
	}

	public int getId(Annotation anno) {

		IsIDCard idcardAnno = getAnnotation(anno);

		if (null == idcardAnno) {
			return -1;
		}

		return idcardAnno.id();
	}

	public int[] getGroupId(Annotation anno) {

		IsIDCard idcardAnno = getAnnotation(anno);

		if (null == idcardAnno) {
			return null;
		}

		return idcardAnno.groupId();
	}

	public boolean validate(Annotation anno, Object val) {

		idcardAnno = (IsIDCard) anno;

		if (null == anno || null == val) {

			return false;
		}

		String str = val.toString();

		return pattern.matcher(str).matches();
	}

	public String failureCode() {
		
		return idcardAnno.failureCode();
	}

	public String failureDesc() {
		
		return idcardAnno.failureDesc();
	}

	public int failureDescResId() {
		
		return idcardAnno.failureDescResId();
	}

}

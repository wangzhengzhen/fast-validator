package com.wangzhengzhen.commons.validator.routines;

import java.lang.annotation.Annotation;
import java.util.regex.Pattern;

import com.wangzhengzhen.commons.validator.annotation.IsEmail;

/**
 * 
 * @author wangzhengzhen
 *
 */
public class EmailValidator implements IValidator<IsEmail> {
	
	private IsEmail emailAnno;
	
	private Pattern pattern;
	
	{
		// 验证邮箱格式：xxx@xx.com 或 xxx@xx.com.cn
		pattern = Pattern.compile("\\w+(\\.\\w+)?@\\w+\\.\\w+(\\.\\w+)?");
	}

	public IsEmail getAnnotation(Annotation anno) {

		return (IsEmail) anno;
	}

	public int getId(Annotation anno) {

		IsEmail emailAnno = getAnnotation(anno);

		if (null == emailAnno) {
			return -1;
		}

		return emailAnno.id();
	}

	public int[] getGroupId(Annotation anno) {

		IsEmail emailAnno = getAnnotation(anno);

		if (null == emailAnno) {
			return null;
		}

		return emailAnno.groupId();
	}

	public boolean validate(Annotation anno, Object val) {

		emailAnno = (IsEmail) anno;
		
		if (null == anno || null == val) {
			
			return false;
		}
		
		String str = val.toString();
		
		return pattern.matcher(str).matches();
	}

	public String failureDesc() {
		
		return emailAnno.failureDesc();
	}

	public int failureDescResId() {
		
		return emailAnno.failureDescResId();
	}
	
	public String failureCode() {
		
		return emailAnno.failureCode();
	}

}

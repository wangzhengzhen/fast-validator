package com.wangzhengzhen.commons.validator.routines;

import com.wangzhengzhen.commons.validator.Validator;
import com.wangzhengzhen.commons.validator.annotation.IsPhoneNumber;

import java.lang.annotation.Annotation;
import java.util.regex.Pattern;

/**
 * 手机号码验证
 * @author wangzhengzhen
 *
 */
public class PhoneNumberValidator implements Validator<IsPhoneNumber> {
	
	private Pattern pattern;
	private IsPhoneNumber phoneNumberAnno;

	{
		pattern = Pattern.compile("(133|153|180|189|181|170|177|134|135|136|137|138|139|150|151|152|157|158|159|178"
				+ "|182|183|184|187|188|130|131|132|155|156|185|186|176|145|147)\\d{8}");
	}

	public IsPhoneNumber getAnnotation(Annotation anno) {

		return (IsPhoneNumber) anno;
	}

	public int getSortId(Annotation anno) {

		IsPhoneNumber isPhoneAnno = getAnnotation(anno);

		if (null == isPhoneAnno) {
			return -1;
		}

		return isPhoneAnno.sortId();
	}

	public int[] getGroupId(Annotation anno) {

		IsPhoneNumber isPhoneAnno = getAnnotation(anno);

		if (null == isPhoneAnno) {
			return null;
		}

		return isPhoneAnno.groupId();
	}

	public boolean validate(Annotation anno, Object val) {

		phoneNumberAnno = (IsPhoneNumber) anno;
		
		if (null == val) {
			return phoneNumberAnno.allowEmpty();
		}
		
		String str = val.toString();
		
		if ("".equals(str)) {
			return phoneNumberAnno.allowEmpty();
		}

		return pattern.matcher(str).matches();
	}

	public String failDesc() {

		return phoneNumberAnno.failDesc();
	}
	
	public String failCode() {
		
		return phoneNumberAnno.failCode();
	}
}

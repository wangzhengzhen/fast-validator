package com.wangzhengzhen.commons.validator.routines;

import com.wangzhengzhen.commons.validator.Validator;
import com.wangzhengzhen.commons.validator.annotation.Password;

import java.lang.annotation.Annotation;
import java.util.regex.Pattern;

/**
 * 密码复杂度验证
 * @author wangzhengzhen
 *
 */
public class PasswordValidator implements Validator<Password> {

	private Pattern number;
	private Pattern letterUpperCase;
	private Pattern letterLowerCase;
	private Pattern otherCharacter;
	private Password passAnno;

	{
		number = Pattern.compile(".*[0-9]+.*");
		letterUpperCase = Pattern.compile(".*[a-z]+.*");
		letterLowerCase = Pattern.compile(".*[A-Z]+.*");
		otherCharacter = Pattern.compile(".*[_~!@#$%^&*()].*");
	}

	public Password getAnnotation(Annotation anno) {

		return (Password) anno;
	}

	public int getSortId(Annotation anno) {

		Password pwdAnno = getAnnotation(anno);

		if (null == pwdAnno) {
			return -1;
		}

		return pwdAnno.sortId();
	}

	public int[] getGroupId(Annotation anno) {

		Password pwdAnno = getAnnotation(anno);

		if (null == pwdAnno) {
			return null;
		}

		return pwdAnno.groupId();
	}

	public boolean validate(Annotation anno, Object val) {
		
		passAnno = (Password) anno;

		if (null == anno || null == val) {

			return false;
		}
		
		boolean numFlag = true, upperCaseFlag = true, lowerCaseFlag = true, otherFlag = true;
		String passwd = val.toString();
		
		if (passAnno.number()) {
			numFlag = number.matcher(passwd).matches();
		}
		
		if (passAnno.letterUpperCase()) {
			upperCaseFlag = letterUpperCase.matcher(passwd).matches();
		}
		
		if (passAnno.letterLowerCase()) {
			lowerCaseFlag = letterLowerCase.matcher(passwd).matches();
		}
		
		if (passAnno.otherCharacter()) {
			otherFlag = otherCharacter.matcher(passwd).matches();
		}

		return numFlag && upperCaseFlag && lowerCaseFlag && otherFlag;
	}
	
	public String failDesc() {

		return passAnno.failDesc();
	}
	
	public String failCode() {
		
		return passAnno.failCode();
	}

}

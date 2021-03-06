package com.wangzhengzhen.commons.validator;

import com.wangzhengzhen.commons.validator.annotation.Range;
import com.wangzhengzhen.commons.validator.annotation.IsPhoneNumber;
import com.wangzhengzhen.commons.validator.annotation.NotEmpty;

public class TestModel {

	/**
	 * 注册时需要验证的字段
	 */
	public static final int GROUP_REGISTER = 1;
	
	/**
	 * 登录时需要验证的字段
	 */
	public static final int GROUP_LOGIN = 2;
	
	@NotEmpty(failDesc = "姓名不能为空")
	private String name;
	
	@Sex(groupId = GROUP_REGISTER, failDesc = "性别只能是‘男’或‘女’")
	private String sex;
	
	@Range(sortId = 1, groupId = GROUP_REGISTER, max = 120, min = 0, failDesc = "年龄只能是 0 - 120 岁")
	private int age;
	
	@IsPhoneNumber(groupId = {GROUP_REGISTER, GROUP_LOGIN}, failDesc = "手机号码格式错误")
	private String phoneNumber;
	
	@NotEmpty(groupId = GROUP_LOGIN, failDesc = "验证码不能为空")
	private String verifyCode;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getVerifyCode() {
		return verifyCode;
	}

	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}
	
	
}

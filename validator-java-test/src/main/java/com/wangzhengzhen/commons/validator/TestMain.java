package com.wangzhengzhen.commons.validator;

import java.util.List;

public class TestMain {

	/**
	 * 模拟场景：有两个接口，一个是注册，一个是登录，用户的信息存在 {@link TestModel} 中
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		
		// 因为验证性别的注解是自定义的，所以需要注册，否则不会验证该注解
		ValidatorUtil.register(Sex.class, new SexValidator());
		
		register();
		System.out.println("======== 分隔线 =========");
		login();
		
	}
	
	/**
	 * 登录时需要验证验证码
	 */
	public static void login() {
		
		TestModel model = new TestModel();
		model.setPhoneNumber("15522222222");
		model.setVerifyCode("123546");
		
		ValidateResult result = ValidatorUtil.checkAllAndGroup(model, TestModel.GROUP_LOGIN);
		
		if (result.isSuccess()) {
			System.out.println("登录验证成功");
		} else {
			System.out.println("登录验证失败");
			for (List<Validator> list : result.getFailValidators().values()) {
				for (Validator v : list) {
					System.out.println(v.failDesc());
				}
			}
		}
	}
	
	public static void register() {
		
		TestModel model = new TestModel();
		model.setName("张三");
		model.setAge(150);
		model.setSex("阴阳人");
		model.setPhoneNumber("15522222222");

		ValidateResult result = ValidatorUtil.checkAllAndGroup(model, TestModel.GROUP_REGISTER);
		
		if (result.isSuccess()) {
			System.out.println("注册验证成功");
		} else {
			System.out.println("注册验证失败");
			for (List<Validator> list : result.getFailValidators().values()) {
				for (Validator v : list) {
					System.out.println(v.failDesc());
				}
			}
		}
	}
	
}

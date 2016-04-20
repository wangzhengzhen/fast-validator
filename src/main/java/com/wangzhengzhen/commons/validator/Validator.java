package com.wangzhengzhen.commons.validator;

import com.wangzhengzhen.commons.validator.annotation.IsEmail;
import com.wangzhengzhen.commons.validator.annotation.IsPhoneNumber;
import com.wangzhengzhen.commons.validator.annotation.Length;
import com.wangzhengzhen.commons.validator.annotation.NotEmpty;
import com.wangzhengzhen.commons.validator.annotation.NotNull;
import com.wangzhengzhen.commons.validator.annotation.Password;
import com.wangzhengzhen.commons.validator.routines.EmailValidator;
import com.wangzhengzhen.commons.validator.routines.IValidator;
import com.wangzhengzhen.commons.validator.routines.LengthValidator;
import com.wangzhengzhen.commons.validator.routines.NotEmptyValidator;
import com.wangzhengzhen.commons.validator.routines.NotNullValidator;
import com.wangzhengzhen.commons.validator.routines.PasswordValidator;
import com.wangzhengzhen.commons.validator.routines.PhoneNumberValidator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 验证器
 * 
 * @author wangzhengzhen
 *
 */
public class Validator {
	
	private static Map<Class<?>, IValidator<?>> validators;
	private static AnnotationParser annoParser;
	
	static {
		
		validators = new HashMap<Class<?>, IValidator<?>>();
		annoParser = new AnnotationParser();
		
		register(IsEmail.class, new EmailValidator());
		register(IsPhoneNumber.class, new PhoneNumberValidator());
		register(Password.class, new PasswordValidator());
		register(NotNull.class, new NotNullValidator());
		register(NotEmpty.class, new NotEmptyValidator());
		register(Length.class, new LengthValidator());
	}
	
	/**
	 * 如果验证成功返回null，失败则返回验证器对象
	 * @param obj
	 * @return
	 */
	public static IValidator<?> checkAll(Object obj) {

		boolean flag = true;
		IValidator<?> validator = null;

		Class<?> cls = obj.getClass();
		Field[] fields = cls.getDeclaredFields();
		try {
			for (Field f : fields) {

				List<Annotation> annos = annoParser.getFieldAnnotations(f);
				for (Annotation anno : annos) {
					validator = validators.get(anno.annotationType());
					f.setAccessible(true);
					flag = validator.validate(anno, f.get(obj));
					if (! flag) {
						break;
					}
				}

				if (! flag) {
					break;
				}

			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();

		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		return  flag ? null : validator;
	}

	/**
	 *
	 * @param obj
	 * @param groupId
	 * @return
	 */
	public static IValidator<?> checkForGroupId(Object obj, Integer groupId) {

		boolean flag = true;
		IValidator<?> validator = null;

		Class<?> cls = obj.getClass();
		Field[] fields = cls.getDeclaredFields();
		try {

			for (Field f : fields) {

				List<Annotation> annos = annoParser.getFieldAnnotations(f);

				for (Annotation anno : annos) {

					validator = validators.get(anno.annotationType());

					boolean exist = false;
					int[] groupIds = validator.getGroupId(anno);

					for (int id : groupIds) {
						// 排除该注解的验证
						if (id == groupId) {
							exist = true;
							break;
						}
					}

					if (! exist) {
						continue;
					}

					f.setAccessible(true);
					flag = validator.validate(anno, f.get(obj));
					if (! flag) {
						break;
					}
				}

				if (! flag) {
					break;
				}

			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();

		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		return  flag ? null : validator;
	}
	
	/**
	 * 注册验证器
	 * @param anno
	 * @param validator
	 */
	public static void register(Class<?> anno, IValidator<?> validator) {
		
		validators.put(anno, validator);
		annoParser.addParser(anno);
	}
	
}

package com.wangzhengzhen.commons.validator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wangzhengzhen.commons.validator.annotation.IsEmail;
import com.wangzhengzhen.commons.validator.annotation.IsPhoneNumber;
import com.wangzhengzhen.commons.validator.annotation.Length;
import com.wangzhengzhen.commons.validator.annotation.NotEmpty;
import com.wangzhengzhen.commons.validator.annotation.NotNull;
import com.wangzhengzhen.commons.validator.annotation.Password;
import com.wangzhengzhen.commons.validator.annotation.Range;
import com.wangzhengzhen.commons.validator.routines.EmailValidator;
import com.wangzhengzhen.commons.validator.routines.IValidator;
import com.wangzhengzhen.commons.validator.routines.LengthValidator;
import com.wangzhengzhen.commons.validator.routines.NotEmptyValidator;
import com.wangzhengzhen.commons.validator.routines.NotNullValidator;
import com.wangzhengzhen.commons.validator.routines.PasswordValidator;
import com.wangzhengzhen.commons.validator.routines.PhoneNumberValidator;
import com.wangzhengzhen.commons.validator.routines.RangeValidator;

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
		register(Range.class, new RangeValidator());
	}

	/**
	 * 检查所有字段，按ID排序
	 * 
	 * @param obj
	 * @return 返回验证失败的验证器
	 */
	public static List<IValidator<?>> checkAll(Object obj) {

		List<IValidator<?>> list = new ArrayList<IValidator<?>>();

		Class<?> cls = obj.getClass();
		try {

			List<AnnotationParser.ParseModel> clsAnnosMap = annoParser.getClassAnnotations(cls, validators);
			for (AnnotationParser.ParseModel model : clsAnnosMap) {
				Field f = model.getField();
				Annotation anno = model.getAnno();
				IValidator<?> validator = model.getValidator();

				f.setAccessible(true);
				boolean flag = validator.validate(anno, f.get(obj));
				if (!flag) {
					list.add(validator);
				}
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();

		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		return list;
	}

	/**
	 * 验证指定组的字段，按ID排序
	 * 
	 * @param obj
	 * @param groupId
	 * @return 返回验证失败的验证器
	 */
	public static List<IValidator<?>> checkForGroup(Object obj, Integer groupId) {

		List<IValidator<?>> list = new ArrayList<IValidator<?>>();

		Class<?> cls = obj.getClass();

		try {

			List<AnnotationParser.ParseModel> clsAnnosMap = annoParser.getClassAnnotations(cls, validators);
			for (AnnotationParser.ParseModel model : clsAnnosMap) {
				Field f = model.getField();
				Annotation anno = model.getAnno();
				IValidator<?> validator = model.getValidator();

				boolean exist = false;
				int[] groupIds = validator.getGroupId(anno);
				for (int id : groupIds) {
					// 排除该注解的验证
					if (id == groupId) {
						exist = true;
						break;
					}
				}

				if (!exist) {
					continue;
				}

				f.setAccessible(true);
				boolean flag = validator.validate(anno, f.get(obj));
				if (!flag) {
					list.add(validator);
				}

			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();

		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		return list;
	}

	/**
	 * 
	 * @param obj
	 * @return
	 */
	public static IValidator<?> check(Object obj) {
		
		IValidator<?> rValidator = null;

		Class<?> cls = obj.getClass();
		try {

			List<AnnotationParser.ParseModel> clsAnnosMap = annoParser.getClassAnnotations(cls, validators);
			for (AnnotationParser.ParseModel model : clsAnnosMap) {
				Field f = model.getField();
				Annotation anno = model.getAnno();
				IValidator<?> validator = model.getValidator();

				f.setAccessible(true);
				boolean flag = validator.validate(anno, f.get(obj));
				if (!flag) {
					rValidator = validator;
					break;
				}
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();

		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		
		return rValidator;
	}
	
	/**
	 * 如果字段在指定组或未声明组ID时，则验证
	 * @param obj
	 * @param groupId
	 * @return
	 */
	public static List<IValidator<?>> checkForGroupAndDefault(Object obj, Integer groupId) {

		List<IValidator<?>> list = new ArrayList<IValidator<?>>();

		Class<?> cls = obj.getClass();

		try {

			List<AnnotationParser.ParseModel> clsAnnosMap = annoParser.getClassAnnotations(cls, validators);
			for (AnnotationParser.ParseModel model : clsAnnosMap) {
				Field f = model.getField();
				Annotation anno = model.getAnno();
				IValidator<?> validator = model.getValidator();

				boolean exist = false;
				int[] groupIds = validator.getGroupId(anno);
				for (int id : groupIds) {
					// 排除该注解的验证
					if (id == groupId) {
						exist = true;
						break;
					}
				}

				/**
				 * 如果指定group，且不存在则跳出
				 */
				if (!exist && groupIds.length > 0) {
					continue;
				}

				f.setAccessible(true);
				boolean flag = validator.validate(anno, f.get(obj));
				if (!flag) {
					list.add(validator);
				}

			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();

		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		return list;
	}
	
	/**
	 * 注册验证器
	 * 
	 * @param anno
	 * @param validator
	 */
	public static void register(Class<?> anno, IValidator<?> validator) {

		validators.put(anno, validator);
		annoParser.addParser(anno);
	}

}

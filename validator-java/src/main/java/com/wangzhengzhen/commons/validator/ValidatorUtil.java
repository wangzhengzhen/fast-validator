package com.wangzhengzhen.commons.validator;

import com.wangzhengzhen.commons.validator.annotation.*;
import com.wangzhengzhen.commons.validator.routines.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;

/**
 * 验证器
 * 
 * @author wangzhengzhen
 *
 */
public class ValidatorUtil {

	private static Map<Class<?>, Validator<?>> validators;
	private static AnnotationParser annotParser;

	static {

		validators = new HashMap<Class<?>, Validator<?>>();
		annotParser = new AnnotationParser();

		register(IsEmail.class, new EmailValidator());
		register(IsPhoneNumber.class, new PhoneNumberValidator());
		register(Password.class, new PasswordValidator());
		register(NotNull.class, new NotNullValidator());
		register(NotEmpty.class, new NotEmptyValidator());
		register(Length.class, new LengthValidator());
		register(Range.class, new RangeValidator());
		register(CheckValue.class, new CheckValueValidator());
	}

	private static ValidateResult check(Object obj, ValidateFlag flag, int... groupId) {

		ValidateResult validateResult = new ValidateResult();
		Map<Field, List<Validator>> failValidators = new LinkedHashMap<Field, List<Validator>>();

		try {
			Class<?> cls = obj.getClass();
			List<Set<AnnotationParser.ValidateModel>> listValidateModel = new ArrayList<Set<AnnotationParser.ValidateModel>>();
			if (null == groupId) {
				Set<AnnotationParser.ValidateModel> set = annotParser.getValidate(cls, validators);
				listValidateModel.add(set);
			} else {
				for (int i = 0, j = groupId.length; i < j; i++) {
					Set<AnnotationParser.ValidateModel> set = annotParser.getValidate(cls, groupId[i], validators);
					listValidateModel.add(set);
				}
			}

			for (Set<AnnotationParser.ValidateModel> set : listValidateModel) {
				// 中断标志
				boolean breakOff = false;
				for (AnnotationParser.ValidateModel model : set) {
					Field f = model.getField();
					Annotation annot = model.getAnnot();
					Validator<?> validator = model.getValidator();

					f.setAccessible(true);
					boolean result = validator.validate(annot, f.get(obj));
					if (!result) {
						List<Validator> validatorList = failValidators.get(f);
						if (null == validatorList) {
							validatorList = new ArrayList<Validator>();
							failValidators.put(f, validatorList);
						}
						validatorList.add(validator);
						if (flag == ValidateFlag.CHECK_ALL || flag == ValidateFlag.CHECK_ALL_AND_GROUP) {
							continue;
						} else {
							breakOff = true;
							break;
						}
					}
				}
				if (breakOff) {
					break;
				}
			}

			if (failValidators.isEmpty()) {
				validateResult.setSuccess(true);

			} else {
				validateResult.setSuccess(false);
				validateResult.setFailValidators(failValidators);
				List<Validator> validatorList = failValidators.get(failValidators.keySet().iterator().next());
				Validator v = validatorList.get(0);
				validateResult.setFailCode(v.failCode());
				validateResult.setFailDesc(v.failDesc());
			}

		} catch (IllegalArgumentException e) {
			e.printStackTrace();

		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		return validateResult;
	}

	public static ValidateResult check(Object obj) {

		return check(obj, ValidateFlag.CHECK);
	}

	public static ValidateResult checkAll(Object obj) {

		return check(obj,  ValidateFlag.CHECK_ALL);
	}

	public static ValidateResult checkGroup(Object obj, int groupId) {

		return check(obj, ValidateFlag.CHECK_GROUP, groupId);
	}

	public static ValidateResult checkGroup(Object obj, int[] groupId) {

		return check(obj, ValidateFlag.CHECK_GROUP, groupId);
	}

	public static ValidateResult checkAndGroup(Object obj, int groupId) {

		return check(obj, ValidateFlag.CHECK_AND_GROUP, groupId);
	}

	public static ValidateResult checkAndGroup(Object obj, int[] groupId) {

		return check(obj, ValidateFlag.CHECK_AND_GROUP, groupId);
	}

	public static ValidateResult checkAllAndGroup(Object obj, int groupId) {

		return check(obj, ValidateFlag.CHECK_ALL_AND_GROUP, groupId);
	}

	public static ValidateResult checkAllAndGroup(Object obj, int[] groupId) {

		return check(obj, ValidateFlag.CHECK_ALL_AND_GROUP, groupId);
	}
	
	/**
	 * 注册验证器
	 * 
	 * @param anno
	 * @param validator
	 */
	public static void register(Class<?> anno, Validator<?> validator) {

		validators.put(anno, validator);
		annotParser.addParser(anno);
	}

}

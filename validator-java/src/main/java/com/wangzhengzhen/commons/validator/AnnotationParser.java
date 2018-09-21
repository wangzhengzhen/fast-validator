package com.wangzhengzhen.commons.validator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;

/**
 * 注解解析器
 * 
 * @author wangzhengzhen
 *
 */
public class AnnotationParser {

	/**
	 * key：ClassName_GroupId
	 */
	private static Map<String, Set<ValidateModel>> parseMap = new HashMap<String, Set<ValidateModel>>();
	
	/**
	 * 解析注解列表
	 */
	private Collection<Class<?>> parseAnnotList = new ArrayList<Class<?>>();

	private Comparator comparator = new Comparator<ValidateModel>() {

		public int compare(ValidateModel arg0, ValidateModel arg1) {

			int id0 = arg0.validator.getSortId(arg0.annot);
			int id1 = arg1.validator.getSortId(arg1.annot);

			id0 = id0 < 1 ? 0 : id0;
			id1 = id1 < 1 ? 0 : id1;

			if (id0 < id1) {
				return 1;
			}
			if (id0 > id1) {
				return 0;
			}
			return 0;
		}
	};

	public AnnotationParser() {

	}

	public AnnotationParser(Collection<Class<?>> parseAnnotList) {

		parseAnnotList.addAll(parseAnnotList);
	}

	public Set<ValidateModel> getValidate(Class<?> cls, int groupId, final Map<Class<?>, Validator<?>> validators) {

		String key = cls.getName() + "_" + groupId;
		Set<ValidateModel> set = parseMap.get(key);
		if (null == set) {
			parseValidate(cls, validators);
			set = parseMap.get(key);
		}
		return set;
	}

	public Set<ValidateModel> getValidate(Class<?> cls, final Map<Class<?>, Validator<?>> validators) {

		String key = cls.getName();
		Set<ValidateModel> set = parseMap.get(key);
		if (null == set) {
			parseValidate(cls, validators);
			set = parseMap.get(key);
		}
		return set;
	}

	/**
	 *  解析类中所有声明要解析的注解
	 * @param cls
	 * @param validators
	 * @return
	 */
	public void parseValidate(Class<?> cls, final Map<Class<?>, Validator<?>> validators) {

		Field[] fields = cls.getDeclaredFields();
		for (Field f : fields) {
			// 获取字段所有注解
			Annotation[] annots = f.getDeclaredAnnotations();
			for (Annotation annot : annots) {
				Class<?> annoCls = annot.annotationType();
				if (parseAnnotList.contains(annoCls)) {
					// 将符合解析列表中的注解添加到返回结果中
					Validator<?> validator = validators.get(annoCls);
					if (null != validator) {
						int[] groupId = validator.getGroupId(annot);
						if (groupId.length == 0) {
							String key = cls.getName();
							Set<AnnotationParser.ValidateModel> set = parseMap.get(key);
							if (null == set) {
								set = new TreeSet<ValidateModel>(comparator);
								parseMap.put(key, set);
							}
							ValidateModel model = new ValidateModel();
							model.setAnnot(annot);
							model.setField(f);
							model.setValidator(validator);
							set.add(model);

						} else {

							for (int i = 0, j = groupId.length; i < j; i++) {
								if (groupId[i] <= 0) {
									throw new RuntimeException("groupId必须大于0");
								}
								String key = cls.getName() + "_" + groupId[i];
								Set<AnnotationParser.ValidateModel> set = parseMap.get(key);
								if (null == set) {
									set = new TreeSet<ValidateModel>(comparator);
									parseMap.put(key, set);
								}
								ValidateModel model = new ValidateModel();
								model.setAnnot(annot);
								model.setField(f);
								model.setGroupId(groupId[i]);
								model.setValidator(validator);
								set.add(model);
							}
						}
					}
				}
			}
		}
	}

	public void addParser(Class<?> annot) {

		parseAnnotList.add(annot);
	}

	public void removeParser(Class<?> annot) {

		parseAnnotList.remove(annot);
	}

	/**
	 * 
	 * @author wangzhengzhen
	 *
	 */
	static class ValidateModel {

		private Annotation annot;
		private Field field;
		private int groupId;
		private Validator<?> validator;

		public Annotation getAnnot() {
			return annot;
		}

		public void setAnnot(Annotation annot) {
			this.annot = annot;
		}

		public Field getField() {
			return field;
		}

		public void setField(Field field) {
			this.field = field;
		}

		public int getGroupId() {
			return groupId;
		}

		public void setGroupId(int groupId) {
			this.groupId = groupId;
		}

		public Validator<?> getValidator() {
			return validator;
		}

		public void setValidator(Validator<?> validator) {
			this.validator = validator;
		}

	}

}

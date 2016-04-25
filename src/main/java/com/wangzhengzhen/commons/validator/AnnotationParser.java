package com.wangzhengzhen.commons.validator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import com.wangzhengzhen.commons.validator.routines.IValidator;

/**
 * 注解解析器
 * 
 * @author wangzhengzhen
 *
 */
public class AnnotationParser {

	/**
	 * 解析注解列表
	 */
	private List<Class<?>> parserList;

	public AnnotationParser(List<Class<?>> list) {

		this.parserList = list;
	}

	public AnnotationParser(Class<?>... annos) {

		this.parserList = new ArrayList<Class<?>>();

		if (null != annos) {
			for (Class<?> a : annos) {
				this.parserList.add(a);
			}
		}
	}

	/**
	 * 获取字段在解析列表中的注解
	 * 
	 * @param field
	 * @return
	 */
	public List<Annotation> getFieldAnnotations(Field field) {

		List<Annotation> annoList = new ArrayList<Annotation>();

		// 获取字段所有注解
		Annotation[] annos = field.getDeclaredAnnotations();

		for (Annotation anno : annos) {
			Class<?> annoCls = anno.annotationType();
			if (parserList.contains(annoCls)) {
				// 将符合解析列表中的注解添加到返回结果中
				annoList.add(anno);
			}
		}

		return annoList;
	}

	/**
	 * 
	 * @param cls
	 * @param validators
	 * @return
	 */
	public List<ParseModel> getClassAnnotations(Class<?> cls, final Map<Class<?>, IValidator<?>> validators) {
		
		// 保存class中的所有注解
		List<ParseModel> list = new ArrayList<AnnotationParser.ParseModel>();

		Field[] fields = cls.getDeclaredFields();
		for (Field f : fields) {
			// 获取字段所有注解
			Annotation[] annos = f.getDeclaredAnnotations();
			for (Annotation anno : annos) {
				Class<?> annoCls = anno.annotationType();
				if (parserList.contains(annoCls)) {
					// 将符合解析列表中的注解添加到返回结果中
					IValidator<?> validator = validators.get(annoCls);
					if (null != validator) {
						ParseModel model = new ParseModel();
						model.setAnno(anno);
						model.setField(f);
						model.setValidator(validator);
						list.add(model);
					}
				}
			}
		}

		/**
		 * 按ID排序
		 */
		Collections.sort(list, new Comparator<ParseModel>() {

			public int compare(ParseModel arg0, ParseModel arg1) {

				int id0 = arg0.validator.getId(arg0.anno);
				int id1 = arg1.validator.getId(arg1.anno);
				
				id0 = id0 < 1 ? 0 : id0;
				id1 = id1 < 1 ? 0 : id1;
				
				if (id0 < id1) {
					return 1;
				}
				if (id0 > id1) {
					return -1;
				}
				return 0;
			}
		});
		return list;
	}

	public void addParser(Class<?> anno) {

		parserList.add(anno);
	}

	public void removeParser(Class<?> anno) {

		parserList.remove(anno);
	}

	/**
	 * 
	 * @author wangzhengzhen
	 *
	 */
	static class ParseModel {

		private Annotation anno;
		private Field field;
		private IValidator<?> validator;

		public Annotation getAnno() {
			return anno;
		}

		public void setAnno(Annotation anno) {
			this.anno = anno;
		}

		public Field getField() {
			return field;
		}

		public void setField(Field field) {
			this.field = field;
		}

		public IValidator<?> getValidator() {
			return validator;
		}

		public void setValidator(IValidator<?> validator) {
			this.validator = validator;
		}

		@Override
		public String toString() {
			return "ParseModel [anno=" + anno + ", field=" + field + ", validator=" + validator + "]";
		}

	}

}

package com.bench.runtime.convert.formatter.string;

import com.bench.common.enums.error.CommonErrorCodeEnum;
import com.bench.common.exception.BenchRuntimeException;
import com.bench.runtime.convert.formatter.string.annotations.BooleanToStringFormat;
import com.bench.runtime.convert.formatter.string.annotations.DateToStringFormat;
import com.bench.runtime.convert.formatter.string.annotations.StringFormatterAnno;
import com.bench.runtime.convert.formatter.string.impl.BooleanToStringFormatter;
import com.bench.runtime.convert.formatter.string.impl.DateToStringFormatter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * 字符串格式化工厂类
 * 
 * @author cold
 *
 * @version $Id: StringFormatterFactory.java, v 0.1 2015年9月23日 下午4:37:34 cold
 *          Exp $
 */
public class StringFormatterFactory {

	public static Map<Class<?>, StringFormatter> formatterMap = new HashMap<Class<?>, StringFormatter>();

	public static Map<Class<?>, StringFormatter> dynamicFormatterMap = new HashMap<Class<?>, StringFormatter>();

	static {
		formatterMap.put(DateToStringFormat.class, new DateToStringFormatter());
		formatterMap.put(BooleanToStringFormat.class, new BooleanToStringFormatter());
	}

	/**
	 * 得到第一个foramatter
	 * 
	 * @param field
	 * @return
	 */
	public static StringFormatter getFirstFormatter(Field field) {
		for (Annotation anno : field.getAnnotations()) {
			StringFormatter foramatter = formatterMap.get(anno.annotationType());
			if (foramatter != null) {
				return foramatter;
			}
		}
		StringFormatterAnno formatterClassAnno = field.getAnnotation(StringFormatterAnno.class);
		if (formatterClassAnno != null) {
			StringFormatter formatter = dynamicFormatterMap.get(formatterClassAnno.formatterClass());
			if (formatter == null) {
				try {
					formatter = (StringFormatter) formatterClassAnno.formatterClass().newInstance();
					dynamicFormatterMap.put(formatterClassAnno.formatterClass(), formatter);
				} catch (Exception e) {
					throw new BenchRuntimeException(CommonErrorCodeEnum.SYSTEM_ERROR, "创建格式化类异常,formatterClassAnno=" + formatterClassAnno);
				}
			}
			return formatter;
		}
		return null;
	}
}

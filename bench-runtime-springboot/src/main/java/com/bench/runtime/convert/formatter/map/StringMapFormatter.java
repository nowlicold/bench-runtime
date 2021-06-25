package com.bench.runtime.convert.formatter.map;


import com.bench.lang.base.clasz.field.utils.FieldUtils;
import com.bench.lang.base.object.utils.ObjectUtils;
import com.bench.runtime.convert.formatter.map.annotations.StringMapEntry;
import com.bench.runtime.convert.formatter.string.StringFormatter;
import com.bench.runtime.convert.formatter.string.StringFormatterFactory;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * 将对象格式为String Map
 * 
 * @author cold
 *
 * @version $Id: StringMapFormatter.java, v 0.1 2015年9月23日 下午5:17:51 cold Exp
 *          $
 */
public class StringMapFormatter {
	/**
	 * 将Object描述为Map<String,String>方式
	 * 
	 * @param object
	 * @return
	 */
	public static Map<String, String> format(Object object) {
		if (object == null) {
			return null;
		}
		Map<String, String> returnMap = new HashMap<String, String>();
		for (Field field : FieldUtils.getAllField(object.getClass())) {
			StringMapEntry mapEntry = field.getAnnotation(StringMapEntry.class);
			if (mapEntry == null) {
				continue;
			}
			Object value = FieldUtils.getFieldValue(field, object);
			StringFormatter stringFormatter = StringFormatterFactory.getFirstFormatter(field);
			if (stringFormatter == null) {
				returnMap.put(field.getName(), ObjectUtils.toString(value));
			} else {
				returnMap.put(field.getName(), stringFormatter.format(field, value));
			}
		}
		return returnMap;
	}
}

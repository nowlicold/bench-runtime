package com.bench.runtime.convert.formatter.string.impl;

import com.bench.lang.base.object.utils.ObjectUtils;
import com.bench.runtime.convert.formatter.string.StringFormatter;
import com.bench.runtime.convert.formatter.string.annotations.BooleanToStringFormat;

import java.lang.reflect.Field;

/**
 * Booelean到字符串格式
 * 
 * @author cold
 *
 * @version $Id: BooleanToStringFormatter.java, v 0.1 2015年9月23日 下午4:28:44
 *          cold Exp $
 */
public class BooleanToStringFormatter implements StringFormatter {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bench.common.lang.string.formatter.string.StringFormatter#format(
	 * java.lang.Object)
	 */
	@Override
	public String format(Field field, Object value) {
		// TODO Auto-generated method stub
		if (!(value instanceof Boolean)) {
			return ObjectUtils.toString(value);
		}
		BooleanToStringFormat format = field.getAnnotation(BooleanToStringFormat.class);
		return (Boolean) value ? format.trueString() : format.falaseString();
	}
}

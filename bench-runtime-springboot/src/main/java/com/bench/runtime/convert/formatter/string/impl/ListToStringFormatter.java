package com.bench.runtime.convert.formatter.string.impl;

import com.bench.lang.base.string.utils.StringUtils;
import com.bench.runtime.convert.formatter.map.annotations.StringJoiner;
import com.bench.runtime.convert.formatter.string.StringFormatter;

import java.lang.reflect.Field;
import java.util.List;

/**
 * 集合转转字符串
 * 
 * @author cold
 *
 * @version $Id: ListToStringFormatter.java, v 0.1 2015年9月30日 下午3:00:36
 *          Administrator Exp $
 */
public class ListToStringFormatter implements StringFormatter {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bench.common.lang.string.formatter.string.StringFormatter#format(
	 * java.lang.reflect.Field, java.lang.Object)
	 */
	@Override
	public String format(Field field, Object value) {
		// TODO Auto-generated method stub
		StringJoiner stringJoiner = field.getAnnotation(StringJoiner.class);
		String joiner = stringJoiner == null ? StringUtils.COMMA_SIGN : stringJoiner.value();
		return StringUtils.join((List<?>) value, joiner);
	}
}

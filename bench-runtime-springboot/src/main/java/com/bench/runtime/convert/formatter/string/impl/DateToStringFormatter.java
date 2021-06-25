package com.bench.runtime.convert.formatter.string.impl;

import com.bench.lang.base.date.utils.DateUtils;
import com.bench.lang.base.object.utils.ObjectUtils;
import com.bench.runtime.convert.formatter.string.StringFormatter;
import com.bench.runtime.convert.formatter.string.annotations.DateToStringFormat;

import java.lang.reflect.Field;
import java.util.Date;

/**
 * 日期到字符串格式
 * 
 * @author cold
 *
 * @version $Id: DateToStringFormatter.java, v 0.1 2015年9月23日 下午4:28:44 cold
 *          Exp $
 */
public class DateToStringFormatter implements StringFormatter {

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
		if (!(value instanceof Date)) {
			return ObjectUtils.toString(value);
		}
		DateToStringFormat foramt = field.getAnnotation(DateToStringFormat.class);
		return DateUtils.format((Date) value, foramt.value());
	}
}

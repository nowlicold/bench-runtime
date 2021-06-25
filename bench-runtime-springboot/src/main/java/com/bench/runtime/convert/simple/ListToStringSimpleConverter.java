/**
 * BenchCode.com Inc.
 * Copyright (c) 2005-2009 All Rights Reserved.
 */
package com.bench.runtime.convert.simple;

import com.bench.lang.base.string.utils.StringUtils;
import com.bench.runtime.convert.formatter.map.annotations.StringJoiner;

import java.lang.reflect.Field;
import java.util.List;

/**
 * 
 * @author cold
 *
 * @version $Id: ListToStringSimpleConverter.java, v 0.1 2019年12月25日 上午9:29:24 cold Exp $
 */
public class ListToStringSimpleConverter implements SimpleConverter {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bench.runtime.core.convert.simple.SimpleConverter#isSupport(java.lang.Class, java.lang.Class)
	 */
	@Override
	public boolean isSupport(Class<?> from, Class<?> to) {
		// TODO Auto-generated method stub
		return List.class.isAssignableFrom(from) && String.class.equals(to);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bench.runtime.core.convert.simple.SimpleConverter#convert(java.lang.reflect.Field, java.lang.reflect.Field, java.lang.Object)
	 */
	@Override
	public Object convert(Field fromField, Field toField, Object fromValue) {
		// TODO Auto-generated method stub
		String joiner = StringUtils.COMMA_SIGN;
		StringJoiner stringJoiner = fromField.getAnnotation(StringJoiner.class);
		if (stringJoiner != null) {
			joiner = stringJoiner.value();
		}
		return StringUtils.join((List<?>) fromValue, joiner);
	}

}

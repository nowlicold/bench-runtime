/**
 * BenchCode.com Inc.
 * Copyright (c) 2005-2009 All Rights Reserved.
 */
package com.bench.runtime.convert.field.from.impl;

import com.bench.lang.base.properties.utils.PropertiesUtils;
import com.bench.runtime.convert.field.from.FieldValueFromStringConverter;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * 将字符串转换为Field类型为Map<String,String>方式的map实例
 * 
 * @author cold
 *
 * @version $Id: StringMap_FieldValueFromStringConverter.java, v 0.1 2015年10月23日
 *          下午2:04:13 Administrator Exp $
 */
public class StringMap_FieldValueFromStringConverter implements FieldValueFromStringConverter {

	private String fieldName;

	public StringMap_FieldValueFromStringConverter(String fieldName) {
		super();
		this.fieldName = fieldName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bench.platform.convert.field.from.FieldValueFromStringConverter#
	 * getFieldName()
	 */
	@Override
	public String getFieldName() {
		// TODO Auto-generated method stub
		return fieldName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bench.platform.convert.field.from.FieldValueFromStringConverter#
	 * convert (java.util.Map, java.lang.String, java.lang.reflect.Field)
	 */
	@Override
	public Object convert(Map<String, String> dataMap, String value, Field field) {
		// TODO Auto-generated method stub
		return PropertiesUtils.restoreMap(value);
	}

}

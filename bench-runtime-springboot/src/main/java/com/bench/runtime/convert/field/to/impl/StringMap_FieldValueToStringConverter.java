/**
 * BenchCode.com Inc.
 * Copyright (c) 2005-2009 All Rights Reserved.
 */
package com.bench.runtime.convert.field.to.impl;

import com.bench.lang.base.properties.utils.PropertiesUtils;
import com.bench.runtime.convert.field.to.FieldValueToStringConverter;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * 将Fieldl类型为Map<String,String>方式的map实例转换为String值
 * 
 * @author cold
 *
 * @version $Id: StringMap_FieldValueToStringConverter.java, v 0.1 2015年10月23日
 *          下午2:01:50 Administrator Exp $
 */

public class StringMap_FieldValueToStringConverter implements FieldValueToStringConverter {

	private String fieldName;

	public StringMap_FieldValueToStringConverter(String fieldName) {
		super();
		this.fieldName = fieldName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bench.platform.convert.field.FieldValueToStringConverter#getFieldName
	 * ()
	 */
	@Override
	public String getFieldName() {
		// TODO Auto-generated method stub
		return fieldName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bench.platform.convert.field.FieldValueToStringConverter#convert(
	 * java.lang.Object, java.lang.reflect.Field, java.lang.Object)
	 */
	@Override
	public String convert(Object object, Field field, Object fieldValue) {
		// TODO Auto-generated method stub
		return PropertiesUtils.convert2String((Map<String, String>) fieldValue, false);
	}

}

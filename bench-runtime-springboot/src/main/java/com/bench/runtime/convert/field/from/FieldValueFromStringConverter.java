/**
 * BenchCode.com Inc.
 * Copyright (c) 2005-2009 All Rights Reserved.
 */
package com.bench.runtime.convert.field.from;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * 
 * @author cold
 *
 * @version $Id: FieldValueFromStringConverter.java, v 0.1 2015年10月23日 下午1:39:21
 *          Administrator Exp $
 */
public interface FieldValueFromStringConverter {

	/**
	 * 获取属性名
	 * 
	 * @return
	 */
	public String getFieldName();

	/**
	 * 转换String值到Object
	 * 
	 * @param dataMap
	 * @param value
	 * @param field
	 * @return
	 */
	public Object convert(Map<String, String> dataMap, String value, Field field);

}

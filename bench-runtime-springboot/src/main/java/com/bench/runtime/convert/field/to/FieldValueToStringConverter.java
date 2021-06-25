/**
 * BenchCode.com Inc.
 * Copyright (c) 2005-2009 All Rights Reserved.
 */
package com.bench.runtime.convert.field.to;

import java.lang.reflect.Field;

/**
 * 将fieldValue转换为String的converter
 * 
 * @author cold
 *
 * @version $Id: FieldValueToStringConverter.java, v 0.1 2015年10月23日 下午1:39:21
 *          Administrator Exp $
 */
public interface FieldValueToStringConverter {

	/**
	 * 获取属性名
	 * 
	 * @return
	 */
	public String getFieldName();

	/**
	 * 转换field值到String
	 * 
	 * @param object
	 * @param field
	 */
	public String convert(Object object, Field field, Object fieldValue);

}

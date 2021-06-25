package com.bench.runtime.convert.impl;

import com.bench.lang.base.convert.Convert;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

/**
 * 常用java数据类型转换
 * 
 * @author cold
 * 
 */
@Service
public class LangToLangConverter extends AbstractConverter {

	public static Class<?>[] LANG_CLASS = new Class<?>[] { String.class, Integer.class, BigDecimal.class, BigInteger.class, Boolean.class, Byte.class, Character.class,
			Date.class, Time.class, Timestamp.class, Short.class, Double.class, Float.class, Long.class, boolean.class, int.class, byte.class, short.class, long.class,
			float.class, double.class, void.class };

	@SuppressWarnings("unchecked")
	public <T> T convert(Object from, Object to, Class<T> toClass, String[] filteFromFields, String[] filteToFields, boolean ignoreNull) {
		// TODO Auto-generated method stub
		return (T) Convert.asType(toClass, from);
	}

	public Class<?>[] supportFrom() {
		// TODO Auto-generated method stub
		return LANG_CLASS;
	}

	public Class<?>[] supportTo() {
		// TODO Auto-generated method stub
		return LANG_CLASS;
	}
}

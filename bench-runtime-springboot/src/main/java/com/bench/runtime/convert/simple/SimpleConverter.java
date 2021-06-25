package com.bench.runtime.convert.simple;


import com.bench.lang.base.order.Ordered;

import java.lang.reflect.Field;

/**
 * 简单转换器
 * 
 * @author cold
 *
 * @version $Id: SimpleConverter.java, v 0.1 2019年12月25日 上午9:27:12 cold Exp $
 */
public interface SimpleConverter extends Ordered {

	/**
	 * 是否支持from到to
	 * 
	 * @param fromClass
	 * @param toClass
	 * @return
	 */
	public boolean isSupport(Class<?> fromClass, Class<?> toClass);

	/**
	 * 执行转换
	 * 
	 * @param fromField
	 * @param toField
	 * @param fromValue
	 * @return
	 */
	public Object convert(Field fromField, Field toField, Object fromValue);

	@Override
	default int order() {
		// TODO Auto-generated method stub
		return 0;
	}
}

package com.bench.runtime.convert.simple;

import com.bench.lang.base.list.utils.ListUtils;
import org.springframework.core.OrderComparator;

import java.util.List;

/**
 * 简单转换器工厂
 * 
 * @author cold
 *
 * @version $Id: SimpleConverterFactory.java, v 0.1 2019年12月25日 上午9:36:50 cold Exp $
 */
public class SimpleConverterFactory {

	/**
	 * 转换器集合
	 */
	private static final List<SimpleConverter> converters = ListUtils.toList(new ListToStringSimpleConverter(), new StringToListSimpleConverter());
	static {
		converters.sort(OrderComparator.INSTANCE);
	}

	/**
	 * 获取转换器
	 * 
	 * @param fromClass
	 * @param toClass
	 * @return
	 */
	public static SimpleConverter getConverter(Class<?> fromClass, Class<?> toClass) {
		return converters.stream().filter(p -> p.isSupport(fromClass, toClass)).findFirst().orElse(null);
	}
}

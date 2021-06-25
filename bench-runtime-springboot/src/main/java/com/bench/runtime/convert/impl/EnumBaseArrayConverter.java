/**
 * 
 */
package com.bench.runtime.convert.impl;

import com.bench.common.enums.EnumBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;

/**
 * @author cold
 * 
 */
@Service
public class EnumBaseArrayConverter extends AbstractConverter {

	private static final Logger logger = LoggerFactory.getLogger(EnumBaseArrayConverter.class);

	public Class<?>[] supportFrom() {
		// TODO Auto-generated method stub
		return new Class[] { EnumBase[].class, String[].class };
	}

	public Class<?>[] supportTo() {
		// TODO Auto-generated method stub
		return new Class[] { EnumBase[].class };
	}

	@SuppressWarnings("unchecked")
	public <T> T convert(Object from, Object to, Class<T> toClass, String[] filteFromFields, String[] filteToFields, boolean ignoreNull) {
		if (!toClass.isArray()) {
			logger.error("to 不是数组，返回null");
			return null;
		}

		Class<?> componentClass = toClass.getComponentType();

		// TODO Auto-generated method stub

		// 字符串
		if (from instanceof String) {
			Enum enumObj = Enum.valueOf((Class) componentClass, from.toString());
			T[] ret = (T[]) Array.newInstance(componentClass, 1);
			ret[0] = (T) enumObj;
			return (T) ret;
		}
		// 直接等于to类型
		else if (from.getClass().equals(componentClass)) {
			T[] ret = (T[]) Array.newInstance(componentClass, 1);
			ret[0] = (T) from;
			return (T) ret;
		}
		// 如果是数组
		else if (from.getClass().isArray()) {
			Class<?> componentFromClass = from.getClass().getComponentType();
			// 字符串数组
			if (componentFromClass.equals(String.class)) {
				String[] fromStrs = (String[]) from;
				T[] ret = (T[]) Array.newInstance(componentClass, fromStrs.length);
				int i = 0;
				for (String fromStr : fromStrs) {
					ret[i++] = (T) Enum.valueOf((Class) componentClass, fromStr);
				}
				return (T) ret;
			}
			// 如果直接等于枚举类型
			else if (componentFromClass.equals(componentClass)) {
				return (T) from;
			}
		}
		return null;
	}
}

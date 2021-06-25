/**
 * 
 */
package com.bench.runtime.convert.impl;

import com.bench.common.enums.EnumBase;
import org.springframework.stereotype.Service;

/**
 * @author cold
 * 
 */
@Service
public class EnumBaseConverter extends AbstractConverter {

	public Class<?>[] supportFrom() {
		// TODO Auto-generated method stub
		return new Class[] { Enum.class, EnumBase.class, String.class };
	}

	public Class<?>[] supportTo() {
		// TODO Auto-generated method stub
		return new Class[] { Enum.class, EnumBase.class };
	}

	@SuppressWarnings("unchecked")
	public <T> T convert(Object from, Object to, Class<T> toClass, String[] filteFromFields, String[] filteToFields, boolean ignoreNull) {
		// TODO Auto-generated method stub
		if (from instanceof String) {
			return (T) Enum.valueOf((Class) toClass, from.toString());
		} else if (toClass.isAssignableFrom(from.getClass())) {
			return (T) from;
		}
		return null;
	}
}

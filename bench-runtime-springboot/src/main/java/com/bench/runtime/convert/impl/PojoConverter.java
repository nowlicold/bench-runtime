/**
 * 
 */
package com.bench.runtime.convert.impl;

import com.bench.runtime.convert.ConvertHelper;
import org.springframework.stereotype.Service;

/**
 * Pojo类转换
 * 
 * @author cold
 * 
 */
@Service
public class PojoConverter extends AbstractConverter {

	public Class<?>[] supportFrom() {
		// TODO Auto-generated method stub
		return new Class[] { Object.class };
	}

	public Class<?>[] supportTo() {
		// TODO Auto-generated method stub
		return new Class[] { Object.class };
	}

	@SuppressWarnings("unchecked")
	public <T> T convert(Object from, Object to, Class<T> toClass, String[] filteFromFields, String[] filteToFields, boolean ignoreNull) {
		// TODO Auto-generated method stub
		Object dest = to;
		if (dest == null) {
			try {
				dest = toClass.newInstance();
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		ConvertHelper.copyIfIgnoreNullValue(from, to, ignoreNull, filteFromFields, filteToFields);
		return (T) dest;

	}

}

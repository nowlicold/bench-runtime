/**
 * 
 */
package com.bench.runtime.convert;

import com.bench.runtime.convert.field.from.FieldValueFromStringConverter;
import com.bench.runtime.convert.field.to.FieldValueToStringConverter;

import java.util.List;
import java.util.Map;

/**
 * @author cold
 * 
 */
public interface ConverterManager {

	/**
	 * 将Object转换到Map
	 * 
	 * @param object
	 * @return
	 */
	public Map<String, String> convertToMap(Object object);

	/**
	 * 将Object转换到Map
	 * 
	 * @param object
	 * @param converters
	 * @return
	 */
	public Map<String, String> convertToMap(Object object, List<? extends FieldValueToStringConverter> converters);

	/**
	 * 将Map转换到Object
	 * 
	 * @param map
	 * @param object
	 * @return
	 */
	public void convertFromMap(Map<String, String> map, Object object);

	/**
	 * 将Map转换到Object
	 * 
	 * @param map
	 * @param object
	 * @param converters
	 * @return
	 */
	public void convertFromMap(Map<String, String> map, Object object, List<? extends FieldValueFromStringConverter> converters);

	/**
	 * 类型转换，如果无法找到转换器，则返回null
	 * 
	 * @param <T>
	 * @param from
	 * @param to
	 * @param toClass
	 * @param filteFromFields
	 * @param filteTofields
	 * @return
	 */
	public <T> T convert(Object from, T to, Class<T> toClass, String[] filteFromFields, String[] filteTofields);

	/**
	 * 类型转换，如果无法找到转换器，则返回null
	 * 
	 * @param <T>
	 * @param from
	 * @param to
	 * @param toClass
	 * @param filteFromFields
	 * @param filteTofields
	 * @param ignoreNull 是否过滤null
	 * @return
	 */
	public <T> T convert(Object from, T to, Class<T> toClass, String[] filteFromFields, String[] filteTofields, boolean ignoreNull);

	/**
	 * 类型转换，如果无法找到转换器，则返回null
	 * 
	 * @param <T>
	 * @param from
	 * @param to
	 * @param filteFromFields
	 * @param filteTofields
	 * @return
	 */
	public <T> T convert(Object from, T to, String[] filteFromFields, String[] filteTofields);

	/**
	 * 类型转换，如果无法找到转换器，则返回null
	 * 
	 * @param <T>
	 * @param from
	 * @param to
	 * @param filteFromFields
	 * @param filteTofields
	 * @param ignoreNull 是否过滤null
	 * @return
	 */
	public <T> T convert(Object from, T to, String[] filteFromFields, String[] filteTofields, boolean ignoreNull);

	/**
	 * 
	 * @param <T>
	 * @param from
	 * @param to
	 * @return
	 */
	public <T> T convert(Object from, T to);

	/**
	 * 
	 * @param <T>
	 * @param from
	 * @param to
	 * @param ignoreNull 是否过滤null
	 * @return
	 */
	public <T> T convert(Object from, T to, boolean ignoreNull);

	/**
	 * 
	 * @param <T>
	 * @param from
	 * @param toClass
	 * @return
	 */
	public <T> T convert(Object from, Class<T> toClass);

	/**
	 * 
	 * @param <T>
	 * @param from
	 * @param toClass
	 * @param ignoreNull 是否过滤null
	 * @return
	 */
	public <T> T convert(Object from, Class<T> toClass, boolean ignoreNull);

	/**
	 * 
	 * @param <T>
	 * @param from
	 * @param to
	 * @return
	 */
	public <T> T convert(Object from, T to, String[] filterFields);

	/**
	 * 
	 * @param <T>
	 * @param from
	 * @param to
	 * @param ignoreNull 是否过滤null
	 * 
	 * @return
	 */
	public <T> T convert(Object from, T to, String[] filterFields, boolean ignoreNull);

	/**
	 * 
	 * 
	 * @param <T>
	 * @param from
	 * @param to
	 * @return
	 */
	public <T> T convert(Object from, T to, Class<T> toClass);

	/**
	 * 
	 * 
	 * @param <T>
	 * @param from
	 * @param to
	 * @param ignoreNull 是否过滤null
	 * @return
	 */
	public <T> T convert(Object from, T to, Class<T> toClass, boolean ignoreNull);

}

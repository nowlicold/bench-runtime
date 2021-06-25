/**
 * 
 */
package com.bench.runtime.convert;

/**
 * 转换器，将From类型的实例转换成To类型的实例
 * 
 * @author cold
 * 
 */
public interface Converter {

	/**
	 * 支持的来源类类型
	 * 
	 * @return
	 */
	public Class<?>[] supportFrom();

	/**
	 * 支持的目的类类型
	 * 
	 * @return
	 */
	public Class<?>[] supportTo();

	/**
	 * 将from转换成To类型的类
	 * 
	 * @param
	 * @param from
	 * @param to
	 * @param filteFromFields
	 *            TODO
	 * @param filteToFields
	 *            TODO
	 * @return
	 */
	public <T> T convert(Object from, Object to, Class<T> toClass, String[] filteFromFields,
			String[] filteToFields, boolean ignoreNull);

}

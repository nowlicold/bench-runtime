package com.bench.runtime.convert;

import com.bench.common.enums.EnumBase;
import com.bench.lang.base.array.utils.ArrayUtils;
import com.bench.lang.base.bean.utils.PropertyUtils;
import com.bench.lang.base.clasz.field.utils.FieldUtils;
import com.bench.lang.base.clasz.utils.ClassUtils;
import com.bench.lang.base.collection.utils.CollectionUtils;
import com.bench.lang.base.convert.enums.StringSplitter;
import com.bench.lang.base.enums.utils.EnumBaseUtils;
import com.bench.lang.base.list.utils.ListUtils;
import com.bench.lang.base.money.Money;
import com.bench.lang.base.object.utils.ObjectUtils;
import com.bench.lang.base.properties.utils.PropertiesUtils;
import com.bench.lang.base.string.utils.StringUtils;
import com.bench.runtime.convert.simple.SimpleConverter;
import com.bench.runtime.convert.simple.SimpleConverterFactory;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 转换工具类
 * 
 * @author jiang.chenjj
 * 
 */
public class ConvertHelper {

	private static final Class[] NUMBER_PRIMIVE_CLASS = new Class[] {
			//
			double.class,
			//
			int.class,
			//
			float.class,
			//
			short.class,
			//
			long.class };

	/**
	 * @param from
	 * @param to
	 */
	public static void copy(Object from, Object to) {
		copyIfIgnoreNullValue(from, to, true, null, null);
	}

	/**
	 * @param from
	 * @param to
	 */
	public static void copyWithNullValue(Object from, Object to) {
		copyIfIgnoreNullValue(from, to, false, null, null);
	}

	/**
	 * @param from
	 * @param to
	 * @param filteFields
	 */
	public static void copy(Object from, Object to, String[] filteFields) {
		copyIfIgnoreNullValue(from, to, true, filteFields, filteFields);
	}

	/**
	 * @param from
	 * @param to
	 * @param filteFields
	 */
	public static void copyWithNullValue(Object from, Object to, String[] filteFields) {
		copyIfIgnoreNullValue(from, to, false, filteFields, filteFields);
	}

	/**
	 * @param from
	 * @param to
	 * @param filteFromFields
	 * @param filteToFields
	 */
	public static void copy(Object from, Object to, String[] filteFromFields, String[] filteToFields) {
		copyIfIgnoreNullValue(from, to, true, filteFromFields, filteToFields);
	}

	/**
	 * @param from
	 * @param to
	 * @param filteFromFields
	 * @param filteToFields
	 */
	public static void copyWithNullValue(Object from, Object to, String[] filteFromFields, String[] filteToFields) {
		copyIfIgnoreNullValue(from, to, false, filteFromFields, filteToFields);
	}

	/**
	 * @param fromm
	 * @param to
	 * @param ignoreIfValueNull
	 * @param filteFromFields
	 * @param filteToFields
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void copyIfIgnoreNullValue(Object fromm, Object to, boolean ignoreIfValueNull, String[] filteFromFields, String[] filteToFields) {
		filteFromFields = resetFilteFields(filteFromFields);
		filteToFields = resetFilteFields(filteToFields);

		if (to == null || fromm == null)
			return;

		for (Field srcField : FieldUtils.getAllField(fromm.getClass())) {
			// 如果定义了from过滤域，且在过滤域内
			if (filteFromFields != null && ArrayUtils.contains(filteFromFields, srcField.getName())) {
				continue;
			}
			Field toField = null;

			toField = FieldUtils.getFieldSafe(to.getClass(), srcField.getName());
			if (toField == null)
				continue;

			// 如果定义了to过滤域，且在过滤域内
			if (filteToFields != null && ArrayUtils.contains(filteToFields, toField.getName())) {
				continue;
			}

			Object value = null;
			try {
				srcField.setAccessible(true);
				value = srcField.get(fromm);
			} catch (Exception e) {
				// 忽略，不做任何事情
			}
			if (value == null && ignoreIfValueNull)
				continue;

			// 金额拷贝，生成新对象
			if (toField.getType().equals(Money.class) && srcField.getType().equals(Money.class)) {
				try {
					toField.setAccessible(true);
					Money m = new Money(0);
					m.setCent(((Money) value).getCent());
					toField.set(to, m);
				} catch (Exception e) {
					// 忽略，不做任何事情
				}
				continue;
			}
			// 字符串到Map
			else if (toField.getType().equals(Map.class) && srcField.getType().equals(String.class)) {
				try {
					toField.setAccessible(true);
					toField.set(to, PropertiesUtils.restoreMap(ObjectUtils.toString(value)));
				} catch (Exception e) {
					// 忽略，不做任何事情
				}
				continue;
			}
			// Map到字符串
			else if (toField.getType().equals(String.class) && srcField.getType().equals(Map.class)) {
				try {
					toField.setAccessible(true);
					toField.set(to, PropertiesUtils.convert2String((Map) value, false));
				} catch (Exception e) {
					// 忽略，不做任何事情
				}
				continue;
			}
			// 类型一致或者子类
			else if (toField.getType().isAssignableFrom(srcField.getType())) {
				try {
					toField.setAccessible(true);
					toField.set(to, value);
				} catch (Exception e) {
					// 忽略，不做任何事情
				}
				continue;
			}
			// Integer->int
			else if (toField.getType() == int.class && srcField.getType() == Integer.class && value != null) {
				try {
					toField.setAccessible(true);
					toField.set(to, ((Integer) value).intValue());
				} catch (Exception e) {
					// 忽略，不做任何事情
				}
				continue;
			}
			// long->int
			else if (toField.getType() == int.class && srcField.getType() == long.class && value != null) {
				try {
					toField.setAccessible(true);
					toField.set(to, ((Long) value).intValue());
				} catch (Exception e) {
					// 忽略，不做任何事情
				}
				continue;
			} // int->long
			else if (toField.getType() == long.class && srcField.getType() == int.class && value != null) {
				try {
					toField.setAccessible(true);
					toField.set(to, ((Integer) value).longValue());
				} catch (Exception e) {
					// 忽略，不做任何事情
				}
				continue;
			}
			// int->Integer
			else if (toField.getType() == Integer.class && srcField.getType() == int.class) {
				try {
					toField.setAccessible(true);
					toField.set(to, value);
				} catch (Exception e) {
					// 忽略，不做任何事情
				}
				continue;
			}
			// Long->long
			else if (toField.getType() == long.class && srcField.getType() == Long.class && value != null) {
				try {
					toField.setAccessible(true);
					toField.set(to, ((Long) value).longValue());
				} catch (Exception e) {
					// 忽略，不做任何事情
				}
				continue;
			} // long->Long
			else if (toField.getType() == Long.class && srcField.getType() == long.class) {
				try {
					toField.setAccessible(true);
					toField.set(to, value);
				} catch (Exception e) {
					// 忽略，不做任何事情
				}
				continue;
			}
			// Double->double
			else if (toField.getType() == double.class && srcField.getType() == Double.class && value != null) {
				try {
					toField.setAccessible(true);
					toField.set(to, ((Double) value).doubleValue());
				} catch (Exception e) {
					// 忽略，不做任何事情
				}
				continue;
			} // double->Double
			else if (toField.getType() == Double.class && srcField.getType() == double.class) {
				try {
					toField.setAccessible(true);
					toField.set(to, value);
				} catch (Exception e) {
					// 忽略，不做任何事情
				}
				continue;
			}
			// Float->float
			else if (toField.getType() == float.class && srcField.getType() == Float.class && value != null) {
				try {
					toField.setAccessible(true);
					toField.set(to, ((Float) value).floatValue());
				} catch (Exception e) {
					// 忽略，不做任何事情
				}
				continue;
			} // float->Float
			else if (toField.getType() == Float.class && srcField.getType() == float.class) {
				try {
					toField.setAccessible(true);
					toField.set(to, value);
				} catch (Exception e) {
					// 忽略，不做任何事情
				}
				continue;
			}
			// 如果源是枚举类
			else if (Enum.class.isAssignableFrom(srcField.getType()) && srcField.getType().isEnum()) {
				try {
					toField.setAccessible(true);
					// 字符串
					if (toField.getType().equals(String.class)) {
						toField.set(to, ((Enum<?>) value).name());
						continue;
					}
					// 数值
					else if (EnumBase.class.isAssignableFrom(srcField.getType())
							&& (Number.class.isAssignableFrom(toField.getType()) || ArrayUtils.contains(NUMBER_PRIMIVE_CLASS, toField.getType()))) {
						toField.set(to, ((EnumBase) value).value());
						continue;
					}
				} catch (Exception e) {
					// 忽略，不做任何事情
					continue;
				}

			}
			// 如果目的是枚举类
			else if (Enum.class.isAssignableFrom(toField.getType()) && toField.getType().isEnum()) {
				try {

					if (srcField.getType().equals(String.class)) {
						PropertyUtils.setProperty(to, toField.getName(), Enum.valueOf((Class<Enum>) toField.getType(), StringUtils.trim(ObjectUtils.toString(value))));
						continue;
					} else if (EnumBase.class.isAssignableFrom(toField.getType())
							&& (Number.class.isAssignableFrom(srcField.getType()) || ArrayUtils.contains(NUMBER_PRIMIVE_CLASS, srcField.getType()))) {
						PropertyUtils.setProperty(to, toField.getName(), EnumBaseUtils.numberValueOf(toField.getType(), (Number) value));
						continue;
					}
				} catch (Exception e) {
					// 忽略，不做任何事情
					continue;
				}
			}
			// 如果源是数组
			else if (srcField.getType().isArray()) {
				// 如果源是Enum数组
				if (srcField.getType().getComponentType().isEnum()) {
					// 如果目的是String
					if (toField.getType() == String.class) {
						StringBuffer buf = new StringBuffer();
						for (Enum<?> enumObj : (Enum[]) value) {
							buf.append(enumObj.name()).append(",");
						}
						if (buf.length() > 0) {
							buf.deleteCharAt(buf.length() - 1);
						}
						try {
							PropertyUtils.setProperty(to, toField.getName(), buf.toString());
						} catch (Exception e) {
							// 忽略，不做任何事情
						}
						continue;
					} // 如果目的是String数组
					else if (toField.getType().isArray() && toField.getType().getComponentType() == String.class) {
						List<String> stringList = new ArrayList<String>();
						for (Enum<?> enumObj : (Enum[]) value) {
							stringList.add(enumObj.name());
						}
						try {
							PropertyUtils.setProperty(to, toField.getName(), stringList.toArray(new String[stringList.size()]));
						} catch (Exception e) {
							// 忽略，不做任何事情
						}
						continue;
					}
				}
				if (srcField.getType().getComponentType() == int.class || srcField.getType().getComponentType() == long.class
						|| srcField.getType().getComponentType() == double.class) {
					// 如果目的是String
					if (toField.getType() == String.class) {
						String stringValue = StringUtils.join(value, StringUtils.COMMA_SIGN);
						try {
							PropertyUtils.setProperty(to, toField.getName(), stringValue);
						} catch (Exception e) {
							// 忽略，不做任何事情
						}
						continue;
					}
				}
			}

			// 如果源是字符串
			else if (srcField.getType() == String.class) {
				Class<?> componentType = ClassUtils.getComponentType(toField);
				// 如果目的是Enum数组
				if (toField.getType().isArray() && componentType != null && componentType.isEnum()) {
					String[] enumNames = StringUtils.split(ObjectUtils.toString(value), ",");
					Object enumAry = Array.newInstance(toField.getType().getComponentType(), enumNames.length);
					int arrayIndex = 0;
					for (String enumName : enumNames) {
						Array.set(enumAry, arrayIndex++, Enum.valueOf((Class<Enum>) componentType, enumName));
					}
					try {
						PropertyUtils.setProperty(to, toField.getName(), enumAry);
					} catch (Exception e) {
						// 忽略，不做任何事情
					}
					continue;
				}
				// 如果目的是Enum List
				if (toField.getType() == List.class && componentType != null && componentType.isEnum()) {
					String[] enumNames = StringUtils.split(ObjectUtils.toString(value), ",");
					List<Object> enumBaseList = new ArrayList<Object>();
					for (String enumName : enumNames) {
						enumBaseList.add(Enum.valueOf((Class<Enum>) componentType, enumName));
					}
					try {
						PropertyUtils.setProperty(to, toField.getName(), enumBaseList);
					} catch (Exception e) {
						// 忽略，不做任何事情
					}
					continue;
				} // 如果目的是String List 或者数组
				if (componentType != null && componentType == String.class
						&& (java.util.Collection.class.isAssignableFrom(toField.getType()) || toField.getType().isArray())) {
					String[] splits = StringUtils.FULL_SPLITER;
					StringSplitter splitter = toField.getAnnotation(StringSplitter.class);
					if (splitter != null) {
						splits = splitter.values();
					}
					String[] valueArray = StringUtils.split(ObjectUtils.toString(value), splits);
					if (toField.getType().isArray()) {
						try {
							PropertyUtils.setProperty(to, toField.getName(), valueArray);
						} catch (Exception e) {
							// 忽略，不做任何事情
						}
						continue;
					} else if (java.util.Collection.class.isAssignableFrom(toField.getType())) {
						try {
							PropertyUtils.setProperty(to, toField.getName(), ListUtils.toList(valueArray));
						} catch (Exception e) {
							// 忽略，不做任何事情
						}
						continue;
					}
				} // 如果目的是int数组
				if (componentType != null && componentType == int.class && toField.getType().isArray()) {
					String[] splits = StringUtils.FULL_SPLITER;
					StringSplitter splitter = toField.getAnnotation(StringSplitter.class);
					if (splitter != null) {
						splits = splitter.values();
					}
					String[] valueArray = StringUtils.split(ObjectUtils.toString(value), splits);
					int[] intValues = ArrayUtils.convertToInt(valueArray);
					try {
						PropertyUtils.setProperty(to, toField.getName(), intValues);
					} catch (Exception e) {
						// 忽略，不做任何事情
					}
					continue;
				} // 如果目的是Integer List 或者数组
				if (componentType != null && componentType == String.class
						&& (java.util.Collection.class.isAssignableFrom(toField.getType()) || toField.getType().isArray())) {
					String[] splits = StringUtils.FULL_SPLITER;
					StringSplitter splitter = toField.getAnnotation(StringSplitter.class);
					if (splitter != null) {
						splits = splitter.values();
					}
					String[] valueArray = StringUtils.split(ObjectUtils.toString(value), splits);
					if (toField.getType().isArray()) {
						try {
							PropertyUtils.setProperty(to, toField.getName(), ArrayUtils.convertToIntegerObjet(valueArray));
						} catch (Exception e) {
							// 忽略，不做任何事情
						}
						continue;
					} else if (java.util.Collection.class.isAssignableFrom(toField.getType())) {
						try {
							PropertyUtils.setProperty(to, toField.getName(), ArrayUtils.convertToIntegerList(valueArray));
						} catch (Exception e) {
							// 忽略，不做任何事情
						}
						continue;
					}
					continue;
				}
			}
			// 兜底
			SimpleConverter simpleConverter = SimpleConverterFactory.getConverter(srcField.getType(), toField.getType());
			if (simpleConverter != null) {
				toField.setAccessible(true);
				try {
					toField.set(to, simpleConverter.convert(srcField, toField, value));
				} catch (Exception e) {
					// 忽略，不做任何事情
				}
			}
		}

	}

	private static String[] resetFilteFields(String[] fitlerFields) {
		if (fitlerFields != null) {
			List<String> fitlerFieldsList = new ArrayList<String>();
			for (String str : fitlerFields) {
				CollectionUtils.addAll(fitlerFieldsList, StringUtils.split(str, ","));
			}
			fitlerFields = fitlerFieldsList.toArray(new String[fitlerFieldsList.size()]);

		}
		return fitlerFields;
	}
}

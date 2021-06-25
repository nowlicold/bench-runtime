/**
 * 
 */
package com.bench.runtime.convert.impl;


import com.bench.common.enums.error.CommonErrorCodeEnum;
import com.bench.lang.base.clasz.field.utils.FieldUtils;
import com.bench.lang.base.convert.Convert;
import com.bench.lang.base.enums.utils.EnumBaseUtils;
import com.bench.lang.base.object.utils.ObjectUtils;
import com.bench.lang.base.string.utils.StringUtils;
import com.bench.runtime.convert.Converter;
import com.bench.runtime.convert.ConverterManager;
import com.bench.runtime.convert.exception.ConvertException;
import com.bench.runtime.convert.field.from.FieldValueFromStringConverter;
import com.bench.runtime.convert.field.to.FieldValueToStringConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author cold
 * 
 */
@Service
public class ConverterManagerImpl implements ConverterManager, ApplicationContextAware, InitializingBean {

	private static final Logger logger = LoggerFactory.getLogger(ConverterManagerImpl.class);

	private ApplicationContext applicationContext;

	private List<Converter> converters;

	private List<Converter> lastConverters;

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		// TODO Auto-generated method stub
		this.applicationContext = applicationContext;
	}

	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		converters = new ArrayList<Converter>();
		lastConverters = new ArrayList<Converter>();
		Map<?, ?> converterMap = applicationContext.getBeansOfType(Converter.class);

		Converter pojoConverter = null;
		Converter langToLangConverter = null;
		for (Map.Entry<?, ?> entry : converterMap.entrySet()) {
			if (entry.getValue() instanceof LangToLangConverter) {
				langToLangConverter = (Converter) entry.getValue();
			} else if (entry.getValue() instanceof PojoConverter) {
				pojoConverter = (Converter) entry.getValue();
			} else {
				converters.add((Converter) entry.getValue());
			}

		}
		lastConverters.add(0, langToLangConverter);
		lastConverters.add(pojoConverter);
		converters.addAll(lastConverters);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.Bench.mipgw.biz.component.convert.ConverterManager#convert(java.
	 * lang.Object, java.lang.Object, java.lang.String[], java.lang.String[])
	 */
	public <T> T convert(Object from, T to, String[] filteFromFields, String[] filteTofields) {
		// TODO Auto-generated method stub
		if (to == null)
			return null;
		return convert(from, to, (Class<T>) to.getClass(), filteFromFields, filteTofields);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.Bench.mipgw.biz.component.convert.ConverterManager#convert(java.
	 * lang.Object, java.lang.Object)
	 */
	public <T> T convert(Object from, T to) {
		// TODO Auto-generated method stub
		return convert(from, to, false);
	}

	@Override
	public <T> T convert(Object from, T to, boolean ignoreNull) {
		// TODO Auto-generated method stub
		return convert(from, to, (Class<T>) to.getClass(), null, null, ignoreNull);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.Bench.mipgw.biz.component.convert.ConverterManager#convert(java.
	 * lang.Object, java.lang.Class)
	 */
	public <T> T convert(Object from, T to, Class<T> toClass, String[] filteFromFields, String[] filteTofields, boolean ignoreNull) {
		if (from == null)
			return null;

		// 检查to和toClass类型是否一致
		if (to != null) {
			if (!to.getClass().isAssignableFrom(toClass) && !toClass.isAssignableFrom(to.getClass())) {
				throw new ConvertException(CommonErrorCodeEnum.SYSTEM_ERROR, "转换目的类型不一致，to：" + to.getClass() + "，toClass：" + toClass);
			}
		}
		// TODO Auto-generated method stub
		// 获取转换器
		Converter converter = getConverter(from, toClass);
		/**
		 * 如果无法找到转换器，则直接返回
		 */
		if (converter == null) {
			return null;
		} else {
			return converter.convert(from, to, toClass, filteFromFields, filteTofields, ignoreNull);
		}
	}

	public <T> T convert(Object from, T to, Class<T> toClass) {
		// TODO Auto-generated method stub
		return convert(from, to, toClass, false);

	}

	/**
	 * 获取转换器
	 * 
	 * @param <T>
	 * @param from
	 * @param to
	 * @return
	 */
	private <T> Converter getConverter(Object from, Class<T> toClass) {

		Converter converter;
		// 根据类名直接匹配查找
		converter = findConverterByEquals(from, toClass);

		// 根据继承关系查找
		if (converter == null) {
			converter = findConverterByToEqualFromHibercy(from, toClass);
		}
		// 根据继承关系查找
		if (converter == null) {
			converter = findConverterByFromEqualToHibercy(from, toClass);
		}
		// 根据继承关系查找
		if (converter == null) {
			converter = findConverterByHibercy(from, toClass);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("返回转换，fromClass：" + from.getClass() + ",toClass:" + toClass + ",converter：" + converter);
		}
		return converter;
	}

	/**
	 * 根据类名相同查找
	 * 
	 * @param <T>
	 * @param from
	 * @param to
	 */
	private <T> Converter findConverterByEquals(Object from, Class<T> to) {
		for (Converter converter : converters) {
			boolean isSupportFrom = false;
			boolean isSupporTo = false;
			Class<?>[] supportFromClasses = converter.supportFrom();
			for (Class<?> supportFromClass : supportFromClasses) {
				if (supportFromClass.equals(from.getClass())) {
					isSupportFrom = true;
					continue;
				}
			}
			Class<?>[] supportToClasses = converter.supportTo();
			for (Class<?> supportToClass : supportToClasses) {
				if (supportToClass.equals(to)) {
					isSupporTo = true;
				}
			}
			if (isSupportFrom && isSupporTo) {
				return converter;
			}
		}
		return null;
	}

	/**
	 * 根据类名继承关系查找
	 * 
	 * @param <T>
	 * @param from
	 * @param to
	 */
	private <T> Converter findConverterByToEqualFromHibercy(Object from, Class<T> to) {
		for (Converter converter : converters) {
			boolean isSupportFrom = false;
			boolean isSupporTo = false;
			Class<?>[] supportFromClasses = converter.supportFrom();
			for (Class<?> supportFromClass : supportFromClasses) {
				if (supportFromClass.isAssignableFrom(from.getClass())) {
					isSupportFrom = true;
				}
			}
			Class<?>[] supportToClasses = converter.supportTo();
			for (Class<?> supportToClass : supportToClasses) {
				if (to.equals(supportToClass)) {
					isSupporTo = true;
				}
			}
			if (isSupportFrom && isSupporTo) {
				return converter;
			}
		}
		return null;
	}

	/**
	 * 根据类名继承关系查找
	 * 
	 * @param <T>
	 * @param from
	 * @param to
	 */
	private <T> Converter findConverterByFromEqualToHibercy(Object from, Class<T> to) {
		for (Converter converter : converters) {
			boolean isSupportFrom = false;
			boolean isSupporTo = false;
			Class<?>[] supportFromClasses = converter.supportFrom();
			for (Class<?> supportFromClass : supportFromClasses) {
				if (from.getClass().equals(supportFromClass)) {
					isSupportFrom = true;
				}
			}
			Class<?>[] supportToClasses = converter.supportTo();
			for (Class<?> supportToClass : supportToClasses) {
				if (supportToClass.isAssignableFrom(to)) {
					isSupporTo = true;
				}
			}
			if (isSupportFrom && isSupporTo) {
				return converter;
			}
		}
		return null;
	}

	/**
	 * 根据类名继承关系查找
	 * 
	 * @param <T>
	 * @param from
	 * @param to
	 */
	private <T> Converter findConverterByHibercy(Object from, Class<T> to) {
		for (Converter converter : converters) {
			boolean isSupportFrom = false;
			boolean isSupporTo = false;
			Class<?>[] supportFromClasses = converter.supportFrom();
			for (Class<?> supportFromClass : supportFromClasses) {
				if (supportFromClass.isAssignableFrom(from.getClass())) {
					isSupportFrom = true;
				}
			}
			Class<?>[] supportToClasses = converter.supportTo();
			for (Class<?> supportToClass : supportToClasses) {
				if (supportToClass.isAssignableFrom(to)) {
					isSupporTo = true;
				}
			}
			if (isSupportFrom && isSupporTo) {
				return converter;
			}
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.Bench.mipgw.biz.component.convert.ConverterManager#convert(java.
	 * lang.Object, java.lang.Object, java.lang.String[])
	 */
	public <T> T convert(Object from, T to, String[] filterFields) {
		// TODO Auto-generated method stub
		return convert(from, to, filterFields, false);
	}

	@Override
	public <T> T convert(Object from, T to, String[] filterFields, boolean ignoreNull) {
		// TODO Auto-generated method stub
		if (to == null)
			return null;
		return convert(from, to, (Class<T>) to.getClass(), filterFields, filterFields, ignoreNull);
	}

	@Override
	public <T> T convert(Object from, Class<T> toClass, boolean ignoreNull) {
		// TODO Auto-generated method stub
		T obj = null;
		try {
			obj = toClass.newInstance();
		} catch (Exception e) {
			logger.error("实例化类异常", e);
			return null;
		}
		return convert(from, obj, ignoreNull);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bench.platform.convert.ConverterManager#convert(java.lang.Object,
	 * java.lang.Class)
	 */
	public <T> T convert(Object from, Class<T> toClass) {
		// TODO Auto-generated method stub
		return convert(from, toClass, false);
	}

	@Override
	public <T> T convert(Object from, T to, Class<T> toClass, boolean ignoreNull) {
		// TODO Auto-generated method stub
		return convert(from, to, toClass, null, null, ignoreNull);
	}

	@Override
	public <T> T convert(Object from, T to, Class<T> toClass, String[] filteFromFields, String[] filteTofields) {
		// TODO Auto-generated method stub
		return this.convert(from, to, toClass, filteFromFields, filteTofields, false);
	}

	@Override
	public <T> T convert(Object from, T to, String[] filteFromFields, String[] filteTofields, boolean ignoreNull) {
		// TODO Auto-generated method stub
		return convert(from, to, (Class<T>) to.getClass(), filteFromFields, filteTofields, ignoreNull);
	}

	public Map<String, String> convertToMap(Object object) {
		return convertToMap(object, null);
	}

	public Map<String, String> convertToMap(Object object, List<? extends FieldValueToStringConverter> converters) {
		// TODO Auto-generated method stub
		Map<String, String> returnMap = new HashMap<String, String>();
		for (Field srcField : FieldUtils.getAllField(object.getClass())) {
			if (Modifier.isFinal(srcField.getModifiers())) {
				continue;
			}
			if (Modifier.isStatic(srcField.getModifiers())) {
				continue;
			}
			Object value = null;
			try {
				srcField.setAccessible(true);
				value = srcField.get(object);
			} catch (Exception e) {
				// 忽略，不做任何事情
			}

			// 个性化处理
			boolean fieldConverted = false;
			if (converters != null) {
				for (FieldValueToStringConverter converter : converters) {
					if (StringUtils.equals(converter.getFieldName(), srcField.getName())) {
						returnMap.put(srcField.getName(), converter.convert(object, srcField, value));
						fieldConverted = true;
						break;
					}
				}
			}
			if (fieldConverted) {
				break;
			}

			if (value == null) {
				returnMap.put(srcField.getName(), null);
				continue;
			}

			if (srcField.getType().isEnum()) {
				returnMap.put(srcField.getName(), value.toString());
			} else {
				try {
					if (Convert.getDefaultConvertManager().isSupport(srcField.getType())) {
						returnMap.put(srcField.getName(), Convert.asString(value));
					} else {
						returnMap.put(srcField.getName(), ObjectUtils.toString(value));
					}
				} catch (Exception e) {
					returnMap.put(srcField.getName(), ObjectUtils.toString(value));
				}
			}

		}
		return returnMap;
	}

	@Override
	public void convertFromMap(Map<String, String> map, Object object) {
		// TODO Auto-generated method stub
		convertFromMap(map, object, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bench.platform.convert.ConverterManager#convertFromMap(java.util.Map,
	 * java.lang.Object, java.util.List)
	 */
	@Override
	public void convertFromMap(Map<String, String> map, Object object, List<? extends FieldValueFromStringConverter> converters) {
		// TODO Auto-generated method stub
		if (object == null || map == null) {
			return;
		}
		for (Map.Entry<String, String> entry : map.entrySet()) {
			Field field = null;
			try {
				field = FieldUtils.getField(object.getClass(), entry.getKey());
			} catch (NoSuchFieldException e) {
				continue;
			}
			if (field == null) {
				continue;
			}
			if (Modifier.isFinal(field.getModifiers())) {
				continue;
			}
			if (Modifier.isStatic(field.getModifiers())) {
				continue;
			}
			field.setAccessible(true);

			// 个性化处理
			boolean fieldConverted = false;
			if (converters != null) {
				for (FieldValueFromStringConverter converter : converters) {
					if (StringUtils.equals(converter.getFieldName(), field.getName())) {
						try {
							field.set(object, converter.convert(map, entry.getValue(), field));
						} catch (Exception e) {
							logger.error("设置属性异常，object=" + object + ",field=" + field.getName(), e);
						}
						fieldConverted = true;
						break;
					}
				}
			}
			if (fieldConverted) {
				break;
			}

			Object value = null;
			if (field.getType().isEnum()) {
				value = EnumBaseUtils.valueOf((Class<Enum>) field.getType(), entry.getValue());
			} else {
				value = Convert.asType(field.getType(), entry.getValue());
			}
			try {
				field.set(object, value);
			} catch (Exception e) {
				logger.error("设置属性异常，object=" + object + ",field=" + field.getName(), e);
				continue;
			}
		}
		return;
	}
}

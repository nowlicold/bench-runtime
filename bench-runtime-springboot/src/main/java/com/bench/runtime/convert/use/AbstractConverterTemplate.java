package com.bench.runtime.convert.use;

import com.bench.common.enums.error.CommonErrorCodeEnum;
import com.bench.common.exception.BenchRuntimeException;
import com.bench.runtime.convert.ConverterManager;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Array;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 抽象的转换器使用模板
 * 
 * @author cold
 *
 * @version $Id: AbstractConverterUseTemplate .java, v 0.1 2016年6月2日 上午11:35:24 cold Exp $
 */
public abstract class AbstractConverterTemplate<FromObj, ToObj> implements Converter<FromObj, ToObj> {

	@Autowired
	protected ConverterManager converterManager;

	public AbstractConverterTemplate() {
		super();
	}

	protected ToObj newModel(Class<ToObj> toclass) {
		try {
			return toclass.newInstance();
		} catch (Exception e) {
			throw new BenchRuntimeException(CommonErrorCodeEnum.SYSTEM_ERROR, "构造类实例异常,toclass=" + toclass, e);
		}
	}

	/**
	 * 构造一个新的对象
	 * 
	 * @return
	 */
	protected ToObj newModel() {
		ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
		@SuppressWarnings("unchecked")
		Class<ToObj> clasz = (Class<ToObj>) type.getActualTypeArguments()[1];
		return newModel(clasz);
	}

	/**
	 * 子类重载以实现个性化的转换需求
	 * 
	 * @param fromObj
	 * @param toObj
	 */
	protected void convertSpecial(FromObj fromObj, ToObj toObj) {

	}

	/**
	 * 转换1个
	 * 
	 * @param fromObj
	 * @return
	 */
	public ToObj convertOne(FromObj fromObj) {
		if (fromObj == null) {
			return null;
		}
		ToObj toObj = newModel();
		convert(fromObj, toObj);
		return toObj;

	}

	public void convert(FromObj fromObj, ToObj toObj) {
		converterManager.convert(fromObj, toObj);
		convertSpecial(fromObj, toObj);
	}

	/**
	 * 转换多个，集合返回
	 * 
	 * @param fromObjList
	 * @return
	 */
	public List<ToObj> convertMany(Collection<FromObj> fromObjList) {
		List<ToObj> returnList = new ArrayList<ToObj>();
		for (FromObj fromObj : fromObjList) {
			returnList.add(this.convertOne(fromObj));
		}
		return returnList;
	}

	/*
	 * 转换为数组 (non-Javadoc)
	 * 
	 * @see com.bench.runtime.core.convert.use.Converter#convertArray(java.util.Collection)
	 */
	@Override
	public ToObj[] convertArray(Collection<FromObj> fromObjList) {
		// TODO Auto-generated method stub
		ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
		@SuppressWarnings("unchecked")
		Class<ToObj> clasz = (Class<ToObj>) type.getActualTypeArguments()[1];
		ToObj[] returnArray = (ToObj[]) Array.newInstance(clasz, fromObjList.size());

		int index = 0;
		for (FromObj fromObj : fromObjList) {
			returnArray[index++] = this.convertOne(fromObj);
		}
		return returnArray;
	}

}

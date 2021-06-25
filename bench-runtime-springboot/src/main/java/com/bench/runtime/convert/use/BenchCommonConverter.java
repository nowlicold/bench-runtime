/**
 * BenchCode.com Inc.
 * Copyright (c) 2005-2009 All Rights Reserved.
 */
package com.bench.runtime.convert.use;

import org.springframework.stereotype.Component;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Bench通用Converter，对于一些不需要特殊转换的Convert，可以直接使用这个
 * 
 * @author cold
 *
 * @version $Id: BenchCommonConverter.java, v 0.1 2019年11月15日 下午7:44:27 cold Exp $
 */
@Component
public class BenchCommonConverter<FromObj, ToObj> extends AbstractConverterTemplate<FromObj, ToObj> {

	/**
	 * 泛型使用时，无法找到正确的ToClass，这里需要作为参数传入
	 * 
	 * @param fromObj
	 * @param toClass
	 * @return
	 */
	public ToObj convertOne(FromObj fromObj, Class<?> toClass) {
		// TODO Auto-generated method stub
		if (fromObj == null) {
			return null;
		}
		ToObj toObj = newModel((Class<ToObj>) toClass);
		convert(fromObj, toObj);
		return toObj;
	}

	/**
	 * 泛型使用时，无法找到正确的ToClass，这里需要作为参数传入
	 * 
	 * @param fromObjList
	 * @param toClass
	 * @return
	 */
	public List<ToObj> convertMany(Collection<FromObj> fromObjList, Class<?> toClass) {
		// TODO Auto-generated method stub
		List<ToObj> returnList = new ArrayList<ToObj>();
		for (FromObj fromObj : fromObjList) {
			returnList.add(this.convertOne(fromObj, toClass));
		}
		return returnList;
	}

	/**
	 * 泛型使用时，无法找到正确的ToClass，这里需要作为参数传入
	 * 
	 * @param fromObjList
	 * @param toClass
	 * @return
	 */
	public ToObj[] convertArray(Collection<FromObj> fromObjList, Class<?> toClass) {
		// TODO Auto-generated method stub
		ToObj[] returnArray = (ToObj[]) Array.newInstance(toClass, fromObjList.size());
		int index = 0;
		for (FromObj fromObj : fromObjList) {
			returnArray[index++] = this.convertOne(fromObj, toClass);
		}
		return returnArray;
	}
}

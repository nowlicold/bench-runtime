package com.bench.runtime.convert.use;

import java.util.Collection;
import java.util.List;

/**
 * 转换器
 * 
 * @author cold
 *
 * @version $Id: Converter .java, v 0.1 2016年6月2日 上午11:35:24 cold Exp $
 */
public interface Converter<FromObj, ToObj> {

	/**
	 * 转换1个
	 * 
	 * @param fromObj
	 * @return
	 */
	public ToObj convertOne(FromObj fromObj);

	/**
	 * 转换多个，集合返回
	 * 
	 * @param fromObjList
	 * @return
	 */
	public List<ToObj> convertMany(Collection<FromObj> fromObjList);

	/**
	 * 转换多个，数组返回
	 * 
	 * @param fromObjList
	 * @return
	 */
	public ToObj[] convertArray(Collection<FromObj> fromObjList);


}

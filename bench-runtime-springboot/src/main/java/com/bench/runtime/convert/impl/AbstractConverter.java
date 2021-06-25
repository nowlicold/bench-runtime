/**
 * 
 */
package com.bench.runtime.convert.impl;

import com.bench.runtime.convert.Converter;
import com.bench.runtime.convert.ConverterManager;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 抽象转换器
 * 
 * @author cold
 * 
 */
public abstract class AbstractConverter implements Converter {
	@Autowired
	protected ConverterManager converterManager;

	public void setConverterManager(ConverterManager converterManager) {
		this.converterManager = converterManager;
	}

}

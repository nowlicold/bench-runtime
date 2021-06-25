package com.bench.runtime.convert.formatter.string;

import java.lang.reflect.Field;

/**
 * 字符粗格式化工具
 * 
 * @author cold
 *
 * @version $Id: Formatter.java, v 0.1 2015年9月23日 下午4:27:37 cold Exp $
 */
public interface StringFormatter {

	public String format(Field field, Object value);

}

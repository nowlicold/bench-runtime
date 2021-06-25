package com.bench.runtime.convert.formatter.string.annotations;


import com.bench.runtime.convert.formatter.string.StringFormatter;

import java.lang.annotation.*;

/**
 * 字符串格式化类
 * 
 * @author cold
 *
 * @version $Id: StringFormatterClass.java, v 0.1 2015年9月30日 下午3:10:06
 *          Administrator Exp $
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
public @interface StringFormatterAnno {

	/**
	 * 格式化类
	 * 
	 * @return
	 */
	Class<? extends StringFormatter> formatterClass();
}

package com.bench.runtime.convert.formatter.map.annotations;


import com.bench.lang.base.string.utils.StringUtils;

import java.lang.annotation.*;

/**
 * 字符串连接
 * 
 * @author cold
 *
 * @version $Id: StringJoiner.java, v 0.1 2015年9月30日 下午2:58:45 Administrator Exp $
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
public @interface StringJoiner {
	public String value() default StringUtils.COMMA_SIGN;
}

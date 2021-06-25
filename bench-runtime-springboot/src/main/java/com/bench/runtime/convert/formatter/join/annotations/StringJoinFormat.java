package com.bench.runtime.convert.formatter.join.annotations;


import com.bench.lang.base.string.utils.StringUtils;

import java.lang.annotation.*;

/**
 * 字符串连接格式化
 * 
 * @author cold
 *
 * @version $Id: StringJoinFormatter.java, v 0.1 2015年9月23日 下午4:12:37 cold
 *          Exp $
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
public @interface StringJoinFormat {
	// 连接符
	String joiner() default StringUtils.DOLLAR_SIGN;

	int maxLength() default -1;
}

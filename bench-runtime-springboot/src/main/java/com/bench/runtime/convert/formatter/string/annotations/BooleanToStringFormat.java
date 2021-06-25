package com.bench.runtime.convert.formatter.string.annotations;

import java.lang.annotation.*;

/**
 * boolean到字符串
 * 
 * @author cold
 *
 * @version $Id: BooleanToStringFormatter.java, v 0.1 2015年9月23日 下午5:22:09
 *          cold Exp $
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
public @interface BooleanToStringFormat {

	String trueString() default "1";

	String falaseString() default "0";

}

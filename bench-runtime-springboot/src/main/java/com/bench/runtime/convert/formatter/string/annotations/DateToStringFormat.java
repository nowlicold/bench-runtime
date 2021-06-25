package com.bench.runtime.convert.formatter.string.annotations;


import com.bench.lang.base.date.utils.DateUtils;

import java.lang.annotation.*;

/**
 * 日期到支付串格式化工具
 * 
 * @author cold
 *
 * @version $Id: DateToStringFormat.java, v 0.1 2015年9月23日 下午4:30:33 cold Exp
 *          $
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
public @interface DateToStringFormat {

	String value() default DateUtils.newFormat;

}

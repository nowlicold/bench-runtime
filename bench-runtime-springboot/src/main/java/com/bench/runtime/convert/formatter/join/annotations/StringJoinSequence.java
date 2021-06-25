package com.bench.runtime.convert.formatter.join.annotations;

import java.lang.annotation.*;

/**
 * 格式化顺序号
 * 
 * @author cold
 *
 * @version $Id: ForamtSequence.java, v 0.1 2015年9月22日 上午11:22:09 cold Exp $
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
public @interface StringJoinSequence {

	/**
	 * 连接顺序号
	 * 
	 * @return
	 */
	public int value();
}

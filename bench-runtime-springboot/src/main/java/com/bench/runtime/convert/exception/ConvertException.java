/**
 * 
 */
package com.bench.runtime.convert.exception;

import com.bench.common.enums.error.ErrorEnum;
import com.bench.common.error.ErrorCode;
import com.bench.common.exception.BenchException;
import com.bench.common.exception.BenchRuntimeException;

/**
 * <p>
 * 
 * </p>
 * 
 * @author cold
 * @version $Id: ConvertException.java,v 0.1 2009-8-26 下午01:27:30 cold Exp $
 */

public class ConvertException extends BenchRuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6903140287376593895L;

	public ConvertException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public <E extends BenchException> ConvertException(E exception) {
		super(exception);
		// TODO Auto-generated constructor stub
	}

	public <E extends BenchRuntimeException> ConvertException(E exception) {
		super(exception);
		// TODO Auto-generated constructor stub
	}

	public ConvertException(ErrorCode errorCode, String message, Throwable throwable) {
		super(errorCode, message, throwable);
		// TODO Auto-generated constructor stub
	}

	public ConvertException(ErrorCode errorCode, String message) {
		super(errorCode, message);
		// TODO Auto-generated constructor stub
	}

	public ConvertException(ErrorCode errorCode, Throwable cause) {
		super(errorCode, cause);
		// TODO Auto-generated constructor stub
	}

	public ConvertException(ErrorCode errorCode) {
		super(errorCode);
		// TODO Auto-generated constructor stub
	}

	public ConvertException(ErrorEnum errorEnum, String message, Throwable throwable) {
		super(errorEnum, message, throwable);
		// TODO Auto-generated constructor stub
	}

	public ConvertException(ErrorEnum errorEnum, String message) {
		super(errorEnum, message);
		// TODO Auto-generated constructor stub
	}

	public ConvertException(ErrorEnum errorEnum, Throwable cause) {
		super(errorEnum, cause);
		// TODO Auto-generated constructor stub
	}

	public ConvertException(ErrorEnum errorEnum) {
		super(errorEnum);
		// TODO Auto-generated constructor stub
	}

	public <E extends BenchException> ConvertException(String message, E exception) {
		super(message, exception);
		// TODO Auto-generated constructor stub
	}

	public <E extends BenchRuntimeException> ConvertException(String message, E exception) {
		super(message, exception);
		// TODO Auto-generated constructor stub
	}

}

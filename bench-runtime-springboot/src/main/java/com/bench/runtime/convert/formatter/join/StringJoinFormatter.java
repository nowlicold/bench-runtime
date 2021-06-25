package com.bench.runtime.convert.formatter.join;



import com.bench.common.enums.error.CommonErrorCodeEnum;
import com.bench.common.exception.BenchRuntimeException;
import com.bench.lang.base.clasz.field.utils.FieldUtils;
import com.bench.lang.base.list.utils.ListUtils;
import com.bench.lang.base.object.utils.ObjectUtils;
import com.bench.lang.base.string.utils.StringUtils;
import com.bench.runtime.convert.formatter.join.annotations.StringJoinFormat;
import com.bench.runtime.convert.formatter.join.annotations.StringJoinSequence;
import com.bench.runtime.convert.formatter.string.StringFormatter;
import com.bench.runtime.convert.formatter.string.StringFormatterFactory;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 基于字符串连接的Formatter
 * 
 * @author cold
 *
 * @version $Id: StringJoinFormatter.java, v 0.1 2015年9月22日 上午11:20:13 cold
 *          Exp $
 */
public class StringJoinFormatter {

	/**
	 * 格式化
	 * 
	 * @param object
	 * @return
	 */
	public static String format(Object object) {
		if (object == null) {
			return StringUtils.EMPTY;
		}
		StringJoinFormat format = object.getClass().getAnnotation(StringJoinFormat.class);
		String joiner = StringUtils.defaultString(format.joiner(), StringUtils.DOLLAR_SIGN);
		Set<Field> fields = FieldUtils.getAllField(object.getClass());

		int maxFormatSequence = 0;
		for (Field field : fields) {
			StringJoinSequence formatSequence = field.getAnnotation(StringJoinSequence.class);
			if (formatSequence != null && (maxFormatSequence == 0 || maxFormatSequence < formatSequence.value())) {
				maxFormatSequence = formatSequence.value();
			}
		}
		List<String> valueList = ListUtils.createList(maxFormatSequence + 1, StringUtils.EMPTY_STRING);
		List<Integer> sequenceList = new ArrayList<Integer>();
		for (Field field : fields) {
			StringJoinSequence formatSequence = field.getAnnotation(StringJoinSequence.class);
			if (formatSequence == null) {
				continue;
			}
			if (sequenceList.contains(formatSequence.value())) {
				throw new BenchRuntimeException(CommonErrorCodeEnum.SYSTEM_ERROR.errorCode(),
						"ForamtSequence重复，sequence=" + formatSequence.value() + ",object=" + object);
			}
			Object value = FieldUtils.getFieldValue(field, object);
			StringFormatter stringFormatter = StringFormatterFactory.getFirstFormatter(field);
			if (stringFormatter == null) {
				valueList.set(formatSequence.value(), ObjectUtils.toString(value));
			} else {
				valueList.set(formatSequence.value(), stringFormatter.format(field, value));
			}
			sequenceList.add(formatSequence.value());
			if (maxFormatSequence == 0 || maxFormatSequence < formatSequence.value()) {
				maxFormatSequence = formatSequence.value();
			}
		}
		valueList = valueList.subList(0, maxFormatSequence + 1);
		return StringUtils.join(valueList, joiner);
	}
}

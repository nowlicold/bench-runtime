package com.bench.runtime.api.mapping;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;

import com.bench.runtime.api.filter.BodyReaderHttpServletRequestWrapper;
import com.bench.runtime.util.JsonUtil;

/**
 * @author cold
 * @date 2020/3/6
 */
public class ParamMappingUtil {

    private static final Map<String, Map<String, String>> PARAM_MAPPING_MAP = new HashMap<>();

    public static void addMappingRelation(String url, String sourceParamName,
                                          String targetParamName) {
        Map<String, String> paramMap = PARAM_MAPPING_MAP.computeIfAbsent(url, k -> new HashMap<>(8));
        paramMap.put(sourceParamName, targetParamName);
    }

    private static Map<String, String> getTargetParamName(String url) {
        return PARAM_MAPPING_MAP.get(url);
    }

    public static BodyReaderHttpServletRequestWrapper mappingParam(HttpServletRequest request,
                                                                   JSONObject requestBody) {
        String url = request.getRequestURI();
        Map<String, String> paramMappingMap = getTargetParamName(url);
        if (paramMappingMap != null) {
            paramMappingMap.forEach(
                (sourceField, targetField) -> JsonUtil.overrideField(requestBody, sourceField, targetField));
        }
        return new BodyReaderHttpServletRequestWrapper(request, requestBody);
    }

}

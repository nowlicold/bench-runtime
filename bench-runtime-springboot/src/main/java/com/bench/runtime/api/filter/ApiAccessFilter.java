package com.bench.runtime.api.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

import com.bench.runtime.api.mapping.ParamMappingUtil;
import com.bench.runtime.api.properties.HttpApiProperties;
import com.bench.common.model.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * @author cold
 * @date 2019-05-15
 */
@Slf4j
@WebFilter(filterName = "ApiAccessFilter", urlPatterns = {"/*"})
public class ApiAccessFilter implements Filter {

    private static final String PUB_URL = "public";

    private static final String PRIVATE_URL = "private";

    private static final String CLIENT = "clientIp";

    private static final String URL = "appUrl";

    private static final String METHOD = "method";

    private static final String BODY = "body";

    private static final String DURATION = "duration";

    private static final String STAGE = "stage";

    private static final String JSON_RESULT = "jsonResult";

    private HttpApiProperties httpApiProperties;

    public ApiAccessFilter(HttpApiProperties httpApiProperties) {
        this.httpApiProperties = httpApiProperties;
    }

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        long start = System.currentTimeMillis();

        // 如果未开启日志拦截,直接跳过
        if (!httpApiProperties.getLogEnable()) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        // 非需要拦截的正则url,直接返回
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        String url = request.getRequestURI();
        String[] urlPattern = url.split("/");
        if (urlPattern.length <= 1) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        // 非public,非private接口,不需要拦截
        String accessLevel = urlPattern[1];
        boolean needWriteLog = (urlPattern.length > 2) && (PUB_URL.equalsIgnoreCase(accessLevel) || PRIVATE_URL
            .equalsIgnoreCase(accessLevel));
        if (!needWriteLog) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        BodyReaderHttpServletRequestWrapper wrapper = new BodyReaderHttpServletRequestWrapper(request);
        BodyReaderHttpServletResponseWrapper responseWrapper = new BodyReaderHttpServletResponseWrapper(response);
        JSONObject requestBody = wrapper.getJsonBody();

        // 参数映射
        BodyReaderHttpServletRequestWrapper requestAfterMapping = ParamMappingUtil
            .mappingParam(request, requestBody);
        // 读取response body
        filterChain.doFilter(requestAfterMapping, responseWrapper);

        JSONObject requestObject = new JSONObject();
        requestObject.put(CLIENT, getIp(request));
        requestObject.put(URL, request.getRequestURI());
        requestObject.put(METHOD, request.getMethod());
        requestObject.put(BODY, requestBody);
        requestObject.put(STAGE, "ApiAccessStart");
        log.info("{}", requestObject);

        JsonResult jsonResult = responseWrapper.convertJsonResult();
        JSONObject responseObject = new JSONObject();
        responseObject.put(URL, request.getRequestURI());
        responseObject.put(DURATION, System.currentTimeMillis() - start);
        responseObject.put(STAGE, "ApiAccessEnd");
        responseObject.put(JSON_RESULT, jsonResult);
        log.info("{}", responseObject);

    }

    @Override
    public void destroy() {

    }

    /**
     * 获取IP地址
     *
     * @param request 请求
     * @return request发起客户端的IP地址
     */
    private String getIp(HttpServletRequest request) {
        if (request == null) {
            return "0.0.0.0";
        }

        String xIp = request.getHeader("X-Real-IP");
        String xFor = request.getHeader("X-Forwarded-For");
        String unknownIp = "unknown";
        if (StringUtils.isNotEmpty(xFor) && !unknownIp.equalsIgnoreCase(xFor)) {
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = xFor.indexOf(",");
            if (index != -1) {
                return xFor.substring(0, index);
            } else {
                return xFor;
            }
        }
        xFor = xIp;
        if (StringUtils.isNotEmpty(xFor) && !unknownIp.equalsIgnoreCase(xFor)) {
            return xFor;
        }
        if (StringUtils.isBlank(xFor) || unknownIp.equalsIgnoreCase(xFor)) {
            xFor = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isBlank(xFor) || unknownIp.equalsIgnoreCase(xFor)) {
            xFor = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isBlank(xFor) || unknownIp.equalsIgnoreCase(xFor)) {
            xFor = request.getHeader("HTTP_CLIENT_IP");
        }
        if (StringUtils.isBlank(xFor) || unknownIp.equalsIgnoreCase(xFor)) {
            xFor = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (StringUtils.isBlank(xFor) || unknownIp.equalsIgnoreCase(xFor)) {
            xFor = request.getRemoteAddr();
        }
        return xFor;
    }

    public static void main(String[] args) {
        System.out.println("test");
    }

}


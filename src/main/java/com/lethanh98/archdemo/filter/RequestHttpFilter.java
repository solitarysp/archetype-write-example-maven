package com.lethanh98.archdemo.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lethanh98.archdemo.config.constant.ConstantPropertiesConfig;
import com.lethanh98.archdemo.filter.cachehttp.CachedBodyHttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.CoyoteOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Configuration
@Slf4j
@Order(1)
@ConditionalOnProperty(value = ConstantPropertiesConfig.APP_LOG_REQUEST_HTTP, havingValue = "true")
public class RequestHttpFilter extends OncePerRequestFilter {
    @Autowired
    ObjectMapper objectMapper;
    Map<String, String> replaceCharsError = new HashMap<>();

    public RequestHttpFilter() {
        replaceCharsError.put("\u0000", "");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain chain) {
        StringBuilder str = new StringBuilder();

        try {
            ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);
            CachedBodyHttpServletRequest cachedBodyHttpServletRequest =
                    new CachedBodyHttpServletRequest(new ContentCachingRequestWrapper(request));
            // request
            str.append("\n Request = ").append(" \n")
                    .append("Request to : ").append(getFullURL(cachedBodyHttpServletRequest)).append(" \n")
                    .append("Method     : ").append(cachedBodyHttpServletRequest.getMethod()).append(" \n")
                    .append("Header     : ").append(cachedBodyHttpServletRequest.getHeaders()).append(" \n")
                    .append("Body       : ").append(replaceChars(new String(cachedBodyHttpServletRequest.getCachedBody(), StandardCharsets.UTF_8))).append(" \n")
                    .append(" \n")
            ;
            chain.doFilter(cachedBodyHttpServletRequest, responseWrapper);
            responseWrapper.copyBodyToResponse();
            // request
            str.append("\n Response = ").append(" \n")
                    .append("Status code  : {}").append(responseWrapper.getStatus()).append(" \n")
                    .append("Header     : ").append(getHeaders(responseWrapper)).append(" \n");
            str.append("Body       : ").append(getBodyResponse(responseWrapper)).append(" \n");
            str.append(" \n");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            log.info(str.toString());
        }

    }

    public String getBodyResponse(ContentCachingResponseWrapper responseWrapper) throws NoSuchFieldException, IOException, IllegalAccessException {
        // get response
        // lấy về ByteBuffer response
        Field field = CoyoteOutputStream.class.getDeclaredField("ob");
        field.setAccessible(true);
        Field field2 = field.get(responseWrapper.getResponse().getOutputStream()).getClass().getDeclaredField("bb");
        field2.setAccessible(true);
        ByteBuffer byteBuffer = (ByteBuffer) field2.get(field.get(responseWrapper.getResponse().getOutputStream()));


        Field fieldBytesWritten = field.get(responseWrapper.getResponse().getOutputStream()).getClass().getDeclaredField("bytesWritten");
        fieldBytesWritten.setAccessible(true);
        Long bytesWritten = (Long) fieldBytesWritten.get(field.get(responseWrapper.getResponse().getOutputStream()));
        if (byteBuffer.array().length < bytesWritten) {
            bytesWritten = Long.valueOf(byteBuffer.array().length);
        }
        return replaceChars(new String(byteBuffer.array(), 0, Math.toIntExact(bytesWritten), StandardCharsets.UTF_8));
    }

    public Map<String, String> getHeaders(HttpServletResponse response) {
        Map<String, String> map = new HashMap();
        Collection<String> headersName = response.getHeaderNames();
        for (String s : headersName) {
            map.put(s, response.getHeader(s));
        }

        return map;
    }

    public String getFullURL(HttpServletRequest request) {
        StringBuilder requestURL = new StringBuilder(request.getRequestURL().toString());
        String queryString = request.getQueryString();

        if (queryString == null) {
            return requestURL.toString();
        } else {
            return requestURL.append('?').append(queryString).toString();
        }
    }

    public String replaceChars(String str) {
        for (Map.Entry<String, String> entry : replaceCharsError.entrySet()) {
            str = str.replace(entry.getKey(), entry.getValue());
        }
        return str;
    }

}

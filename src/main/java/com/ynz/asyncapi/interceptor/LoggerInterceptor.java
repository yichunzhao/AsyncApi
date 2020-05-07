package com.ynz.asyncapi.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

/**
 * the code here demonstrates how an async-request works with the interceptor.
 * we may see:
 * 1) when an async-request is handled, the interceptor handlers are quited except the pre-handle.
 * 2) after the async task is carried out, all interceptors are triggered, pre-handle, post-handle, and after-handle.
 */

@Slf4j
public class LoggerInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("[preHandle][" + request + "]" + "[" + request.getMethod() + "]" + request.getRequestURI() + "  Source IP:  " + getRemoteAddress(request));

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("[postHandle][" + request + "]" + "[" + request.getMethod() + "]" + request.getRequestURI());
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("[afterCompletion][ " + response + "]");
        Optional.ofNullable(ex).ifPresent(e -> log.error("Exception: " + ex));
    }

    private String getRemoteAddress(HttpServletRequest request) {
        String ipFromHeader = request.getHeader("X-FORWARDED-FOR");
        if (ipFromHeader != null && !ipFromHeader.isEmpty()) {
            log.debug("ip from proxy - X-FORWARDED-FOR :" + ipFromHeader);

            return ipFromHeader;
        }
        return request.getRemoteAddr();
    }
}

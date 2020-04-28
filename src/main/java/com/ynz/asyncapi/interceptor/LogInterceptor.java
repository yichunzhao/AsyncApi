package com.ynz.asyncapi.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.Duration;
import java.time.Instant;

/**
 * the code here demonstrates how an async-request works with the interceptor.
 * we may see:
 * 1) when an async-request is handled, the interceptor handlers are quited except the pre-handle.
 * 2) after the async task is carried out, all interceptors are triggered, pre-handle, post-handle, and after-handle.
 */

@Slf4j
public class LogInterceptor extends HandlerInterceptorAdapter {
    private int sequence = 1;

    @Autowired
    private HttpSession session;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info(" in preHandle");

        log.info("request session id: " + request.getRequestedSessionId());

        log.info("session is: " + session.getId());

        session.setAttribute("pre-moment:" + sequence, Instant.now());
        sequence++;

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info(" in postHandle");

        log.info("response locale : " + response.getLocale());

        log.info("session is: " + session.getId());


        session.setAttribute("post-moment:", Instant.now());
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info(" in after Completion");
        log.info("session is: " + session.getId());

        session.setAttribute("completed-moment:", Instant.now());
        Instant start = (Instant) session.getAttribute("pre-moment:1");
        Instant post = (Instant) session.getAttribute("post-moment:");
        Instant completed = (Instant) session.getAttribute("completed-moment:");
        log.info("pre-moment-session: " + start);
        log.info("post-moment-session:" + post);
        log.info("completed-moment-session:" + completed);

        log.info("time-elapsed: " + Duration.between(start, completed));


    }
}

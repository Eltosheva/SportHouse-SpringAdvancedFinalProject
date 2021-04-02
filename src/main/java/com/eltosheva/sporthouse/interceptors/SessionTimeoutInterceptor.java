package com.eltosheva.sporthouse.interceptors;

import com.eltosheva.sporthouse.utils.AppConstants;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class SessionTimeoutInterceptor implements HandlerInterceptor {
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, ModelAndView modelAndView) throws Exception {
        if (UserInterceptor.isLoggedIn()) {
            if ((System.currentTimeMillis() - request.getSession().getLastAccessedTime())
                    > AppConstants.MAX_INACTIVE_SESSION_TIME) {
                request.getSession().invalidate();
                response.sendRedirect(AppConstants.SECURITY_INVALID_SESSION_URL);
            }
        }
    }
}

package com.eltosheva.sporthouse.config;

import com.eltosheva.sporthouse.interceptors.SessionTimeoutInterceptor;
import com.eltosheva.sporthouse.interceptors.UserInterceptor;
import com.eltosheva.sporthouse.utils.AppConstants;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class AppInterceptorConfig implements WebMvcConfigurer {

    private final UserInterceptor userInterceptor;
    private final SessionTimeoutInterceptor sessionTimeoutInterceptor;

    public AppInterceptorConfig(UserInterceptor userInterceptor, SessionTimeoutInterceptor sessionTimeoutInterceptor) {
        this.userInterceptor = userInterceptor;
        this.sessionTimeoutInterceptor = sessionTimeoutInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userInterceptor)
                .excludePathPatterns(AppConstants.AUTH_INTERCEPTOR_PATH_EXCLUSIONS);
        registry.addInterceptor(sessionTimeoutInterceptor)
                .excludePathPatterns(AppConstants.AUTH_INTERCEPTOR_PATH_EXCLUSIONS);
    }
}

package com.eltosheva.sporthouse.config;

import com.eltosheva.sporthouse.security.AuthUserDetailsService;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.eltosheva.sporthouse.utils.AppConstants.*;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final AuthUserDetailsService authUserDetailsService;
    private final PasswordEncoder passwordEncoder;

    public SecurityConfig(AuthUserDetailsService authUserDetailsService, PasswordEncoder passwordEncoder) {
        this.authUserDetailsService = authUserDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .userDetailsService(authUserDetailsService)
            .passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                .antMatchers(PUBLIC_PAGES_MATCHER).permitAll()
                .antMatchers(SECURE_PAGE_PATTERN).authenticated()
            .and()
                .formLogin()
                    .loginPage(SECURITY_LOGIN_URL)
                    .defaultSuccessUrl(APP_USER_HOME_PAGE)
                    .failureForwardUrl(FAIL_TO_LOGIN_PAGE)
                    .usernameParameter(SPRING_SECURITY_LOGIN_EMAIL_KEY)
                    .passwordParameter(SPRING_SECURITY_LOGIN_PASSWORD_KEY)
            .and()
                .logout()
                    .logoutUrl(SECURITY_LOGOUT_URL)
                    .logoutSuccessUrl(APP_BASE_URL)
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID");
    }
}

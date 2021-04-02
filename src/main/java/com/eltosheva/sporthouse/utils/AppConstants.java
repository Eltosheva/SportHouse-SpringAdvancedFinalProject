package com.eltosheva.sporthouse.utils;

public class AppConstants {

    public static final String[] PUBLIC_PAGES_MATCHER = {
        "/",
        "/index",
        "/login",
        "/halls",
        "/team",
        "/sports",
        "/user/register",
        "/coach/register"
    };

    public static final String[] AUTH_INTERCEPTOR_PATH_EXCLUSIONS = {
      "/css/**",
      "/images/**",
      "/js/**",
      "/login",
      "/api/**"
    };

    public static final long MAX_INACTIVE_SESSION_TIME = 500 * 1000;

    public static final String SECURE_PAGE_PATTERN = "/**";

    public static final String SECURITY_LOGIN_URL = "/login";
    public static final String SECURITY_INVALID_SESSION_URL = "/login?invalid-session=true";
    public static final String SECURITY_LOGOUT_URL = "/logout";
    public static final String APP_BASE_URL = "/";
    public static final String APP_USER_HOME_PAGE = "/home";
    public static final String FAIL_TO_LOGIN_PAGE = "/loginError";

    public static final String SPRING_SECURITY_LOGIN_EMAIL_KEY = "email";
    public static final String SPRING_SECURITY_LOGIN_PASSWORD_KEY = "password";

    public static final String HOST = "smtp.gmail.com";
    public static final int PORT = 587;
    public static final String PROTOCOL = "smtp";
    public static final String USERNAME = "email";
    public static final String PASSWORD = "password";
    public static final String EMAIL_SENDING_FROM = "sales@sporthouse.com";

    public static final String EMAIL_TEMPLATE_ENCODING = "UTF-8";

    public static final String EMAIL_CONFIRM_ORDER_TEMPLATE = "confirmation_order_email.html";
}

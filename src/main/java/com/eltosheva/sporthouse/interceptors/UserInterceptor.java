package com.eltosheva.sporthouse.interceptors;

import com.eltosheva.sporthouse.repositories.ShoppingCartRepository;
import com.eltosheva.sporthouse.repositories.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Component
public class UserInterceptor implements HandlerInterceptor {

    private final UserRepository userRepository;
    private final ShoppingCartRepository shoppingCartRepository;

    public UserInterceptor(UserRepository userRepository, ShoppingCartRepository shoppingCartRepository) {
        this.userRepository = userRepository;
        this.shoppingCartRepository = shoppingCartRepository;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler,
                           ModelAndView modelAndView) throws Exception {
        if (modelAndView != null && isLoggedIn()) {
            addToModelUserDetails(modelAndView);
        }
    }

    private void addToModelUserDetails(ModelAndView modelAndView) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Map<String, String> userDetails = userRepository.getUserData(email);
        modelAndView.addAllObjects(userDetails);
        modelAndView.addObject("shoppingCartUserCount",
                shoppingCartRepository.countShoppingCartByUser_Email(email));
    }

    public static boolean isLoggedIn() {
        try {
            return !SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser");
        }catch (Exception e) {
            return false;
        }
    }
}

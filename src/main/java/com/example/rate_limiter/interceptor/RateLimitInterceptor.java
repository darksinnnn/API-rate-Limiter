package com.example.rate_limiter.interceptor;

import com.example.rate_limiter.service.RateLimiterService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class RateLimitInterceptor implements HandlerInterceptor {

    @Autowired
    private RateLimiterService rateLimiterService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1. Get the User ID from the Header (Simulated login)
        // In Postman, you will add a header: "X-User-ID: 123"
        String userId = request.getHeader("X-User-ID");

        if (userId == null || userId.isEmpty()) {
            response.setStatus(400);
            response.getWriter().write("Missing Header: X-User-ID");
            return false; // Stop here
        }

        // 2. Check if allowed
        if (!rateLimiterService.isAllowed(userId)) {
            response.setStatus(429); // 429 = Too Many Requests
            response.getWriter().write("Too many requests! Please wait.");
            return false; // BLOCK
        }

        return true; // ALLOW
    }
}
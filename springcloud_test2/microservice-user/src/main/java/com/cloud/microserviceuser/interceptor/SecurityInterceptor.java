package com.cloud.microserviceuser.interceptor;

import com.cloud.microserviceuser.annotions.RequiredPermission;
import org.springframework.http.HttpStatus;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SecurityInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 验证权限
        if (this.hasPermission(request, response, handler)) {
            return true;
        }
        // 如果没有权限 则抛403异常
        response.sendError(HttpStatus.FORBIDDEN.value(), "No Permission");
        return false;
    }

    private boolean hasPermission(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            // 获取方法上的注解
            RequiredPermission requiredPermission = handlerMethod.getMethod().getAnnotation(RequiredPermission.class);
            // 如果方法上的注解为空 则获取类的注解
            if (requiredPermission == null) {
                requiredPermission = handlerMethod.getMethod().getDeclaringClass().getAnnotation(RequiredPermission.class);
            }
            // 如果标记了注解，则判断权限
            if (requiredPermission != null) {
                // 判断用户是否已经登录
                Object userId = request.getSession().getAttribute("userId");
                if(userId != null){
                    return true;
                }
                return false;
            }
        }
        return true;
    }
}

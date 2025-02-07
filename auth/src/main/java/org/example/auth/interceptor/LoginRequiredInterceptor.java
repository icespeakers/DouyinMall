//package org.example.auth.interceptor;
//
//import org.example.auth.annotation.LoginRequired;
//import org.springframework.web.method.HandlerMethod;
//import org.springframework.web.servlet.HandlerInterceptor;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.lang.reflect.Method;
//
//public class LoginRequiredInterceptor implements HandlerInterceptor {
//
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        return HandlerInterceptor.super.preHandle(request, response, handler);
////        if(handler instanceof HandlerMethod){
////            HandlerMethod handlerMethod=(HandlerMethod) handler;
////            Method method=handlerMethod.getMethod();
////            LoginRequired loginRequired =method.getAnnotation(LoginRequired.class);
////            if(loginRequired!=null&&hostHolder.getUser()==null){
////                response.sendRedirect(request.getContextPath()+"/login");
////                return false;
////
////            }
////        }
////        return true;
//    }
//}

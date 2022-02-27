package com.stonedt.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class LoginHandlerInterceptor implements HandlerInterceptor {
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    Object attribute = request.getSession().getAttribute("User");
    if (attribute == null) {
      if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
        response.setHeader("REDIRECT", "REDIRECT");
        response.setHeader("CONTENTPATH", "/login");
        response.setStatus(403);
      } else {
        response.sendRedirect(request.getContextPath() + "/login");
      } 
      return false;
    } 
    return true;
  }
//  
//  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//    super.postHandle(request, response, handler, modelAndView);
//  }
//  
//  public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//    super.afterCompletion(request, response, handler, ex);
//  }
}

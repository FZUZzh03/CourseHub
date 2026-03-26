package com.zzh.coursehub.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zzh.coursehub.annotation.RequireStudent;
import com.zzh.coursehub.annotation.RequireTeacher;
import com.zzh.coursehub.entity.vo.Result;
import com.zzh.coursehub.utils.UserHolder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(!(handler instanceof HandlerMethod))return true;//判断是否为controller方法
        HandlerMethod method = (HandlerMethod) handler;//获取方法
        HttpSession session = request.getSession();//获取session容器
        Long userId = (Long) session.getAttribute("userId");//从容器中获取用户id
        if(userId==null){
            response.setContentType("application/json;charset=utf-8");
            Result result = Result.fail(401, "请重新登录");
            response.getWriter().write(objectMapper.writeValueAsString(result));
            return false;
        }
        String role = (String) session.getAttribute("role");//从容器中获取角色
        if(role==null){
            return false;
        }
        RequireStudent student = method.getMethodAnnotation(RequireStudent.class);//获取控制层方法的注解信息
        if(student!=null&&!role.equals("student")){
            Result result = Result.fail(403, "没有权限");
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(objectMapper.writeValueAsString(result));
            return false;
        }
        RequireTeacher teacher = method.getMethodAnnotation(RequireTeacher.class);
        if(teacher!=null&&!role.equals("teacher")){
            Result result = Result.fail(403, "没有权限");
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(objectMapper.writeValueAsString(result));
            return false;
        }

        UserHolder.saveUser(userId);//可访问，将用户ID存入ThreadLocal中
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserHolder.removeUser();
    }
}

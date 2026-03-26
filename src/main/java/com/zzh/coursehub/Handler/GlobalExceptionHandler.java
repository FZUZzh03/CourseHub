package com.zzh.coursehub.Handler;

import com.zzh.coursehub.entity.vo.Result;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e){
        return Result.fail("系统错误，请联系管理员");
    }
}

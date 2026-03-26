package com.zzh.coursehub.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> implements Serializable {
    private int code;
    private String msg;
    private T data;


    public static Result fail(String msg) {
        return new Result(404,msg,null);
    }

    public static Result fail(int code,String msg) {
        return new Result(code,msg,null);
    }
    public static Result success(String msg) {
        return new Result(200,msg,null);
    }

    public static <T>Result<T> success(T data) {
        return new Result<>(200,"success",data);
    }
}

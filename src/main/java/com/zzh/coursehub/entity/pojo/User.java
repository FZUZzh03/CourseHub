package com.zzh.coursehub.entity.pojo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class User implements Serializable {
    private Long id;
    private String username;
    private String password;
    private  String name;
    private String role;
    private Integer age;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Integer isDelete;
}

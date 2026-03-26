package com.zzh.coursehub.entity.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class StudentVO implements Serializable {
    private Long id;
    private String username;
    private String name;
}

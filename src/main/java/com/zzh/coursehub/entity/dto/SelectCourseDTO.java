package com.zzh.coursehub.entity.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class SelectCourseDTO implements Serializable {
    private String username;
    private Long courseId;
}

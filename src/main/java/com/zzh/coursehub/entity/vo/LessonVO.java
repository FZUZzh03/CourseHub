package com.zzh.coursehub.entity.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class LessonVO implements Serializable {
    private Long id;
    private String courseName;
    private String teacherName;
    private String classroom;
}

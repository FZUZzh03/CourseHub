package com.zzh.coursehub.entity.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class AvailableCourseListVO implements Serializable {

    private Long id;
    private String courseName;
    private String teacherName;
    private Integer maxNum;
    private Integer selectedNum;
    private Integer remainNum;
    private String classroom;

}

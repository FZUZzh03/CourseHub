package com.zzh.coursehub.entity.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

@Data
public class NewCourseDTO implements Serializable {
    @NotBlank(message = "课程名称不能为空")
    private String courseName;
    @NotBlank(message = "任课老师不能为空")
    private String teacherName;
    @NotNull(message = "选课人数不能为空")
    @Min(value = 1,message = "最大人数必须大于0")
    private Integer maxNum;
    @NotBlank(message = "教学地点不能为空")
    private String classroom;
}

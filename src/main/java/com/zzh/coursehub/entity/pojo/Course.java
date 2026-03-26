package com.zzh.coursehub.entity.pojo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
@Data
public class Course implements Serializable {
    private Long id;
    private String courseName;
    private Long teacherId;
    private Integer maxNum;
    private Integer selectedNum;
    private String classroom;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Integer isDelete;
}

package com.zzh.coursehub.entity.pojo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
@Data
public class Lesson implements Serializable {
    private Long id;
    private Long studentId;
    private Long courseId;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Integer isDeleted;
}

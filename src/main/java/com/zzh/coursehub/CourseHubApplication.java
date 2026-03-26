package com.zzh.coursehub;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.zzh.coursehub.mapper")
public class CourseHubApplication {

    public static void main(String[] args) {
        SpringApplication.run(CourseHubApplication.class, args);
    }

}

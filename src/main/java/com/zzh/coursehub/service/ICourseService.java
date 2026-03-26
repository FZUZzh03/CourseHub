package com.zzh.coursehub.service;

import com.zzh.coursehub.entity.dto.CourseUpdateDTO;
import com.zzh.coursehub.entity.dto.NewCourseDTO;
import com.zzh.coursehub.entity.dto.PageDTO;
import com.zzh.coursehub.entity.vo.Result;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface ICourseService {
    public Result getAllCourses(PageDTO pageDTO);
    public Result getAllAvailableCourses(PageDTO pageDTO);
    public Result addCourse(@RequestBody NewCourseDTO newCourseDTO);
    public Result updateCourse(@PathVariable Long id, @RequestBody CourseUpdateDTO courseUpdateDTO);
    public Result deleteCourse(@PathVariable Long id);
}

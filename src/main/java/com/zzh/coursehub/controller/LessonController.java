package com.zzh.coursehub.controller;

import com.zzh.coursehub.annotation.RequireStudent;
import com.zzh.coursehub.entity.dto.PageDTO;
import com.zzh.coursehub.entity.dto.SelectCourseDTO;
import com.zzh.coursehub.entity.pojo.Lesson;
import com.zzh.coursehub.entity.vo.Result;
import com.zzh.coursehub.service.ILessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/enrollments")
public class LessonController {
    @Autowired
    ILessonService lessonService;

    @PostMapping()
    @RequireStudent
    public Result selectCourse(@RequestBody SelectCourseDTO selectCourseDTO) {
        return lessonService.selectCourse(selectCourseDTO);
    }

    @DeleteMapping("{id}")
    @RequireStudent
    public Result deleteCourse(@PathVariable Long id) {
        return lessonService.deleteCourse(id);
    }

    @GetMapping("/student")
    @RequireStudent
    public Result getLessons(@RequestBody PageDTO pageDTO) {
        return lessonService.getLessons(pageDTO);
    }
}

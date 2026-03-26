package com.zzh.coursehub.controller;

import com.zzh.coursehub.annotation.RequireTeacher;
import com.zzh.coursehub.entity.dto.CourseUpdateDTO;
import com.zzh.coursehub.entity.dto.NewCourseDTO;
import com.zzh.coursehub.entity.dto.PageDTO;
import com.zzh.coursehub.entity.pojo.Course;
import com.zzh.coursehub.entity.vo.Result;
import com.zzh.coursehub.service.ICourseService;
import com.zzh.coursehub.service.ILessonService;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    @Autowired
    ICourseService courseService;

    @Autowired
    ILessonService lessonService;

    //学生查看可选课程列表
    @GetMapping("/available")
    public Result getAllAvailableCourses(@RequestBody PageDTO pageDTO) {
        return courseService.getAllAvailableCourses(pageDTO);
    }


    //教师查看所有课程
    @GetMapping()
    @RequireTeacher
    public Result getAllCourses(@RequestBody PageDTO pageDTO) {
        return courseService.getAllCourses(pageDTO);
    }

    //教师创建课程
    @PostMapping()
    @RequireTeacher
    public Result addCourse(@RequestBody NewCourseDTO newCourseDTO) {
        return courseService.addCourse(newCourseDTO);
    }

    //教师修改课程信息
    @PutMapping("/{id}")
    @RequireTeacher
    public Result updateCourse(@PathVariable Long id, @RequestBody CourseUpdateDTO  courseUpdateDTO) {
        return courseService.updateCourse(id,courseUpdateDTO);
    }

    //教师删除课程
    @DeleteMapping("/{id}")
    @RequireTeacher
    public Result deleteCourse(@PathVariable Long id) {
        return courseService.deleteCourse(id);
    }

    //教师查看课程学生
    @GetMapping("/{courseId}/students")
    @RequireTeacher
    public Result getStudentsByCourseId(@PathVariable Long courseId,@RequestBody PageDTO pageDTO) {
        return lessonService.getStudentsByCourseId(courseId,pageDTO);
    }

}

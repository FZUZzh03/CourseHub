package com.zzh.coursehub.service;

import com.zzh.coursehub.entity.dto.PageDTO;
import com.zzh.coursehub.entity.dto.SelectCourseDTO;
import com.zzh.coursehub.entity.vo.Result;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface ILessonService {
    public Result getStudentsByCourseId(@PathVariable Long id,@RequestBody PageDTO pageDTO);

    public Result selectCourse(SelectCourseDTO selectCourseDTO);

    public Result deleteCourse(Long id);

    public Result getLessons(@RequestBody PageDTO pageDTO);
}

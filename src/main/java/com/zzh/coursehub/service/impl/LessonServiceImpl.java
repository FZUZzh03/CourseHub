package com.zzh.coursehub.service.impl;

import com.zzh.coursehub.entity.dto.PageDTO;
import com.zzh.coursehub.entity.dto.SelectCourseDTO;
import com.zzh.coursehub.entity.pojo.Course;
import com.zzh.coursehub.entity.pojo.Lesson;
import com.zzh.coursehub.entity.pojo.User;
import com.zzh.coursehub.entity.vo.*;
import com.zzh.coursehub.mapper.CourseMapper;
import com.zzh.coursehub.mapper.LessonMapper;
import com.zzh.coursehub.mapper.UserMapper;
import com.zzh.coursehub.service.ICourseService;
import com.zzh.coursehub.service.ILessonService;
import com.zzh.coursehub.utils.UserHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.DuplicateFormatFlagsException;
import java.util.List;

@Slf4j
@Service
public class LessonServiceImpl implements ILessonService {

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private LessonMapper lessonMapper;


    @Override
    public Result getLessons(PageDTO pageDTO) {
        Long userId = UserHolder.getUser();
        int pageNum = pageDTO.getPageNum() == null || pageDTO.getPageNum() < 1 ? 1 : pageDTO.getPageNum();
        int pageSize = pageDTO.getPageSize() == null || pageDTO.getPageSize() < 1 ? 2 : pageDTO.getPageSize();
        int offset = (pageNum - 1) * pageSize;
        pageDTO.setOffset(offset);
        List<Lesson> list = lessonMapper.getLessonByUserId(userId,pageDTO);
        Long total = lessonMapper.countLessonsByUserId(userId);
        List<LessonVO> ansList = new ArrayList<>();
        for (Lesson lesson : list) {
            Course course = courseMapper.getCourseById(lesson.getCourseId());
            User teacher = userMapper.getUserById(course.getTeacherId());
            LessonVO lessonVO = new LessonVO();
            lessonVO.setId(lesson.getId());
            lessonVO.setCourseName(course.getCourseName());
            lessonVO.setTeacherName(teacher.getName());
            lessonVO.setClassroom(course.getClassroom());
            ansList.add(lessonVO);
        }
        PageResult<LessonVO> result = PageResult.build(
                ansList,
                total,
                pageDTO.getPageSize(),
                pageDTO.getPageNum()
        );
        return Result.success(result);
    }

    @Transactional
    @Override
    public Result deleteCourse(Long id) {
        Long userId = UserHolder.getUser();
        Lesson lesson = lessonMapper.getLessonByUserIdAndCourseId(userId, id);
        if(lesson==null){
            return Result.fail(400,"未选择该课程，无法退选");
        }
        lessonMapper.deleteCourseByLessonId(lesson.getId());
        courseMapper.decreaseSelectedNumByCourseId(id);
        return Result.success("退选成功");
    }

    @Transactional
    @Override
    public Result selectCourse(SelectCourseDTO selectCourseDTO) {
        Long userId = UserHolder.getUser();
        Long courseId = selectCourseDTO.getCourseId();
        Course course = courseMapper.getCourseById(courseId);
        if (course == null) {
            return Result.fail(404,"课程不存在");
        }
        if (course.getMaxNum() <= course.getSelectedNum()) {
            return Result.fail(400,"人数已满");
        }
        Lesson lesson = lessonMapper.getLessonByUserIdAndCourseId(userId, courseId);
        //先查是否已选课
        if (lesson != null) {
            if (lesson.getIsDeleted() == 0) {
                return Result.fail(400,"不可重复选课");
            } else if (lesson.getIsDeleted() == 1) {
                //如果课表存在，但是被退选，现在重新选上
                lessonMapper.selectCourseAgain(lesson.getId());
                return Result.success("选课成功");
            }
        }
        //再查一遍，防止并发修改
        Lesson checkLesson = lessonMapper.getLessonByUserIdAndCourseId(userId, courseId);
        if(checkLesson!=null){
            return Result.fail(400,"不可重复选课");
        }
        //如果没有查到
        Lesson newLesson = new Lesson();
        newLesson.setCourseId(courseId);
        newLesson.setStudentId(userId);
        try {
            lessonMapper.createNewLesson(newLesson);
            courseMapper.increaseSelectedNumByCourseId(courseId);
        }catch (DuplicateKeyException e){
            return Result.fail(400,"不可重复选课");
        }
        return Result.success("选课成功");
    }

    @Override
    public Result getStudentsByCourseId(Long id, PageDTO pageDTO) {
        Long userId = UserHolder.getUser();
        Course course = courseMapper.getCourseById(id);
        if(course == null){
            return Result.fail(404,"课程不存在");
        }
        if(!course.getTeacherId().equals(userId)){
            return Result.fail(403,"不可查看其他老师的课程学生");
        }
        int pageNum = pageDTO.getPageNum() == null || pageDTO.getPageNum() < 1 ? 1 : pageDTO.getPageNum();
        int pageSize = pageDTO.getPageSize() == null || pageDTO.getPageSize() < 1 ? 2 : pageDTO.getPageSize();
        int offset = (pageNum - 1) * pageSize;
        pageDTO.setOffset(offset);
        List<StudentVO> list = lessonMapper.getStudentsByCourseId(id,pageDTO);
        Long total = lessonMapper.countStudentsByCourseId(id);
        PageResult<StudentVO> result = PageResult.build(
                list,
                total,
                pageDTO.getPageSize(),
                pageDTO.getPageNum()
        );
        return Result.success(result);
    }
}

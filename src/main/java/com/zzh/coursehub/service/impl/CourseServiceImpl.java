package com.zzh.coursehub.service.impl;

import com.zzh.coursehub.entity.dto.CourseUpdateDTO;
import com.zzh.coursehub.entity.dto.NewCourseDTO;
import com.zzh.coursehub.entity.dto.PageDTO;
import com.zzh.coursehub.entity.pojo.Course;
import com.zzh.coursehub.entity.pojo.User;
import com.zzh.coursehub.entity.vo.*;
import com.zzh.coursehub.mapper.CourseMapper;
import com.zzh.coursehub.mapper.LessonMapper;
import com.zzh.coursehub.mapper.UserMapper;
import com.zzh.coursehub.service.ICourseService;
import com.zzh.coursehub.utils.UserHolder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourseServiceImpl implements ICourseService {

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private LessonMapper lessonMapper;

    @Override
    public Result getAllCourses(PageDTO pageDTO) {
        int pageNum = pageDTO.getPageNum() == null || pageDTO.getPageNum() < 1 ? 1 : pageDTO.getPageNum();
        int pageSize = pageDTO.getPageSize() == null || pageDTO.getPageSize() < 1 ? 10 : pageDTO.getPageSize();
        int offset = (pageNum - 1) * pageSize;
        pageDTO.setOffset(offset);
        List<CourseListVO> list = courseMapper.getCourseList(pageDTO);
        Long total = courseMapper.countAll();
        PageResult<CourseListVO> result = PageResult.build(
                list,
                total,
                pageDTO.getPageSize(),
                pageDTO.getPageNum()
        );
        return Result.success(result);
    }

    @Override
    public Result getAllAvailableCourses(PageDTO pageDTO) {
        int pageNum = pageDTO.getPageNum() == null || pageDTO.getPageNum() < 1 ? 1 : pageDTO.getPageNum();
        int pageSize = pageDTO.getPageSize() == null || pageDTO.getPageSize() < 1 ? 10 : pageDTO.getPageSize();
        int offset = (pageNum - 1) * pageSize;
        pageDTO.setOffset(offset);
        //先查出所有课程
        List<CourseListVO> list = courseMapper.getCourseList(pageDTO);
        Long total = courseMapper.countAllAvailable();
        //遍历所有课程，排除满员课程
        List<AvailableCourseListVO> ansList = new ArrayList<>();
        for(CourseListVO courseListVO : list){
            if(courseListVO.getRemainNum()>0) {
                AvailableCourseListVO availableCourseListVO = new AvailableCourseListVO();
                BeanUtils.copyProperties(courseListVO, availableCourseListVO);
                ansList.add(availableCourseListVO);
            }
        }
        PageResult<AvailableCourseListVO> result = PageResult.build(
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
        Course course = courseMapper.getCourseById(id);
        if(course==null){
            return Result.fail(404,"课程不存在");
        }
        if(!course.getTeacherId().equals(userId)){
            return Result.fail(403,"不可删除其他老师的课程");
        }
        courseMapper.deleteCourseById(id);
        lessonMapper.deleteLessonCauseCourseDeleted(id);
        return Result.success("删除成功");
    }

    @Transactional
    @Override
    public Result updateCourse(Long id, CourseUpdateDTO courseUpdateDTO) {
        Long userId = UserHolder.getUser();
        Course course = courseMapper.getCourseById(id);
        if(course==null){
            return Result.fail(404,"未找到课程信息");
        }
        if(!course.getTeacherId().equals(userId)){
            return Result.fail(403,"不得修改其他老师的课程信息");
        }
        course.setCourseName(courseUpdateDTO.getCourseName());
        course.setClassroom(courseUpdateDTO.getClassroom());
        course.setMaxNum(courseUpdateDTO.getMaxNum());
        courseMapper.updateCourse(course);
        return Result.success("修改成功");
    }

    @Transactional
    @Override
    public Result addCourse(NewCourseDTO newCourseDTO) {
        Long userId = UserHolder.getUser();
        User teacher = userMapper.getByname(newCourseDTO.getTeacherName());
        if(teacher==null||!teacher.getRole().equals("teacher")){
            return Result.fail(404,"老师不存在");
        }
        Long teacherId = teacher.getId();
        if(!teacherId.equals(userId)){
            return Result.fail(403,"不得为其他老师创建课程");
        }
        Course course = courseMapper.getCourseByTeacherIdAndName(teacherId,newCourseDTO.getCourseName());
        if(course!=null){
            return Result.fail(400,"该老师已创建同名课程");
        }
        Course newCourse = new Course();
        BeanUtils.copyProperties(newCourseDTO,newCourse);
        newCourse.setTeacherId(teacherId);
        newCourse.setSelectedNum(0);
        courseMapper.add(newCourse);
        return Result.success("创建成功");
    }
}

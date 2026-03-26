package com.zzh.coursehub.mapper;

import com.zzh.coursehub.entity.dto.PageDTO;
import com.zzh.coursehub.entity.pojo.Course;
import com.zzh.coursehub.entity.vo.CourseListVO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CourseMapper {

    List<CourseListVO> getCourseList( PageDTO pageDTO);

    @Select("select count(*) from course where  is_deleted = 0")
    Long countAll();

    @Select("select count(*) from course where max_num - selected_num > 0 AND is_deleted = 0")
    Long countAllAvailable();

    @Select("select * from course where teacher_id = #{id} AND course_name = #{name}")
    Course getCourseByTeacherIdAndName(Long id, String name);

    @Insert("insert into course (course_name,teacher_id,max_num,selected_num,classroom) values (#{courseName},#{teacherId},#{maxNum},#{selectedNum},#{classroom})")
    int add(Course newCourse);

    @Select("select * from course where id = #{id} AND is_deleted = 0 order by id for update")
    Course getCourseById(Long id);

    @Update("update course set course_name = #{courseName},max_num = #{maxNum},classroom = #{classroom},update_time = NOW() where id = #{id}")
    int updateCourse(Course newCourse);

    @Update("update course set is_deleted = 1 ,update_time = NOW() where id = #{id}")
    int deleteCourseById(Long id);

    @Update("update course set selected_num = selected_num + 1, update_time = NOW() where id = #{courseId}")
    int increaseSelectedNumByCourseId(Long courseId);

    @Update("update course set selected_num = selected_num - 1,update_time = NOW() where id = #{courseId}")
    int decreaseSelectedNumByCourseId(Long courseId);
}

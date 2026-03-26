package com.zzh.coursehub.mapper;

import com.zzh.coursehub.entity.dto.PageDTO;
import com.zzh.coursehub.entity.pojo.Lesson;
import com.zzh.coursehub.entity.vo.CourseListVO;
import com.zzh.coursehub.entity.vo.LessonVO;
import com.zzh.coursehub.entity.vo.StudentVO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface LessonMapper {

    List<StudentVO> getStudentsByCourseId(Long id, @Param("pageDTO") PageDTO pageDTO);

    @Select("select count(*) from lesson where course_id = #{id} AND is_deleted = 0")
    Long countStudentsByCourseId(Long id);

    @Select("select count(*) from lesson where student_id = #{id} AND is_deleted = 0")
    Long countLessonsByUserId(Long id);

    @Select("select * from lesson where student_id = #{userId} AND course_id = #{courseId} for update")
    Lesson getLessonByUserIdAndCourseId(Long userId, Long courseId);

    @Insert("insert into lesson (student_id,course_id) values (#{studentId},#{courseId}) ")
    int createNewLesson(Lesson lesson);

    @Update("update lesson set is_deleted = 1 ,update_time = NOW() where id = #{id}")
    int deleteCourseByLessonId(Long id);

    List<Lesson> getLessonByUserId(Long userId, @Param("pageDTO")PageDTO pageDTO);

    @Update("update lesson set is_deleted = 0 where id = #{id}")
    int selectCourseAgain(Long id);

    @Update("update lesson set is_deleted = 1,update_time = NOW() where course_id = #{courseId}")
    int deleteLessonCauseCourseDeleted(Long courseId);
}
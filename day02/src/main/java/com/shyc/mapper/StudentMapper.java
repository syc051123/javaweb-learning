package com.shyc.mapper;

import com.shyc.pojo.Student;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Student 表的 MyBatis Mapper 接口
 */
@Mapper
public interface StudentMapper {

    // TODO: 补全 @Select 注解和 SQL 语句
    // 提示：SELECT * FROM student
    // 返回类型：List<Student>

    List<Student> findAll();

    //按照姓名查找学生
    @Select("select * from student where name like #{name}")
    List<Student> findByName(String name);

    @Delete("DELETE FROM student WHERE student_id=#{studentId} ")
    int deleteByStudentId(String studentId);

    @Insert("INSERT INTO student (student_id, name, gender, age, phone) "
            + " VALUES (#{studentId}, #{name}, #{gender}, #{age}, #{phone})")
    int addStudent(Student student);

    @Update("UPDATE student SET " +
            "name= #{name}," +
            "gender=#{gender}," +
            "age=#{age}," +
            "phone=#{phone}," +
            "student_id=#{studentId} WHERE student_id=#{studentId}")
    int updateStudentById(Student student);

    @Select("SELECT * FROM student WHERE student_id = #{id} AND name = #{name}")
    Student findByStudentIdAndName(@Param("id") String studentId,
                                   @Param("name") String name);


}

package com.shyc.mapper;

import com.shyc.pojo.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Student 表的 MyBatis Mapper 接口
 */
@Mapper
public interface StudentMapper {

    // TODO: 补全 @Select 注解和 SQL 语句
    // 提示：SELECT * FROM student
    // 返回类型：List<Student>
    @Select("SELECT * FROM student")
     List<Student> findAll();

    //按照姓名查找学生
    @Select("select * from student where name like #{name}")
    List<Student> findByName(String name);

}

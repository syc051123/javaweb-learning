package com.shyc;

import com.shyc.mapper.StudentMapper;
import com.shyc.pojo.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * MyBatis 测试类
 */
@SpringBootTest
public class MyBatisTest {

    @Autowired
    private StudentMapper studentMapper;

    /**
     * 查询所有学生
     * <p>
     * 步骤：
     * 1. 调用 studentMapper.findAll()
     * 2. 遍历打印结果
     */
    @Test
    public void testFindAll() {
        // TODO: 调用 studentMapper.findAll() 获取 List<Student>
        // TODO: 遍历 list，打印每个 student
        List<Student> list = studentMapper.findAll();
        for (Student stu : list) {
            System.out.println(stu);
        }
    }

    /**
     * 按姓名查询学生
     * <p>
     * 步骤：
     * 1. 调用 studentMapper.findByName(...)
     * 2. 打印结果
     */
    @Test
    public void testFindByName() {
        // TODO: 调用 studentMapper.findByName("张三")
        // TODO: 打印结果
        List<Student> list =studentMapper.findByName("张%");
            list.forEach(System.out::println);
    }

}

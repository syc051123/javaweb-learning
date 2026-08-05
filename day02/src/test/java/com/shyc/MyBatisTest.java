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

    /**
     *删除学生
     * 步骤：
     * 1. 调用 studentMapper.delete(...)
     * 2. 打印结果
     */
    @Test
    public void testDelete() {
        // TODO: 调用 studentMapper.delete(...)
        // TODO: 删除学号为 1001 的学生
        int count = studentMapper.deleteByStudentId("20240002");
        System.out.println("删除了 " + count + " 条记录");
    }

    /**
     * 新增学生
     * <p>
     * 步骤：
     * 1. 调用 studentMapper.insert(...)
     * 2. 获取插入的行数
     * 3. 打印结果
     */
    @Test
    public void testAddStudent() {
        // TODO: 创建 Student 对象
        Student student = new Student();
        student.setStudentId("20240002");
        student.setName("张三");
        student.setGender("男");
        student.setAge(18);
        student.setPhone("12345678901");

        // TODO: 调用 studentMapper.insert(...)
        // TODO: 获取插入的行数
        int count = studentMapper.addStudent(student);
        System.out.println("插入了 " + count + " 条记录");
    }

    /**
     * 修改学生
     * <p>
     * 步骤：
     * 1. 调用 studentMapper.update(...)
     * 2. 获取修改的行数
     * 3. 打印结果
     */

    @Test
    public void testUpdateStudentById() {
        // TODO: 创建 Student 对象
        Student student = new Student();
        student.setStudentId("20240002");
        student.setName("张三");
        student.setGender("男");
        student.setAge(18);
        student.setPhone("12345678901");

        // TODO: 调用 studentMapper.update(...)
        // TODO: 获取修改的行数
        int count = studentMapper.updateStudentById(student);
        System.out.println("修改了 " + count + " 条记录");


    }


    @Test
    public void testFindByStudentIdAndName() {
        // TODO: 调用 studentMapper.findByStudentIdAndName(...)
        // TODO: 获取 Student 对象
        Student student = studentMapper.findByStudentIdAndName("20240002", "张三");
        System.out.println(student);
    }

}

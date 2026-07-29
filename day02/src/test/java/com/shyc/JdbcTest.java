package com.shyc;

import com.shyc.pojo.Student;
import org.junit.jupiter.api.Test;

import java.sql.*;

/**
 * JDBC 测试类
 * <p>
 * 使用 JDBC 原生 API 操作 student 表
 * <p>
 * TODO: 补全以下方法的 JDBC 操作代码
 */
public class JdbcTest {

    /**
     * 数据库连接信息
     */
    private static final String URL = "jdbc:mysql://localhost:3306/javaweb_learning?useUnicode=true&characterEncoding=UTF-8";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "123456";

    /**
     * 查询所有学生
     * <p>
     * 步骤：
     * 1. 获取 Connection
     * 2. 编写 SQL：SELECT * FROM student
     * 3. 创建 PreparedStatement
     * 4. 执行查询，获取 ResultSet
     * 5. 遍历 ResultSet，封装成 Student 对象并打印
     * 6. 关闭资源（rs → pstmt → conn）
     */
    @Test
    public void testQueryAll() throws Exception {
        // TODO: 补全 JDBC 查询代码
        Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/javaweb_learning", "root", "123456"
        );
        String sql = "SELECT * FROM student where age>?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, 18);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            Student stu = new Student();
            stu.setStudentId(rs.getString("student_id"));
            stu.setName(rs.getString("name"));
            stu.setGender(rs.getString("gender"));
            stu.setAge(rs.getInt("age"));
            stu.setPhone(rs.getString("phone"));
            System.out.println(stu);
        }
        rs.close();
        pstmt.close();
        conn.close();


    }

    /**
     * 按学号查询学生
     * <p>
     * 步骤：
     * 1. 获取 Connection
     * 2. 编写 SQL：SELECT * FROM student WHERE student_id = ?
     * 3. 创建 PreparedStatement，设置参数
     * 4. 执行查询，获取 ResultSet
     * 5. 遍历 ResultSet，封装成 Student 对象并打印
     * 6. 关闭资源
     */
    @Test
    public void testQueryById() throws Exception {

        Connection conn=DriverManager .getConnection(URL, USERNAME, PASSWORD);
        String sql="SELECT * FROM student WHERE student_id LIKE ?";
        PreparedStatement pstmt=conn.prepareStatement(sql);
        pstmt.setString(1, "2%");
        ResultSet rs=pstmt.executeQuery();
        int count=0;
        while (rs.next()) {
            count++;
            Student stu=new Student();
            stu.setStudentId(rs.getString("student_id"));
            stu.setName(rs.getString("name"));
            stu.setGender(rs.getString("gender"));
            stu.setAge(rs.getInt("age"));
            stu.setPhone(rs.getString("phone"));
            System.out.println(stu);
        }
        System.out.println("共有"+count+"条记录");
        rs.close();
        pstmt.close();
        conn.close();
    }

    /**
     * 新增学生
     * <p>
     * 步骤：
     * 1. 获取 Connection
     * 2. 编写 SQL：INSERT INTO student(...) VALUES(?, ?, ?, ?, ?)
     * 3. 创建 PreparedStatement，设置参数
     * 4. 执行 executeUpdate，获取影响行数
     * 5. 关闭资源
     */
    @Test
    public void testInsert() throws Exception {
        // TODO: 补全 JDBC 新增代码
        Connection conn=DriverManager .getConnection(URL, USERNAME, PASSWORD);
        String sql="INSERT INTO student(student_id,name,gender,age,phone) VALUES(?,?,?,?,?)";
        PreparedStatement pstmt=conn.prepareStatement(sql);
        pstmt.setString(1, "20190001");
        pstmt.setString(2, "张三");
        pstmt.setString(3, "男");
        pstmt.setInt(4, 18);
        pstmt.setString(5, "13800000000");
        int count=pstmt.executeUpdate();
        pstmt.close();
        conn.close();


    }

    /**
     * 按学号更新学生姓名
     * <p>
     * 步骤：
     * 1. 获取 Connection
     * 2. 编写 SQL：UPDATE student SET name = ? WHERE student_id = ?
     * 3. 创建 PreparedStatement，设置参数
     * 4. 执行 executeUpdate，获取影响行数
     * 5. 关闭资源
     */
    @Test
    public void testUpdate() throws Exception {
        // TODO: 补全 JDBC 更新代码
        Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        String sql = "UPDATE student SET name = ? WHERE student_id = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, "张三");
        pstmt.setString(2, "2024030");
        int count = pstmt.executeUpdate();
        System.out.println("成功更新" + count + "条记录");
        pstmt.close();
        conn.close();
    }
    /**
     * 按学号删除学生
     * <p>
     * 步骤：
     * 1. 获取 Connection
     * 2. 编写 SQL：DELETE FROM student WHERE student_id = ?
     * 3. 创建 PreparedStatement，设置参数
     * 4. 执行 executeUpdate，获取影响行数
     * 5. 关闭资源
     */
    @Test
    public void testDelete() throws Exception {
        // TODO: 补全 JDBC 删除代码
        Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        String sql = "DELETE FROM student WHERE student_id = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, "2024030");
        int count = pstmt.executeUpdate();
        System.out.println("成功删除" + count + "条记录");
        pstmt.close();
        conn.close();

    }
    /**
     * 去除表中重复数据
     * <p>
     *     步骤：
     *     1. 获取 Connection
     *     2. 创建 Statement
     *     3. 执行 SQL：SELECT DISTINCT student_id FROM student
     *     4. 获取 ResultSet
     *     5. 遍历 ResultSet，打印学号
     *     6. 关闭资源

     */
    @Test
    public void testDistinct() throws Exception {
        Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        Statement stmt = conn.createStatement();
        String sql = "SELECT DISTINCT * FROM student";
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
          Student stu = new Student();
          stu.setStudentId(rs.getString("student_id"));
          stu.setName(rs.getString("name"));
          stu.setGender(rs.getString("gender"));
          stu.setAge(rs.getInt("age"));
          stu.setPhone(rs.getString("phone"));
          System.out.println(stu);
        }
    }




}

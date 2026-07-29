package com.shyc;

import com.shyc.pojo.Student;
import org.junit.jupiter.api.Test;

import java.sql.*;

/**
 * JDBC 测试类 —— 完整参考答案
 */
public class JdbcTest_参考答案 {

    private static final String URL = "jdbc:mysql://localhost:3306/javaweb_learning";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "123456";

    // ========== 查询 ==========

    @Test
    public void testQueryAll() throws Exception {
        // 1. 获取连接
        Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

        // 2. 编写 SQL
        String sql = "SELECT * FROM student";

        // 3. 创建 PreparedStatement
        PreparedStatement pstmt = conn.prepareStatement(sql);

        // 4. 执行查询
        ResultSet rs = pstmt.executeQuery();

        // 5. 遍历结果
        while (rs.next()) {
            Student stu = new Student();
            stu.setStudentId(rs.getString("student_id"));
            stu.setName(rs.getString("name"));
            stu.setGender(rs.getString("gender"));
            stu.setAge(rs.getInt("age"));
            stu.setPhone(rs.getString("phone"));
            System.out.println(stu);
        }

        // 6. 关闭资源（后开先关）
        rs.close();
        pstmt.close();
        conn.close();
    }

    @Test
    public void testQueryById() throws Exception {
        Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

        String sql = "SELECT * FROM student WHERE student_id = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);

        // 设置参数（? 从1开始）
        pstmt.setString(1, "2024001");

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

    // ========== 增删改 ==========

    @Test
    public void testInsert() throws Exception {
        Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

        String sql = "INSERT INTO student(student_id, name, gender, age, phone) VALUES(?, ?, ?, ?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);

        pstmt.setString(1, "2024030");
        pstmt.setString(2, "张三");
        pstmt.setString(3, "男");
        pstmt.setInt(4, 20);
        pstmt.setString(5, "13800000000");

        // executeUpdate 返回影响的行数
        int rows = pstmt.executeUpdate();
        System.out.println("影响了 " + rows + " 行");

        pstmt.close();
        conn.close();
    }

    @Test
    public void testUpdate() throws Exception {
        Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

        String sql = "UPDATE student SET name = ? WHERE student_id = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);

        pstmt.setString(1, "李四");
        pstmt.setString(2, "2024030");

        int rows = pstmt.executeUpdate();
        System.out.println("影响了 " + rows + " 行");

        pstmt.close();
        conn.close();
    }

    @Test
    public void testDelete() throws Exception {
        Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

        String sql = "DELETE FROM student WHERE student_id = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);

        pstmt.setString(1, "2024030");

        int rows = pstmt.executeUpdate();
        System.out.println("影响了 " + rows + " 行");

        pstmt.close();
        conn.close();
    }
}

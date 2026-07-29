# 黑马 JavaWeb 第 62 集学习指导：JDBC 入门

> **视频**：BV1yGydYEE3H，P62
> **学完后你能做到**：用 Java 代码连接 MySQL，执行 SQL

---

## 一、JDBC 是什么

🟢 **必须熟练（概念）**

**JDBC** = Java DataBase Connectivity，Java 数据库连接。

**拆词根**：
- **Java** — Java 语言
- **DataBase** — 数据库
- **Connectivity** — 连接能力

合起来：**用 Java 连接数据库的一套标准 API。**

JDBC 是操作数据库**最底层、最基础**的技术。现在企业多用 MyBatis 这类封装框架，但 JDBC 是底层原理。

---

## 二、准备工作

### 1. 在 pom.xml 加 JDBC 依赖

**生效范围**：`day02/pom.xml` 的 `<dependencies>` 标签内

```xml
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-j</artifactId>
    <scope>runtime</scope>
</dependency>
```

> SpringBoot 父工程已经管了 MySQL 驱动的版本，所以不用写 `<version>`。

### 2. 在 test 目录下写 JDBC 代码

在 `src/test/java/com/shyc/` 下创建测试类。

---

## 三、查询数据

🟢 **必须熟练（查是 JDBC 里最重要的操作）**

### 完整代码

```java
package com.shyc;

import org.junit.jupiter.api.Test;
import java.sql.*;

public class JdbcTest {

    @Test
    public void testQuery() throws Exception {
        // 1. 获取连接
        Connection conn = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/javaweb_learning", "root", "123456"
        );

        // 2. 预编译 SQL（? 是占位符，防 SQL 注入）
        String sql = "SELECT * FROM student WHERE age > ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, 19);  // 设置参数

        // 3. 执行查询
        ResultSet rs = pstmt.executeQuery();

        // 4. 处理结果
        while (rs.next()) {
            String studentId = rs.getString("student_id");
            String name = rs.getString("name");
            String gender = rs.getString("gender");
            int age = rs.getInt("age");
            String phone = rs.getString("phone");
            System.out.println(studentId + " - " + name + " - " + gender + " - " + age);
        }

        // 5. 关闭资源
        rs.close();
        pstmt.close();
        conn.close();
    }
}
```

### ResultSet 详解

🟡 **知道怎么用就行**

`ResultSet`（结果集）：封装了 DQL 查询的结果。

- `next()`：光标向下移动一行。返回 `true` 表示有数据，`false` 表示没数据了
- `getXxx(字段名)`：获取当前行的某个字段值

| 方法 | 说明 |
|------|------|
| `rs.getInt("age")` | 获取整型字段 |
| `rs.getString("name")` | 获取字符串字段 |
| `rs.getDouble("price")` | 获取小数类型字段 |

---

## 四、增删改数据

### 新增

```java
@Test
public void testInsert() throws Exception {
    Connection conn = DriverManager.getConnection(
        "jdbc:mysql://localhost:3306/javaweb_learning", "root", "123456"
    );

    String sql = "INSERT INTO student(student_id, name, gender, age, phone) VALUES (?, ?, ?, ?, ?)";
    PreparedStatement pstmt = conn.prepareStatement(sql);
    pstmt.setString(1, "20240031");
    pstmt.setString(2, "测试");
    pstmt.setString(3, "男");
    pstmt.setInt(4, 20);
    pstmt.setString(5, "13800000000");

    int count = pstmt.executeUpdate();  // 返回影响的行数
    System.out.println("影响了 " + count + " 行");

    pstmt.close();
    conn.close();
}
```

### 修改

```java
@Test
public void testUpdate() throws Exception {
    Connection conn = DriverManager.getConnection(
        "jdbc:mysql://localhost:3306/javaweb_learning", "root", "123456"
    );

    String sql = "UPDATE student SET age = ? WHERE name = ?";
    PreparedStatement pstmt = conn.prepareStatement(sql);
    pstmt.setInt(1, 25);
    pstmt.setString(2, "刘雨萱");

    int count = pstmt.executeUpdate();
    System.out.println("影响了 " + count + " 行");

    pstmt.close();
    conn.close();
}
```

### 删除

```java
@Test
public void testDelete() throws Exception {
    Connection conn = DriverManager.getConnection(
        "jdbc:mysql://localhost:3306/javaweb_learning", "root", "123456"
    );

    String sql = "DELETE FROM student WHERE student_id = ?";
    PreparedStatement pstmt = conn.prepareStatement(sql);
    pstmt.setString(1, "20240031");

    int count = pstmt.executeUpdate();
    System.out.println("删除了 " + count + " 行");

    pstmt.close();
    conn.close();
}
```

---

## 五、核心 API 总结

🟡 **知道作用就行，具体写法可以查**

| API | 作用 |
|-----|------|
| `DriverManager` | 管理驱动，建立连接 |
| `Connection` | 和数据库的连接 |
| `PreparedStatement` | 预编译 SQL，用 `?` 占位，防 SQL 注入 |
| `ResultSet` | 封装查询结果，用 `next()` 遍历 |
| `executeQuery()` | 执行 SELECT 查询，返回 ResultSet |
| `executeUpdate()` | 执行 INSERT/UPDATE/DELETE，返回影响行数 |

---

## 六、小练习

1. 在 day02 的 `src/test/java/com/shyc/` 下创建 `JdbcTest.java`
2. 复制上面的查询代码，把条件改成查年龄小于 20 的女生
3. 运行测试，看看结果

---

## 七、一句话总结

> **JDBC 是 Java 操作数据库的底层 API：查询用 `executeQuery()` + `ResultSet`，增删改用 `executeUpdate()`。用 `PreparedStatement` 的 `?` 占位符来防 SQL 注入。**

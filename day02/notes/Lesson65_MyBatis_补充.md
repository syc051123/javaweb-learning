# MyBatis 入门 — 补充笔记

> 基于你的 06.md 笔记补充几个关键点

---

## 一、JDBC → MyBatis 变化到底有多大？

**你刚写的 20+ 行 JDBC 代码**：

```java
Connection conn = DriverManager.getConnection(...);
String sql = "SELECT * FROM student WHERE student_id = ?";
PreparedStatement pstmt = conn.prepareStatement(sql);
pstmt.setString(1, "2024001");
ResultSet rs = pstmt.executeQuery();
while(rs.next()) {
    Student stu = new Student();
    stu.setStudentId(rs.getString("student_id"));
    stu.setName(rs.getString("name"));
    stu.setGender(rs.getString("gender"));
    stu.setAge(rs.getInt("age"));
    stu.setPhone(rs.getString("phone"));
    System.out.println(stu);
}
rs.close(); pstmt.close(); conn.close();
```

**换成 MyBatis**：

```java
@Mapper
public interface StudentMapper {
    @Select("SELECT * FROM student WHERE student_id = #{id}")
    Student findByStudentId(String id);
}
```

这就是框架的意义：**你只需要关心 SQL 本身和返回值，其他让框架干。**

---

## 二、`#{}` vs `${}` — 必须搞清楚

笔记里提了但没展开，这里是关键：

| 符号 | 行为 | 安全吗 | 什么时候用 |
|------|------|--------|-----------|
| `#{name}` | 预编译，生成 `?` | ✅ 安全 | **99% 的情况** |
| `${name}` | 直接拼接字符串 | ❌ SQL 注入风险 | 表名/列名动态传入时 |

**🟢 默认永远用 `#{}`**，除非你知道自己在做什么。

---

## 三、MyBatis 帮你省了哪几件事？

对照你 JDBC 写的代码：

| JDBC 你要自己做的事 | MyBatis 帮你做了 |
|-------------------|----------------|
| `DriverManager.getConnection()` | 自动从连接池拿连接 |
| 手动 set 参数 | `#{}` 自动匹配 |
| 手动遍历 ResultSet 封装对象 | 自动映射到实体类 |
| 手动 close 资源 | 自动归还连接到池 |

---

## 四、关于你的项目怎么搞

笔记里说创建新的 SpringBoot 工程，但咱们可以直接在 **day02** 里加 MyBatis 依赖，不需要再建一个模块。

要加的依赖：

```xml
<!-- pom.xml 里加这个 -->
<dependency>
    <groupId>org.mybatis.spring.boot</groupId>
    <artifactId>mybatis-spring-boot-starter</artifactId>
    <version>3.0.4</version>
</dependency>
```

然后在 `application.properties` 里配数据库连接：

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/javaweb_learning
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.username=root
spring.datasource.password=123456

# 打印 MyBatis 执行的 SQL
mybatis.configuration.log-impl=org.apache.ibatis.logging.stdout.StdOutImpl
```

---

## 五、改动最小化

笔记里用的是 `user` 表，但我们的数据库里已经建了 `student` 表。所以后面写 MyBatis 的时候，我会帮你**直接用 `student` 表**，不用再重新建表插数据，直接上手写 Mapper。

---

**准备好开始动手了吗？** 我帮你加 MyBatis 依赖，然后写第一个 `StudentMapper` 接口查所有学生。
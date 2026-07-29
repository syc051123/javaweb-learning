# 黑马 JavaWeb 第 65 集学习指导：MyBatis 入门

> **视频**：P65，MyBatis 快速入门
> **学完后你能做到**：用 MyBatis 代替 JDBC 查询数据库

---

## 一、MyBatis 是什么

🟢 **必须熟练（概念）**

**MyBatis** 是一款优秀的**持久层框架**，用于**简化 JDBC 开发**。

**拆词**：
- **My** — 我的
- **Batis** — 原来叫 **iBatis**（Internet + Abatis），意思是"互联网的数据库映射工具"
- 2010 年从 Apache 迁移到 Google Code 后改名为 **MyBatis**

大白话：**JDBC 写起来太啰嗦了，MyBatis 帮你省掉那些重复代码。**

---

## 二、JDBC 对比 MyBatis

### JDBC 查数据（你刚写的）

```java
Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
String sql = "SELECT * FROM student";
PreparedStatement pstmt = conn.prepareStatement(sql);
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
rs.close(); pstmt.close(); conn.close();
```

### MyBatis 查数据

```java
@Mapper                         // ① 标记是 Mapper
public interface StudentMapper {
    @Select("SELECT * FROM student")   // ② 写上 SQL
    List<Student> findAll();           // ③ 定义方法，返回 List
}
```

测试类里调用：

```java
@Autowired
private StudentMapper studentMapper;

@Test
public void testFindAll() {
    List<Student> list = studentMapper.findAll();
    for (Student stu : list) {
        System.out.println(stu);
    }
}
```

**MyBatis 帮你省掉的**：

| JDBC 你要做的事 | MyBatis 做了什么 |
|----------------|-----------------|
| 手动获取 Connection | 连接池自动提供 |
| 手动创建 PreparedStatement | `@Select` 自动生成 |
| 手动 set 参数 | `#{}` 自动匹配 |
| 手动遍历 ResultSet 封装对象 | 自动映射到实体类 |
| 手动 close 资源 | 自动归还 |

---

## 三、快速入门步骤

🟢 **必须熟练（这一套流程）**

### 第 1 步：加依赖

```xml
<dependency>
    <groupId>org.mybatis.spring.boot</groupId>
    <artifactId>mybatis-spring-boot-starter</artifactId>
    <version>3.0.4</version>
</dependency>
```

> 已加好了，直接用。

### 第 2 步：配数据库

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/javaweb_learning
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.username=root
spring.datasource.password=123456
mybatis.configuration.log-impl=org.apache.ibatis.logging.stdout.StdOutImpl
```

> 已配好了，直接用。

### 第 3 步：写 Mapper 接口

在 `com.shyc.mapper` 包下创建接口：

```java
@Mapper
public interface StudentMapper {
    @Select("SELECT * FROM student")
    List<Student> findAll();
}
```

### 第 4 步：写测试类

```java
@SpringBootTest
public class MyBatisTest {

    @Autowired
    private StudentMapper studentMapper;

    @Test
    public void testFindAll() {
        List<Student> list = studentMapper.findAll();
        for (Student stu : list) {
            System.out.println(stu);
        }
    }
}
```

---

## 四、两个核心注解

🟢 **必须熟练**

### `@Mapper`

- 标记在接口上，告诉 Spring："这是一个 MyBatis 的 Mapper 接口"
- Spring 启动时会自动为这个接口**生成实现类对象（代理对象）**，放进 IOC 容器
- 所以你才能在测试类里用 `@Autowired` 直接注入

### `@Select("SQL 语句")`

- 标记在方法上，告诉 MyBatis："这个方法执行的是这条 SELECT 查询"
- SQL 里可以用 `#{字段名}` 做参数占位

---

## 五、需要了解的术语

🟡 **看得懂就行**

| 术语 | 意思 |
|------|------|
| **持久层** | 操作数据库的那一层（也叫 DAO 层） |
| **框架** | 半成品软件，你在此基础上写代码更高效 |
| **ORM** | Object Relational Mapping，对象关系映射，把数据库表映射成 Java 对象 |
| **MyBatis-Plus** | MyBatis 的增强版（后面学），进一步简化 CRUD |

---

## 🟢 你要记住的（就 3 个）

1. **`@Mapper`** — 标记接口，让 Spring 认识它
2. **`@Select("SQL")`** — 写上查询语句
3. **`List<实体类>`** — 返回值类型，MyBatis 自动把查询结果封装成对象列表

其他的（连接池、配置、依赖坐标）**🟡 查得到就行**。

---

## 练习题

在 `StudentMapper` 中添加一个新方法 `findByName`，根据姓名查询学生：
- 要求使用 `@Select` 注解和 `#{name}` 占位符
- 返回 `List<Student>`

<details>
<summary>参考答案</summary>

```java
@Select("SELECT * FROM student WHERE name = #{name}")
List<Student> findByName(String name);
```

测试：

```java
@Test
public void testFindByName() {
    List<Student> list = studentMapper.findByName("张三");
    for (Student stu : list) {
        System.out.println(stu);
    }
}
```

</details>

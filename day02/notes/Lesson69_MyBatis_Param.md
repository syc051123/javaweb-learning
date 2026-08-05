# 黑马 JavaWeb 第 69 集学习指导：多参数查询 + @Param

> **视频**：P69，MyBatis 多参数查询
> **学完后你能做到**：用 `@Param` 传递多个参数到 SQL

---

## 一、问题

之前传参都是**传一个对象**：

```java
@Insert("INSERT INTO student(...) VALUES(#{studentId}, #{name}...)")
int addStudent(Student student);  // 一个对象包所有参数
```

但有时你不想创建对象，只想传**两个单独的字段**，比如"查学号为 X、姓名为 Y 的学生"。这时候就需要 `@Param`。

---

## 二、`@Param` 注解

🟢 **必须熟练**

### 写法

```java
@Select("SELECT * FROM student WHERE student_id = #{id} AND name = #{name}")
Student findByStudentIdAndName(@Param("id") String studentId, 
                               @Param("name") String name);
```

### 解释

| 部分 | 意思 |
|------|------|
| `@Param("id")` | 给第一个参数起名叫 `id` |
| `@Param("name")` | 给第二个参数起名叫 `name` |
| `#{id}`、`#{name}` | SQL 里通过这个名字引用参数 |
| 返回值 `Student` | 不写 `List<Student>`，因为查出来最多一条 |

### 调用

```java
Student stu = studentMapper.findByStudentIdAndName("20240001", "刘雨萱");
```

---

## 三、不写 `@Param` 行不行？

你的笔记里有一句重点：

> 基于官方骨架创建的 Spring Boot 项目，**接口编译时会保留方法形参名**，`@Param` 可以省略。

什么意思呢？Spring Boot 的 Maven 插件在编译时加了个参数 `-parameters`，它让编译后的字节码**保留方法的参数原名**。

所以你其实可以这样写，也能跑：

```java
// 不写 @Param，靠参数名匹配
@Select("SELECT * FROM student WHERE student_id = #{studentId} AND name = #{name}")
Student findByStudentIdAndName(String studentId, String name);
```

但**企业开发中更保险的做法是加上 `@Param`**，因为：
1. 不是所有项目都保留了 `-parameters` 编译参数
2. 写上 `@Param` 语义更清晰，一眼看出 SQL 参数和 Java 参数的对应关系

---

## 四、练习

### 步骤

**1）在 `StudentMapper` 添加：**

```java
@Select("SELECT * FROM student WHERE student_id = #{studentId} AND name = #{name}")
Student findByStudentIdAndName(@Param("studentId") String studentId, 
                               @Param("name") String name);
```

**2）在 `MyBatisTest` 添加：**

```java
@Test
public void testFindByStudentIdAndName() {
    Student stu = studentMapper.findByStudentIdAndName("20240001", "刘雨萱");
    System.out.println(stu);
}
```

**3）跑一下：**

```bash
mvn test -Dtest=MyBatisTest#testFindByStudentIdAndName
```

---

<details>
<summary>参考答案</summary>

```java
@Select("SELECT * FROM student WHERE student_id = #{studentId} AND name = #{name}")
Student findByStudentIdAndName(@Param("studentId") String studentId, 
                               @Param("name") String name);
```

```java
@Test
public void testFindByStudentIdAndName() {
    Student stu = studentMapper.findByStudentIdAndName("20240001", "刘雨萱");
    System.out.println(stu);
}
```
</details>

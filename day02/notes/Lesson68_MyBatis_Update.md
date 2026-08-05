# 黑马 JavaWeb 第 68 集学习指导：MyBatis 修改操作

> **视频**：P68，MyBatis 修改
> **学完后你能做到**：用 `@Update` 注解更新数据

---

## 一、`@Update` 注解

🟢 **必须熟练**

### 写法

```java
@Update("UPDATE student SET name = #{name}, gender = #{gender}, " +
        "age = #{age}, phone = #{phone} " +
        "WHERE student_id = #{studentId}")
int updateByStudentId(Student student);
```

### 要点

跟 `@Insert` 一样，**传对象**，`#{}` 里写对象属性名：

```java
Student stu = new Student();
stu.setStudentId("2025001");
stu.setName("王小明_修改了");
stu.setGender("男");
stu.setAge(19);
stu.setPhone("13999999999");

int count = studentMapper.updateByStudentId(stu);
```

MyBatis 会自动调用 `stu.getStudentId()`、`stu.getName()` 等取值。

---

## 二、增删改查对比一览

🟢 **必须熟练**

| 操作 | 注解 | SQL 关键词 | 执行方法 | 返回值 |
|------|------|-----------|---------|--------|
| 查询 | `@Select` | SELECT | `executeQuery()` | `List<实体>` |
| 新增 | `@Insert` | INSERT INTO | `executeUpdate()` | `int` |
| 修改 | `@Update` | UPDATE | `executeUpdate()` | `int` |
| 删除 | `@Delete` | DELETE | `executeUpdate()` | `int` |

**规律**：增删改都是 `executeUpdate()`，返回值都是 `int`（影响行数）。

---

## 三、练习

### 步骤

**1）在 `StudentMapper` 添加：**

```java
@Update("UPDATE student SET name = #{name}, gender = #{gender}, " +
        "age = #{age}, phone = #{phone} " +
        "WHERE student_id = #{studentId}")
int updateByStudentId(Student student);
```

**2）在 `MyBatisTest` 添加：**

```java
@Test
public void testUpdate() {
    Student stu = new Student();
    stu.setStudentId("2025001");
    stu.setName("王小明_已修改");
    stu.setGender("男");
    stu.setAge(20);
    stu.setPhone("13999999999");
    int count = studentMapper.updateByStudentId(stu);
    System.out.println("修改了 " + count + " 条记录");
}
```

**3）跑一下：**

```bash
mvn test -Dtest=MyBatisTest#testUpdate
```

---

<details>
<summary>参考答案</summary>

**StudentMapper.java 加：**

```java
@Update("UPDATE student SET name = #{name}, gender = #{gender}, " +
        "age = #{age}, phone = #{phone} " +
        "WHERE student_id = #{studentId}")
int updateByStudentId(Student student);
```

**MyBatisTest.java 加：**

```java
@Test
public void testUpdate() {
    Student stu = new Student();
    stu.setStudentId("2025001");
    stu.setName("王小明_已修改");
    stu.setGender("男");
    stu.setAge(20);
    stu.setPhone("13999999999");
    int count = studentMapper.updateByStudentId(stu);
    System.out.println("修改了 " + count + " 条记录");
}
```
</details>

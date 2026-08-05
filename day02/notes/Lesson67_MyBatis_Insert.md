# 黑马 JavaWeb 第 67 集学习指导：MyBatis 新增操作

> **视频**：P67，MyBatis 新增
> **学完后你能做到**：用 `@Insert` 注解插入数据

---

## 一、`@Insert` 注解

🟢 **必须熟练**

### 写法

```java
@Insert("INSERT INTO student(student_id, name, gender, age, phone) " +
        "VALUES(#{studentId}, #{name}, #{gender}, #{age}, #{phone})")
int insert(Student student);
```

### 跟删除对比

| | `@Delete` | `@Insert` |
|--|-----------|-----------|
| 参数 | 单个值（学号） | **整个对象** |
| SQL | `WHERE student_id = #{studentId}` | `VALUES(#{studentId}, ...)` |
| 参数写法 | `#{}` 里写参数名 | `#{}` 里写**对象属性名** |

---

## 二、传递对象参数

🟢 **必须掌握**

当 SQL 需要的参数很多时（比如插入一条学生记录要 5 个字段），一个一个传太麻烦。MyBatis 允许你**直接传一个对象**，然后在 `#{}` 里写对象的属性名：

```java
// Mapper 接口
@Insert("INSERT INTO student(student_id, name, gender, age, phone) " +
        "VALUES(#{studentId}, #{name}, #{gender}, #{age}, #{phone})")
int insert(Student student);

// 调用时传对象
Student stu = new Student();
stu.setStudentId("2025001");
stu.setName("王小明");
stu.setGender("男");
stu.setAge(18);
stu.setPhone("13800001111");
studentMapper.insert(stu);
```

MyBatis 会自动调用 `stu.getStudentId()`、`stu.getName()` 等 getter 方法取值。

---

## 三、主键返回

🟡 **先了解，后面用得上**

如果你的表有自增主键（比如 `id`），插入后想获取自动生成的 ID，需要加 `@Options`：

```java
@Options(useGeneratedKeys = true, keyProperty = "id")
@Insert("INSERT INTO student(name) VALUES(#{name})")
int insert(Student student);
```

不过你的 `student` 表没有自增主键（用的是 `student_id` 手动赋值），所以暂时用不上这个，知道就行。

---

## 四、练习

### 步骤

**1）在 `StudentMapper` 添加：**

```java
@Insert("INSERT INTO student(student_id, name, gender, age, phone) " +
        "VALUES(#{studentId}, #{name}, #{gender}, #{age}, #{phone})")
int insert(Student student);
```

**2）在 `MyBatisTest` 添加：**

```java
@Test
public void testInsert() {
    Student stu = new Student();
    stu.setStudentId("2025001");
    stu.setName("王小明");
    stu.setGender("男");
    stu.setAge(18);
    stu.setPhone("13800001111");
    int count = studentMapper.insert(stu);
    System.out.println("插入了 " + count + " 条记录");
}
```

**3）跑一下：**

```bash
mvn test -Dtest=MyBatisTest#testInsert
```

---

<details>
<summary>参考答案</summary>

**StudentMapper.java 加：**

```java
@Insert("INSERT INTO student(student_id, name, gender, age, phone) " +
        "VALUES(#{studentId}, #{name}, #{gender}, #{age}, #{phone})")
int insert(Student student);
```

**MyBatisTest.java 加：**

```java
@Test
public void testInsert() {
    Student stu = new Student();
    stu.setStudentId("2025001");
    stu.setName("王小明");
    stu.setGender("男");
    stu.setAge(18);
    stu.setPhone("13800001111");
    int count = studentMapper.insert(stu);
    System.out.println("插入了 " + count + " 条记录");
}
```
</details>

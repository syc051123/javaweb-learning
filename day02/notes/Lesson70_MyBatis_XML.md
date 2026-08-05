# 黑马 JavaWeb 第 70 集学习指导：MyBatis XML 映射配置

> **视频**：P70，XML 映射配置
> **学完后你能做到**：把 SQL 从注解挪到 XML 文件中管理

---

## 零、XML 是什么

🟡 **看得懂就行**

**XML** 全称 **Extensible Markup Language**（可扩展标记语言）。

**拆词根**：
- **Extensible** — 可扩展的（标签你自己定，不像 HTML 标签固定死了）
- **Markup** — 标记（用 `<标签>` 包内容）
- **Language** — 语言

长这样：
```xml
<student>
    <name>张三</name>
    <age>18</age>
</student>
```

MyBatis 用 XML 来写 SQL 语句，一个标签对应一个 SQL。

---

## 一、为什么需要 XML

🟡 **看得懂就行，后面 Tlias 项目会用到**

注解适合简单 SQL，但如果 SQL 很长、有动态条件（`if`、`where`、`foreach`），注解里写就很不方便：

```java
// 注解——SQL 跟 Java 代码混在一起
@Select("SELECT * FROM student WHERE name LIKE #{name} AND age > #{age}")
List<Student> findByNameAndAge(String name, Integer age);
```

XML 方式——**SQL 单独放一个文件**，跟 Java 代码完全分离：

```xml
<!-- StudentMapper.xml -->
<select id="findByNameAndAge" resultType="com.shyc.pojo.Student">
    SELECT * FROM student WHERE name LIKE #{name} AND age > #{age}
</select>
```

---

## 二、三大规范（必须遵守）

🟢 **必须记住**

### 规范 1：同包同名

XML 文件必须跟 Mapper 接口放在**同一个包**下，**文件名相同**。

```
src/main/java/com/shyc/mapper/
├── StudentMapper.java       ← 接口
└── StudentMapper.xml        ← XML 映射文件（同名同包）
```

### 规范 2：namespace 等于接口全限定名

```xml
<mapper namespace="com.shyc.mapper.StudentMapper">
```

告诉 MyBatis："这个 XML 对应的是 `StudentMapper` 这个接口"。

### 规范 3：SQL 标签的 id 等于方法名

```xml
<select id="findAll" resultType="com.shyc.pojo.Student">
    SELECT * FROM student
</select>
```

MyBatis 看到 `id="findAll"`，就知道这个 SQL 对应 `StudentMapper` 接口里的 `findAll()` 方法。

---

## 三、注解 vs XML

| 对比 | 注解 | XML |
|------|------|-----|
| 适用场景 | **简单** CRUD | **复杂** SQL（多表、动态条件） |
| SQL 位置 | Java 代码里 | 独立 XML 文件 |
| 动态 SQL | 不好写（`<if>`、`<where>`） | 原生支持 |
| 维护性 | 改动要重新编译 | 改 XML 不用重新编译 |

**官方建议**：简单的用注解，复杂的用 XML。**同一个方法不能在注解和 XML 里同时配，会冲突。**

---

## 四、练习

### 把 `findAll` 从注解改成 XML

**1）在 `com.shyc.mapper` 包下创建 `StudentMapper.xml`：**

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "https://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.shyc.mapper.StudentMapper">

    <select id="findAll" resultType="com.shyc.pojo.Student">
        SELECT * FROM student
    </select>

</mapper>
```

**2）把 `StudentMapper.java` 里 `findAll()` 上的 `@Select` 注解删掉：**

```java
// 删除这行：@Select("SELECT * FROM student")
List<Student> findAll();
```

**3）跑测试：**

```bash
mvn test -Dtest=MyBatisTest#testFindAll
```

结果应该跟之前一样能查出数据。

---

<details>
<summary>参考答案</summary>

**StudentMapper.java**：
```java
List<Student> findAll();  // 删掉 @Select，方法签名保留
```

**StudentMapper.xml**：
```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "https://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.shyc.mapper.StudentMapper">

    <select id="findAll" resultType="com.shyc.pojo.Student">
        SELECT * FROM student
    </select>

</mapper>
```
</details>

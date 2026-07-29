# 黑马 JavaWeb 第 66 集学习指导：MyBatis 删除操作

> **视频**：P66，MyBatis 删除
> **学完后你能做到**：用 `@Delete` 注解删除数据

---

## 一、`@Delete` 注解

🟢 **必须熟练**

### 写法

```java
@Delete("DELETE FROM student WHERE student_id = #{studentId}")
int deleteByStudentId(String studentId);
```

### 对比 JDBC

| | JDBC | MyBatis |
|--|------|---------|
| 代码量 | 10+ 行 | **1 行注解** |
| 参数 | `pstmt.setString(1, id)` | `#{studentId}` 自动匹配 |
| 返回值 | `int rows = pstmt.executeUpdate()` | 方法返回 `int` |

### 两个注意点

**① 参数用 `#{}` 占位**

```java
@Delete("DELETE FROM student WHERE student_id = #{studentId}")
int deleteByStudentId(String studentId);
//                    ↑ 参数名         ↑ 必须一致
```

`#{studentId}` 里的 `studentId` 必须跟方法参数名 `studentId` 一致。

**② 返回值 `int` 表示影响行数**

```java
int rows = studentMapper.deleteByStudentId("2024001");
System.out.println("删除了 " + rows + " 条");
```

如果返回 0，说明没删到数据（学号不存在）。

---

## 二、`#{}` vs `${}`

🟡 **看得懂就行，面试可能会问**

笔记里专门有一节讲这个区别：

| 符号 | 行为 | 安全性 | 什么时候用 |
|------|------|--------|-----------|
| `#{...}` | 预编译，生成 `?` | ✅ 安全 | **99% 的情况** |
| `${...}` | 直接拼接字符串 | ❌ SQL 注入风险 | 只有表名/列名动态传入时 |

**企业开发强烈建议用 `#{}`**。

---

## 三、动手练习

### 步骤

**1）在 `StudentMapper` 中添加方法：**

```java
@Delete("DELETE FROM student WHERE student_id = #{studentId}")
int deleteByStudentId(String studentId);
```

**2）在 `MyBatisTest` 中添加测试方法：**

```java
@Test
public void testDeleteByStudentId() {
    int rows = studentMapper.deleteByStudentId("20190001");
    System.out.println("删除了 " + rows + " 条数据");
}
```

**3）跑一下验证：**

```bash
mvn test -Dtest=MyBatisTest#testDeleteByStudentId
```

> 注意：删除的数据就没了，你现在的 student 表里 `20190001` 是张三那条重复数据，可以拿它试手。

---

<details>
<summary>参考答案</summary>

**StudentMapper.java** 加：

```java
@Delete("DELETE FROM student WHERE student_id = #{studentId}")
int deleteByStudentId(String studentId);
```

**MyBatisTest.java** 加：

```java
@Test
public void testDeleteByStudentId() {
    int rows = studentMapper.deleteByStudentId("20190001");
    System.out.println("删除了 " + rows + " 条数据");
}
```

</details>

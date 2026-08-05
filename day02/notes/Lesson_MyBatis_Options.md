# MyBatis 补充：获取自增主键（@Options）

> 配合 `@Insert` 使用，在插入后拿回数据库自动生成的 ID

---

## 什么时候用

你的表有 `INT AUTO_INCREMENT PRIMARY KEY` 自增主键时，插入数据不需要手动设 ID，数据库会自动生成。

但问题来了：**插入完我怎么知道生成的是几号？**

```sql
INSERT INTO student(name) VALUES('张三');  
-- 数据库自动生成 id = 101，但我没拿到
```

---

## `@Options` 解决这个问题

```java
@Options(useGeneratedKeys = true, keyProperty = "id")
@Insert("INSERT INTO student(name, gender, age, phone) " +
        "VALUES(#{name}, #{gender}, #{age}, #{phone})")
int insert(Student student);
```

| 参数 | 意思 |
|------|------|
| `useGeneratedKeys = true` | 告诉 MyBatis："我要拿数据库自动生成的 ID" |
| `keyProperty = "id"` | "把生成的 ID 存到对象的 `id` 属性里" |

**使用效果**：

```java
Student stu = new Student();
stu.setName("张三");
// stu.setId(...)  // 不用设，数据库自动生成

studentMapper.insert(stu);

System.out.println(stu.getId());  // ✅ 这里就能拿到自动生成的ID了！
```

---

## 为什么咱们没用上

因为 `student` 表没有自增主键，它用的是 `student_id`（字符串，手动赋值）。

但到了 **Tlias 项目**，所有表都会像这样：

```sql
CREATE TABLE emp (
    id INT AUTO_INCREMENT PRIMARY KEY,  -- 自增主键
    username VARCHAR(20),
    ...
);
```

到时候你每插入一条员工，都要用 `@Options` 拿回生成的 ID。

---

## 你现在要掌握什么

🟡 **知道有这个东西就行**，到 Tlias 项目里我会带着你实际用一遍。

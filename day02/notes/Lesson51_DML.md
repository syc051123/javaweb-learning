# 黑马 JavaWeb 第 51 集学习指导：DML — 增删改数据

> **视频**：BV1yGydYEE3H，P51
> **学完后你能做到**：往表里插入数据、修改数据、删除数据

---

## 一、DML 是什么

🟢 **必须熟练**

**DML** = Data Manipulation Language，数据操纵语言。就是**增、删、改**数据。

| 操作 | SQL 关键字 | 英文 |
|------|-----------|------|
| 增加 | `INSERT` | 插入 |
| 修改 | `UPDATE` | 更新 |
| 删除 | `DELETE` | 删除 |

---

## 二、INSERT — 插入数据

### 语法

```sql
-- 给指定字段插入数据
INSERT INTO 表名 (字段1, 字段2) VALUES (值1, 值2);

-- 给所有字段插入数据（省略字段名）
INSERT INTO 表名 VALUES (值1, 值2, 值3, ...);

-- 批量插入
INSERT INTO 表名 (字段1, 字段2) VALUES (值1, 值2), (值3, 值4), (值5, 值6);
```

### 实战：往 student 表插入数据

打开 DataGrip，选中 `javaweb_learning` 数据库，执行：

```sql
-- 插入一条完整数据
INSERT INTO student (id, name, gender, age, phone)
VALUES (1, '史彦超', '男', 21, '13800001111');

-- 插入多条数据
INSERT INTO student (id, name, gender, age, phone)
VALUES
(2, '张三', '男', 25, '13800002222'),
(3, '李四', '女', 22, '13800003333'),
(4, '王五', '男', 24, '13800004444');
```

### 注意事项

🟡 **知道就行**

```sql
-- 字符串要用引号括起来
INSERT INTO student VALUES (5, '赵六', '男', 20, '13800005555');

-- 字段名和值要一一对应
INSERT INTO student (name, age) VALUES ('测试', 18);  -- 其他字段为 NULL
```

---

## 三、UPDATE — 修改数据

### 语法

```sql
UPDATE 表名 SET 字段1=值1, 字段2=值2 WHERE 条件;
```

**⚠️ 不加 WHERE 会修改全部数据！**

### 实战

```sql
-- 把张三的年龄改成 26
UPDATE student SET age = 26 WHERE name = '张三';

-- 修改手机号
UPDATE student SET phone = '13999998888' WHERE id = 1;

-- 查看修改结果
SELECT * FROM student;
```

---

## 四、DELETE — 删除数据

### 语法

```sql
DELETE FROM 表名 WHERE 条件;
```

**⚠️ 不加 WHERE 会删除全部数据！**

### 实战

```sql
-- 删除 id 为 4 的学生
DELETE FROM student WHERE id = 4;

-- 删除年龄大于 30 的学生
DELETE FROM student WHERE age > 30;
```

---

## 五、小练习

在 DataGrip 里执行以下操作：

1. 往 student 表插入一条你自己（学号、姓名、性别、年龄、手机号）
2. 把李四的性别改成 `'男'`
3. 删除手机号以 `138` 开头的学生（提示：试试 `LIKE`）

<details>
<summary>点击查看答案（参考）</summary>

```sql
-- 1. 插入自己
INSERT INTO student (id, name, gender, age, phone)
VALUES (6, '史彦超', '男', 21, '13800006666');

-- 2. 修改李四性别
UPDATE student SET gender = '男' WHERE name = '李四';

-- 3. 删除手机号 138 开头的学生
DELETE FROM student WHERE phone LIKE '138%';
```

</details>

---

## 六、一句话总结

> **INSERT 加数据、UPDATE 改数据、DELETE 删数据。三个操作共同点：不加 WHERE = 操作全部数据（很危险！）。**

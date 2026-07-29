# 黑马 JavaWeb 第 52 集学习指导：DQL — 基础查询

> **视频**：BV1yGydYEE3H，P52
> **学完后你能做到**：从表中查询数据、过滤、排序

---

## 一、DQL 是什么

🟢 **必须熟练（面试考最多）**

**DQL** = Data Query Language，数据查询语言。就是 `SELECT` 语句。

面试 SQL 题 80% 都是 DQL，**这是 SQL 里最重要的部分。**

---

## 二、基本查询

### 1. 查询全部

```sql
SELECT * FROM student;
```

`*` 代表所有字段。平时练习用，正式代码里一般写具体字段名。

### 2. 查询指定字段

```sql
SELECT name, age FROM student;
```

### 3. 查询时起别名

```sql
SELECT name AS 姓名, age AS 年龄 FROM student;
```

`AS` 可以省略，但高手建议写上，更清晰。

### 4. 去重查询

```sql
-- 查询所有不重复的年龄
SELECT DISTINCT age FROM student;
```

---

## 三、条件查询 WHERE

🟢 **必须熟练**

```sql
SELECT 字段 FROM 表名 WHERE 条件;
```

### 比较运算符

| 运算符 | 说明 |
|--------|------|
| `=` | 等于 |
| `!=` 或 `<>` | 不等于 |
| `>` / `<` / `>=` / `<=` | 大于/小于/大于等于/小于等于 |
| `BETWEEN ... AND ...` | 在某个范围（含两端） |
| `IN (...)` | 在指定值列表里 |
| `LIKE` | 模糊匹配 |

### 实战

```sql
-- 查询年龄等于 20 的学生
SELECT * FROM student WHERE age = 20;

-- 查询年龄大于 20 的学生
SELECT * FROM student WHERE age > 20;

-- 查询年龄在 18 到 20 之间的学生
SELECT * FROM student WHERE age BETWEEN 18 AND 20;

-- 查询姓张的学生（模糊匹配）
SELECT * FROM student WHERE name LIKE '张%';

-- 查询名字带"雨"的学生
SELECT * FROM student WHERE name LIKE '%雨%';

-- 查询年龄是 18 或 20 或 22 的学生
SELECT * FROM student WHERE age IN (18, 20, 22);
```

**`LIKE` 的通配符：**
- `%` 匹配任意多个字符
- `_` 匹配正好一个字符

---

## 四、排序 ORDER BY

🟢 **必须熟练**

```sql
SELECT 字段 FROM 表名 ORDER BY 字段 ASC/DESC;
```

- `ASC` — 升序（默认，不写就是升序）
- `DESC` — 降序

### 实战

```sql
-- 按年龄从小到大排
SELECT * FROM student ORDER BY age ASC;

-- 按年龄从大到小排
SELECT * FROM student ORDER BY age DESC;

-- 先按性别分组，再按年龄降序
SELECT * FROM student ORDER BY gender, age DESC;
```

---

## 五、分页查询 LIMIT

🟢 **必须熟练**

```sql
SELECT 字段 FROM 表名 LIMIT 起始索引, 每页条数;
```

**起始索引 = (页码 - 1) × 每页条数**

### 实战

```sql
-- 第1页：前5条
SELECT * FROM student LIMIT 0, 5;

-- 第2页：跳过前5条，取5条
SELECT * FROM student LIMIT 5, 5;

-- 第3页
SELECT * FROM student LIMIT 10, 5;
```

---

## 六、小练习

用你的 student 表试试：

1. 查询所有女生的名字和年龄
2. 查询年龄大于 19 的男生
3. 按年龄降序排，只显示前 5 条
4. 查询姓"陈"的学生
5. 查询年龄在 18~20 之间的女生

<details>
<summary>点击查看答案</summary>

```sql
-- 1. 所有女生的名字和年龄
SELECT name, age FROM student WHERE gender = '女';

-- 2. 年龄大于 19 的男生
SELECT * FROM student WHERE gender = '男' AND age > 19;

-- 3. 年龄降序前5条
SELECT * FROM student ORDER BY age DESC LIMIT 0, 5;

-- 4. 姓陈的
SELECT * FROM student WHERE name LIKE '陈%';

-- 5. 年龄18~20的女生
SELECT * FROM student WHERE gender = '女' AND age BETWEEN 18 AND 20;
```

</details>

---

## 七、一句话总结

> **SELECT + WHERE + ORDER BY + LIMIT = 90% 的查询需求。模糊查用 LIKE %，范围查用 BETWEEN，排序用 ORDER BY，分页用 LIMIT。**

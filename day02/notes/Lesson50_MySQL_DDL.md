# 黑马 JavaWeb 第 50 集学习指导：MySQL 概述 & DDL

> **视频**：BV1yGydYEE3H，P50
> **章节位置**：数据库章节第 1 集
> **学完后你能做到**：理解数据库基本概念，会用 DDL 创建数据库和表

---

## 一、核心概念

### 1. 数据库是什么

🔴 **理解概念即可**

程序运行时的数据存在内存里，一关程序就丢了。数据库就是**让数据永久的、有组织地存下来**的地方。

**数据流转：**

```
程序（Java） → SQL 语句 → DBMS（MySQL） → 数据库（存储数据）
```

- **DB** = DataBase，数据库
- **DBMS** = DataBase Management System，数据库管理系统（MySQL 就是 DBMS）
- **SQL** = Structured Query Language，操作数据库的语言

### 2. 关系型数据库（RDBMS）

🔴 **理解概念即可**

**拆词根**：**Relation** = 关系、关联。关系型数据库就是用"表"来存数据的数据库。

**类比**：Excel 表格——一个文件里有多个 Sheet（表），每个 Sheet 有行有列，表跟表之间还能通过某个字段关联。

```
表名：emp（员工）
┌───┬──────────┬────┬───────────┐
│id│ username  │ age│  entry_date│
├───┼──────────┼────┼───────────┤
│ 1 │ 张三     │ 25 │ 2024-01-01│
│ 2 │ 李四     │ 30 │ 2024-02-15│
└───┴──────────┴────┴───────────┘
   ↑            ↑
主键（唯一标识）   字段（列）
```

### 3. 数据模型

🔴 **理解即可**

```
MySQL 服务器 → 可以管理多个 数据库（Database）
                               ↓
                        每个数据库里有多个 表（Table）
                               ↓
                        每个表里有 行（Row）和 列（Column）
```

---

## 二、你已经完成的环境搭建

| 步骤 | 状态 |
|------|------|
| 安装 MySQL 8.0 | ✅ 已装 |
| 安装 DataGrip | ✅ 已装 |
| 连接 DataGrip → MySQL | ✅ 已连 |
| 创建数据库 `javaweb_learning` | ✅ 已创建 |

---

## 三、DDL — 操作数据库和表

🟢 **必须熟练**

**DDL** = Data Definition Language，数据定义语言。用来**创建、修改、删除**数据库和表的结构。

### 1. 数据库操作

```sql
-- 创建数据库
CREATE DATABASE 数据库名;

-- 查看所有数据库
SHOW DATABASES;

-- 切换/使用某个数据库
USE 数据库名;

-- 删除数据库（慎用！）
DROP DATABASE 数据库名;
```

### 2. 表操作

#### 创建表

```sql
CREATE TABLE 表名 (
    字段名 字段类型 [约束],
    字段名 字段类型 [约束],
    ...
);
```

**实战：** 在你的 `javaweb_learning` 库里创建一个学生表

先在 DataGrip 左侧双击 `javaweb_learning` 选中它（变成蓝色），然后新建控制台执行：

```sql
CREATE TABLE student (
    id      INT             COMMENT '学号',
    name    VARCHAR(10)     COMMENT '姓名',
    gender  CHAR(1)         COMMENT '性别',
    age     TINYINT         COMMENT '年龄',
    phone   VARCHAR(11)     COMMENT '手机号'
);
```

**常见数据类型：**

| 类型 | 说明 | 例子 |
|------|------|------|
| `INT` | 整数 | 年龄、数量、ID |
| `VARCHAR(n)` | 字符串，最长 n 个字符 | 用户名、姓名 |
| `CHAR(n)` | 定长字符串 | 手机号（固定11位） |
| `TINYINT` | 小整数（0~255） | 年龄、状态 |
| `DATE` | 日期 | 入职日期 |
| `DATETIME` | 日期+时间 | 创建时间、更新时间 |

#### 查看表

```sql
-- 查看当前数据库有哪些表
SHOW TABLES;

-- 查看表结构
DESC 表名;
```

#### 删除表（慎用！）

```sql
DROP TABLE 表名;
```

---

## 四、小练习

在 DataGrip 里，选中 `javaweb_learning` 数据库，执行：

```sql
SHOW TABLES;
```

确认能看到 `student` 表。

<details>
<summary>点击查看答案</summary>
执行成功的话，会显示：

```
+----------------------------+
| Tables_in_javaweb_learning |
+----------------------------+
| student                    |
+----------------------------+
```
</details>

---

## 五、一句话总结

> **关系型数据库 = Excel 表格集合。DDL 就是用来创建数据库和表的"装修工具"——CREATE 建、DROP 拆、SHOW/DESC 看。**

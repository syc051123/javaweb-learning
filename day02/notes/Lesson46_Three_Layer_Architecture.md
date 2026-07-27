# 黑马 JavaWeb 第 46 集学习指导：三层架构

> **视频**：BV1yGydYEE3H，P46（时长 18:47）
> **学完后你能搞懂**：为什么代码要分层，Controller、Service、Dao 各管什么

---

## 一、核心概念

### 1. 为什么要分层？

你的 day02 目前只有一层：`HelloController` 直接返回 `"hello world"`。

但如果项目变大，比如做一个**员工管理系统**，所有代码都塞在一个类里会怎样？

```java
@RestController
public class EmployeeController {
    
    @GetMapping("/list")
    public List<Employee> list() {
        // 1. 接收请求  ✅
        // 2. 写 SQL 查数据库  😵
        // 3. 判断权限  😵
        // 4. 记录日志  😵
        // 5. 返回结果  ✅
    }
}
```

一个方法做五件事，乱成一锅粥。**三层架构就是把这些事分到三个不同的"部门"去做。**

### 2. 三层架构长什么样

```
浏览器请求
    ↓
┌──────────────────┐
│  Controller 层    │  ← 接待员：接收请求、返回结果
│  （表现层）        │     不干活，只管传话
└──────┬───────────┘
       ↓
┌──────────────────┐
│  Service 层       │  ← 业务员：处理业务逻辑
│  （业务逻辑层）     │     判断、计算、权限校验
└──────┬───────────┘
       ↓
┌──────────────────┐
│  Dao 层           │  ← 跑腿的：操作数据库
│  （数据访问层）     │     增删改查
└──────────────────┘
```

### 3. 每一层的职责

| 层级 | 类名约定 | 职责 | 日常类比 |
|------|---------|------|---------|
| **Controller** | `XxxController` | 接收请求、调用 Service、返回结果 | **前台接待**——客人来了，接电话，转给对应的业务员 |
| **Service** | `XxxService` | 处理业务逻辑（判断、计算、权限） | **业务员**——客人说要办什么事，业务员判断能不能办、怎么办 |
| **Dao** | `XxxDao` 或 `XxxMapper` | 操作数据库（增删改查） | **仓库管理员**——业务员说要什么数据，就去仓库里拿 |

### 4. 数据流转方向

```
用户请求 → Controller → Service → Dao → 数据库
                                               ↓
用户收到 ← Controller ← Service ← Dao ← 数据库返回数据
```

**数据是单向流动的：**
- **请求方向**：Controller → Service → Dao
- **响应方向**：Dao → Service → Controller

**关键规则**：
- Controller 只能调 Service，不能直接调 Dao
- Service 只能调 Dao，不能直接操作数据库
- 每一层只跟自己下面那一层打交道

---

## 二、代码示例（还是以你的 day02 为例）

假如你不是返回 "hello world"，而是从数据库查一个用户列表。

### 未分层（反面教材）

```java
@RestController
public class UserController {
    
    @GetMapping("/users")
    public List<User> list() {
        // 直接在这里写 SQL 查询
        // 又写 SQL 又处理请求，混在一起
    }
}
```

### 分层后（正确写法）

**Controller 层** — 只负责接收请求和返回结果

```java
@RestController
public class UserController {
    
    @GetMapping("/users")
    public List<User> list() {
        // 调 Service，不问细节
        return userService.listAll();
    }
}
```

**Service 层** — 只负责业务逻辑

```java
public class UserService {
    
    public List<User> listAll() {
        // 做一些判断、校验...
        // 然后调 Dao 拿数据
        return userDao.findAll();
    }
}
```

**Dao 层** — 只负责操作数据库

```java
public class UserDao {
    
    public List<User> findAll() {
        // 执行 SQL：SELECT * FROM user
        // 返回数据
    }
}
```

---

## 三、分层的好处

| 好处 | 白话解释 |
|------|---------|
| **各司其职** | 每层只管自己的事，不越界 |
| **好维护** | 改 SQL 只改 Dao，改逻辑只改 Service，互不影响 |
| **好测试** | 可以单独测每一层，不用把整个项目跑起来 |
| **可复用** | 同一个 Service 可以同时给 Web 端和移动端用 |

---

## 四、练习题

**Q1**：三层架构是哪三层？各自的关键字是什么（Controller/Service/Dao）？

**Q2**：数据在三层之间是怎么流动的？

**Q3**：Controller 能不能直接调 Dao？

**Q4**：如果要改一个 SQL 查询语句，应该改哪一层？

<details>
<summary>点击查看答案</summary>

**A1**：
- **Controller 层**（表现层）— 接待请求
- **Service 层**（业务逻辑层）— 处理逻辑
- **Dao 层**（数据访问层）— 操作数据库

**A2**：
- 请求方向：Controller → Service → Dao
- 响应方向：Dao → Service → Controller

**A3**：**不能**。Controller 只能调 Service，不能跳过 Service 直接调 Dao。这是规矩，跳了就会破坏分层结构。

**A4**：**Dao 层**。SQL 操作数据库属于数据访问的职责。

</details>

---

## 五、一句话总结

> **三层架构就是把代码分成三个部门：Controller 接待请求、Service 处理业务、Dao 操作数据库，各管各的，互不越界。**

---

## 下一集预告

**P47《IOC & DI — 控制反转 & 依赖注入》**：三层之间怎么互相"认识"？Spring 的 IOC 容器是怎么管理对象的？

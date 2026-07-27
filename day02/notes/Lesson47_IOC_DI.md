# 黑马 JavaWeb 第 47 集学习指导：IOC & DI 入门

> **视频**：BV1yGydYEE3H，P47
> **章节位置**：分层解耦第 2 集
> **学完后你能搞懂**：为什么不用 `new` 也能拿到对象？Spring 的 IOC 容器怎么帮你管理对象？

---

## 一、问题背景：为什么要搞 IOC 和 DI？

你现在写三层架构，是这么做的：

```java
// Controller 里自己 new Service
private UserService userService = new UserServiceImpl();

// Service 里自己 new Dao
private UserDao userDao = new UserDaoImpl();
```

**问题**：如果以后 `UserServiceImpl` 要换成 `UserServiceImpl2`，你得到 Controller 里改代码。这叫**耦合**。

**解耦思路**：

```
原来：  Controller  →  自己 new Service    （耦合）
解耦后：Controller  →  找容器要 Service   （不耦合）
                          ↑
                     IOC 容器（专门管对象）
```

---

## 二、核心概念

### 1. IOC - 控制反转

🟢 **必须熟练（概念）**

> **对象的创建权**从程序员手里**反转**给 Spring 容器。

**大白话**：以前你要对象就自己 `new`，现在 Spring 帮你 `new` 好了放在一个"大箱子"里，你直接拿就行。

**代码对比**：

```java
// 之前：自己控制
private UserService userService = new UserServiceImpl();

// 之后：Spring 控制（你只管声明，别管创建）
private UserService userService;
```

### 2. DI - 依赖注入

🟢 **必须熟练（概念）**

> Spring 容器把你需要的对象，**自动注入**到你写的代码里。

**大白话**：你跟 Spring 说"我要一个 UserService"，Spring 就从箱子里拿出来塞给你。

### 3. Bean

🟡 **看得懂就行**

> IOC 容器中管理的对象就叫 **Bean**。

---

## 三、实操：改造你的项目实现 IOC + DI

### Step 1：在 Service 和 Dao 上声明 Bean

🟢 **必须熟练**

**UserServiceImpl** — 加 `@Service` 注解

```java
@Service                          // ← 告诉 Spring：把这个类交给你管了
public class UserServiceImpl implements UserService {

    @Autowired                    // ← 告诉 Spring：我需要一个 UserDao
    private UserDao userDao;

    @Override
    public List<User> findAll() {
        // ... 业务逻辑（不用 new 了，直接用注入好的 userDao）
    }
}
```

**UserDaoImpl** — 加 `@Repository` 注解

```java
@Repository                       // ← 告诉 Spring：把这个类交给你管了
public class UserDaoImpl implements UserDao {
    // ...
}
```

| 注解 | 用在哪 | 说明 |
|------|--------|------|
| `@Component` | 通用 | 基础注解，不属于三层时用 |
| `@Controller` | Controller 层 | 你已经在用了（@RestController 里包含了） |
| `@Service` | Service 层 | 🟢 **必须熟** |
| `@Repository` | Dao 层 | 🟡 知道就行，后面 MyBatis 用得少 |

### Step 2：用 `@Autowired` 注入依赖

🟢 **必须熟练**

**UserController** — 删掉 `new`，改成注入

```java
@RestController
public class UserController {

    @Autowired                     // ← 让 Spring 注入 UserService
    private UserService userService;

    @RequestMapping("/list")
    public List<User> list() {
        return userService.findAll();
    }
}
```

**三种注入方式对比**：

```java
// 方式一：属性注入（最常用，代码最简洁）
@Autowired
private UserService userService;

// 方式二：构造器注入（官方推荐，更规范）
private final UserService userService;
public UserController(UserService userService) {
    this.userService = userService;
}

// 方式三：setter 注入（用得少）
@Autowired
public void setUserService(UserService userService) { ... }
```

🟢 **先掌握方式一，以后熟悉了再切方式二。**

### Step 3：组件扫描（已经帮你配好了）

🔴 **了解即可，不用管**

`@SpringBootApplication` 包含了 `@ComponentScan`，默认扫描启动类所在包及其子包。

```
com.shyc/                      ← 启动类在这里
├── Application.java
├── controller/                ← 会扫描
├── service/                   ← 会扫描
└── dao/                       ← 会扫描
```

**只要你的类都在 `com.shyc` 下，就不用操心组件扫描。**

---

## 四、多个实现类时怎么办

🔴 遇到时再回来看，不需要现在背

如果有一个 `UserServiceImpl2`，容器里有两个相同类型的 Bean：

```java
@Service
public class UserServiceImpl implements UserService { ... }

@Service
public class UserServiceImpl2 implements UserService { ... }
```

Spring 不知道注入哪个，会报错。三种解决方式：

| 方案 | 说明 |
|------|------|
| `@Primary` | 指定默认的那个 |
| `@Qualifier("bean名")` | 配合 `@Autowired` 指定具体名字 |
| `@Resource(name = "bean名")` | JDK 提供的，按名称注入 |

---

## 五、改造后的项目结构

```
day02/src/main/java/com/shyc/
├── Application.java
├── controller/
│   ├── HelloController.java
│   └── UserController.java       ← @Autowired 注入 Service
├── service/
│   ├── UserService.java          ← 接口
│   └── impl/
│       └── UserServiceImpl.java  ← @Service
├── dao/
│   ├── UserDao.java              ← 接口
│   └── impl/
│       └── UserDaoImpl.java      ← @Repository
└── pojo/
    └── User.java
```

---

## 六、练习题

**Q1**：IOC 和 DI 分别是什么？用一句话说清楚。

**Q2**：把对象交给 Spring 管理，需要在类上加什么注解？（分别说出三层各用什么注解）

**Q3**：`@Autowired` 是干什么用的？

**Q4**：为什么你之前没写 `@ComponentScan` 但 Bean 还是生效了？

<details>
<summary>点击查看答案</summary>

**A1**：
- **IOC**（控制反转）：对象的创建权从程序员交给 Spring 容器
- **DI**（依赖注入）：Spring 容器把对象自动注入到你需要的地方

**A2**：
- Controller 层：`@Controller`（`@RestController` 已经包含了）
- Service 层：`@Service`
- Dao 层：`@Repository`
- 不属于三层：`@Component`

**A3**：依赖注入——告诉 Spring"我需要这个类型的对象，帮我注入进来"。

**A4**：因为 `@SpringBootApplication` 里已经包含了 `@ComponentScan`，默认扫描启动类所在包 `com.shyc` 及其子包。

</details>

---

## 七、一句话总结

> **IOC = 把对象交给 Spring 管；DI = Spring 自动把对象注入给你。加 `@Service`/`@Repository` 声明 Bean，加 `@Autowired` 注入使用。**

---

## 下一集预告

**P48《IOC & DI 详解》**：深入 Bean 声明细节、三种注入方式对比、@Primary/@Qualifier/@Resource 的区别。

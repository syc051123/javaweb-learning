# 黑马 JavaWeb 第 48 集学习指导：IOC 详解

> **视频**：BV1yGydYEE3H，P48
> **章节位置**：分层解耦第 3 集
> **学完后你能搞懂**：四个注解到底怎么选、组件扫描是什么

---

## 一、Bean 的声明 — 四个注解怎么选

🟢 **必须熟练**

上集学了 `@Service` 和 `@Repository`，但 Spring 一共提供了四个声明 Bean 的注解：

| 注解 | 用在哪 | 含义 |
|------|--------|------|
| `@Component` | 不属于三层的通用类 | 基础注解，其他三个都是它的"分身" |
| `@Controller` | Controller 层 | 你已经在用了（`@RestController` 里包含了它） |
| `@Service` | Service 层 | 🟢 业务层必须用这个 |
| `@Repository` | Dao 层 | 🟡 数据层用这个（后面 MyBatis 用得少） |

**本质：功能完全一样，都是"把类交给 Spring 管"。区别只是名字不同，让别人一眼看出这个类属于哪一层。**

```java
@Component  // Spring 知道这是个 Bean，但看不出是哪层的
public class SomeUtil { ... }

@Service    // Spring 知道：哦，这是业务层的
public class UserServiceImpl implements UserService { ... }
```

**为什么要有四个？** 就像你给文件夹起名——你当然可以全部叫"文档"，但如果分成"学习资料"、"工作文件"、"照片"，找起来方便得多。

---

## 二、组件扫描 — 为什么注解有时不生效

🟡 **看得懂就行，不用背**

### 2.1 问题

你在类上加了 `@Service`，但启动后没生效，为什么？

**答案：还需要被组件扫描扫到。**

### 2.2 组件扫描的范围

```java
@SpringBootApplication  // 里面包含了 @ComponentScan
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```

`@ComponentScan` 默认扫描**启动类所在包及其子包**：

```
com/shyc/                  ← 启动类在这里（Application.java）
├── Application.java       ← @SpringBootApplication
├── controller/            ← ✅ 会扫描
├── service/               ← ✅ 会扫描
├── dao/                   ← ✅ 会扫描
└── pojo/                  ← ✅ 会扫描（虽然 pojo 没加注解）
```

### 2.3 如果放错位置会怎样

```java
com/
├── shyc/
│   └── Application.java     ← 启动类在这里
└── other/                    ← ❌ 不会被扫描！
    └── SomeService.java      ← 加了 @Service 也没用
```

**拆词根**：**ComponentScan** = Component（组件） + Scan（扫描） → 扫描组件。

**大白话**：Spring 说"我不知道你加了注解的类在哪，你告诉我从哪开始找"——`@ComponentScan` 就是告诉 Spring"从这开始往下翻"。

### 2.4 你现在需要记住的

🟢 **只要你的所有代码都在 `com.shyc` 包（或子包）里，就不用操心组件扫描。** 这是 SpringBoot 帮你配好的默认行为。

---

## 三、练习题

**Q1**：`@Component`、`@Service`、`@Repository` 功能上有什么区别？

**Q2**：为什么有时候加了 `@Service` 但 Bean 没生效？

**Q3**：你的 `UserServiceImpl` 在 `com.shyc.service.impl` 包下，会被扫描到吗？

<details>
<summary>点击查看答案</summary>

**A1**：功能**完全一样**，都是把类交给 Spring 容器管理。区别只是语义上的——`@Service` 表示业务层、`@Repository` 表示数据层、`@Component` 表示通用组件。

**A2**：最常见的原因是**没有被组件扫描覆盖**。类所在的包不在启动类所在包及其子包范围内。

**A3**：**会被扫描到**。因为启动类 `Application.java` 在 `com.shyc` 包下，而 `com.shyc.service.impl` 是它的子包。

</details>

---

## 四、一句话总结

> **四个注解功能一样、语义不同；组件扫描默认从启动类所在包开始往下找；别把类放到包外面去就行。**

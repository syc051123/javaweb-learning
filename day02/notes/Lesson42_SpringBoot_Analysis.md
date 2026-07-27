# 黑马 JavaWeb 第 42 集学习指导：SpringBootWeb 入门 - 入门解析

> **视频**：BV1yGydYEE3H，P42（时长 30:07）
> **章节位置**：SpringBootWeb 章节第 2 集（41 集建项目，42 集讲原理）
> **学完后你能搞懂**：`@SpringBootApplication` 到底干了啥、内嵌 Tomcat 怎么来的、自动配置是什么鬼

---

## 一、大白话讲核心概念

### 1. @SpringBootApplication 不是一个人在战斗

P41 你在启动类上只贴了一个注解 `@SpringBootApplication`，但它其实是**三个注解的合体**：

```java
@SpringBootApplication     ← 这其实是一个"三合一"大礼包
```

拆开来看：

| 注解 | 大白话解释 |
|------|-----------|
| `@SpringBootConfiguration` | 标记这个类是"管配置的"（里面装了 `@Configuration`，意思是"这个类里有配置信息"） |
| `@EnableAutoConfiguration` | **自动配置**——根据你加的依赖，自动帮你配好相应的东西 |
| `@ComponentScan` | **组件扫描**——扫描当前包及其子包，找到所有 `@RestController`、`@Service` 等注解的类，把它们加进 Spring 容器里管理 |

**类比**：你去吃自助火锅——
- `@SpringBootConfiguration`：你拿了个盘子（这个类是用来装东西的）
- `@EnableAutoConfiguration`：厨房根据你选的锅底，自动配好调料、配菜（你加了 `spring-boot-starter-web`，它就自动配好 Tomcat、SpringMVC）
- `@ComponentScan`：服务员在整个餐厅里找你的"号牌"（加了 `@RestController`、`@Service` 的类），把它们叫过来伺候你

### 2. 为什么不用配置 Tomcat 了？

以前用原生 Spring 做 Web 开发，你要：
1. 下载 Tomcat
2. 配置 Tomcat（端口、编码等）
3. 把项目打成 war 包丢进 Tomcat 的 webapps 目录
4. 启动 Tomcat

SpringBoot 的 `spring-boot-starter-web` 里**自带了 Tomcat 的 jar 包**，启动时直接在你代码里 new 一个 Tomcat 出来运行。这就是**内嵌 Tomcat**。

**类比**：以前你要去网吧上网（装 Tomcat），SpringBoot 直接把一台小电脑嵌在你的项目里，插电就能用。

### 3. 自动配置是怎么回事？

你只加了一个 `spring-boot-starter-web`，SpringBoot 就自动给你配好了：
- Tomcat 服务器（默认 8080 端口）
- SpringMVC 框架（处理请求、返回数据）
- Jackson（把 Java 对象转成 JSON）
- ……

它怎么做到的？SpringBoot 的 jar 包里藏了一个文件叫 `spring.factories`，里面写了一大串"如果发现这个依赖，就自动配这些类"的规则。

**大白话**：你告诉 SpringBoot"我要吃火锅"（加了 starter-web），它就去翻自己的"菜单"（spring.factories），自动把锅底、调料、配菜全给你端上来。

---

## 二、不用写代码，但需要理解这张图

```
浏览器 → 请求 localhost:8080/hello
            ↓
    内嵌 Tomcat（监听 8080 端口）
            ↓
      Spring 容器（管理所有组件）
            ↓
    @RestController 找到 @GetMapping("/hello")
            ↓
      执行 hello() 方法 → 返回 "hello world"
            ↓
      Spring 把结果返回给浏览器
```

---

## 三、练习题

**Q1**：`@SpringBootApplication` 是哪个三个注解的合体？各自的作用是什么？

**Q2**：为什么 SpringBoot 项目不需要单独装 Tomcat？

**Q3**：`@ComponentScan` 不写会怎样？

<details>
<summary>点击查看答案</summary>

**A1**：
- `@SpringBootConfiguration` — 标记这是配置类
- `@EnableAutoConfiguration` — 根据依赖自动配置
- `@ComponentScan` — 扫描当前包及其子包，找到所有组件

**A2**：因为 `spring-boot-starter-web` 依赖里自带了 Tomcat 的 jar 包，SpringBoot 启动时直接内嵌运行 Tomcat，不需要单独安装。

**A3**：Spring 就找不到你写的 `@RestController`、`@Service` 这些组件，访问 `http://localhost:8080/hello` 会返回 404。因为 Spring 不知道有个 `HelloController` 存在。

</details>

---

## 四、一句话总结

> **`@SpringBootApplication` = 配置 + 自动配置 + 组件扫描，三合一；spring-boot-starter-web 自带内嵌 Tomcat，所以不用单独装服务器。**

---

## 下一集预告

**P43《HTTP 协议-概述》**：当浏览器和服务器对话时，它们之间说的是什么语言？这就是 HTTP 协议。

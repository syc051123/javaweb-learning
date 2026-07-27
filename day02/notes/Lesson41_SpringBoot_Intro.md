# 黑马 JavaWeb 第 41 集学习指导：SpringBootWeb 入门 - 入门程序

> **视频**：BV1yGydYEE3H，P41（时长 28:16）
> **学完后你能做到**：创建 SpringBoot 项目、写 Controller 返回数据、跑起来用浏览器访问

---

## 一、大白话讲核心概念

### SpringBoot 是啥

以前用 Spring 做 Web 开发，要配一堆 XML、装 Tomcat、找各种 jar 包，折腾半天。SpringBoot 把这一切**自动搞定**了——加一个依赖，写一个注解，直接跑起来。

**类比**：以前煮泡面要自己烧水、自己放调料、自己掐时间（原生 Spring）。SpringBoot 等于**自热火锅**——打开盖子，加水，等 15 分钟就能吃。

### 创建 SpringBoot Web 项目就三步

```
改 pom.xml（加父工程 + web依赖）→ 写启动类 → 写 Controller
```

---

## 二、你实际做了什么

### 1. pom.xml 加了两个东西

**父工程 `<parent>`**
```xml
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>3.2.5</version>
</parent>
```
作用是：SpringBoot 帮你管好所有 jar 包的版本号，你加依赖时**不用写 version** 了。

**Web 起步依赖**
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```
这一个依赖 = 以前十几个 jar（SpringMVC + 内嵌 Tomcat + Jackson JSON 处理），全包了。

### 2. 启动类 Application.java

```java
package com.shyc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```

| 关键代码 | 大白话解释 |
|---------|-----------|
| `@SpringBootApplication` | 贴个标签告诉 Spring："这是我家的门口，从这里启动" |
| `SpringApplication.run(...)` | 启动内嵌 Tomcat 服务器，开始监听浏览器发来的请求 |

### 3. Controller HelloController.java

```java
package com.shyc.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "hello world";
    }
}
```

| 注解 | 大白话解释 |
|------|-----------|
| `@RestController` | 这个类是"前台接待员"，专门处理浏览器来的请求 |
| `@GetMapping("/hello")` | 门牌号——浏览器访问 `http://localhost:8080/hello` 时，执行下面的方法 |

### 启动后效果

浏览器访问 `http://localhost:8080/hello` → 页面显示 `hello world`

---

## 三、目录结构说明

```
day02/
├── pom.xml                              ← 项目配置文件
├── src/
│   └── main/
│       ├── java/
│       │   └── com/
│       │       └── shyc/
│       │           ├── Application.java          ← 启动类
│       │           └── controller/
│       │               └── HelloController.java  ← 控制器
│       └── resources/                            ← 配置文件目录（目前空）
```

IDE 里创建 Maven 模块（不选骨架）时，只会生成 `.mvn/` 和 `pom.xml`，**src 目录需要手动建**，这是正常的。

---

## 四、练习题

**Q1**：把 `/hello` 改成 `/sayHi`，需要改哪行代码？

**Q2**：pom.xml 里 `<parent>` 那块不写会怎样？

**Q3**：`@RestController` 和 `@GetMapping` 分别管什么事？

<details>
<summary>点击查看答案</summary>

**A1**：改一行——`@GetMapping("/hello")` → `@GetMapping("/sayHi")`，重启后访问 `http://localhost:8080/sayHi`

**A2**：不写 `<parent>` 的话，SpringBoot 依赖（比如 `spring-boot-starter-web`）的版本号就没地方继承了，要么自己逐个写 version，要么报错。父工程就是帮你省掉这些啰嗦事。

**A3**：
- `@RestController`：声明这个类是"接待员"，专门接收和处理 HTTP 请求
- `@GetMapping("/hello")`：指定门牌号，告诉 Spring 当有人访问 `/hello` 时就调用这个方法

</details>

---

## 五、一句话总结

> **改 pom.xml（加父工程 + spring-boot-starter-web）→ 写 @SpringBootApplication 启动类 → 写 @RestController Controller，三步跑起一个 Web 服务。**

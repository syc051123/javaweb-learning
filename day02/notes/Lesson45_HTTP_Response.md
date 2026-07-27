# 黑马 JavaWeb 第 45 集学习指导：HTTP 协议 - 响应协议

> **视频**：BV1yGydYEE3H，P45（时长 24:04）
> **章节位置**：HTTP 协议章节第 3 集（43-45 集）
> **学完后你能搞懂**：服务器返回的响应长什么样，状态码 200/404/500 是什么意思

---

## 一、核心概念

### 1. HTTP 响应长什么样

浏览器发请求，服务器处理后返回一段**文本**，就像这样：

```http
HTTP/1.1 200 OK
Content-Type: text/html;charset=UTF-8
Content-Length: 12

hello world
```

**这整段文本就是 HTTP 响应**，分三部分：

### 2. 响应结构

```
┌─────────────────────────────────────────┐
│ 响应状态行 │ HTTP/1.1 200 OK            │
├─────────────────────────────────────────┤
│ 响应头    │ Content-Type: text/html      │
│          │ Content-Length: 12           │
│          │ Date: Mon, 27 Jul 2026 ...   │
├─────────────────────────────────────────┤
│ 空行      │ （空一行，分隔头跟体）        │
├─────────────────────────────────────────┤
│ 响应体    │ hello world                  │
└─────────────────────────────────────────┘
```

### 3. 响应状态行

```http
HTTP/1.1    200        OK
   ↑        ↑          ↑
协议版本   状态码    状态描述
```

**状态码**是重点，分 5 大类：

| 状态码 | 含义 | 常见例子 |
|--------|------|---------|
| 1xx | 信息，服务器收到请求，需要继续操作 | 很少见 |
| **2xx** | **成功** | **200 OK**（请求成功） |
| **3xx** | **重定向** | **302 Found**（临时跳转）、**304 Not Modified**（缓存可用） |
| **4xx** | **客户端错误** | **400 Bad Request**（请求语法错）、**401 Unauthorized**（未授权）、**403 Forbidden**（禁止访问）、**404 Not Found**（资源不存在） |
| **5xx** | **服务器错误** | **500 Internal Server Error**（服务器内部错误）、**502 Bad Gateway**（网关错误）、**503 Service Unavailable**（服务不可用） |

**必须记住的 3 个**：
- **200** = 一切正常 ✅
- **404** = 你要的东西不存在 ❌
- **500** = 服务器出 bug 了 💥

### 4. 响应头

常见的响应头：

| 响应头 | 作用 |
|--------|------|
| Content-Type | 告诉浏览器返回的是什么格式（text/html、application/json、image/png 等） |
| Content-Length | 返回内容的长度（字节数） |
| Date | 服务器响应的时间 |
| Server | 服务器软件信息（比如 nginx、Apache） |
| Set-Cookie | 让浏览器保存 Cookie（后面登录会用到） |

### 5. 响应体

服务器真正返回给浏览器的数据：
- 访问网页 → 返回 HTML 代码
- 访问接口 → 返回 JSON 数据
- 访问图片 → 返回图片二进制数据

---

## 二、实际例子

### 例子 1：你的 day02 项目

```http
HTTP/1.1 200 OK
Content-Type: text/plain;charset=UTF-8
Content-Length: 11

hello world
```

- 状态码 200：成功
- Content-Type：纯文本格式
- 响应体：`hello world`

### 例子 2：访问不存在的页面

```http
HTTP/1.1 404 Not Found
Content-Type: text/html;charset=UTF-8

<html><body><h1>Whitelabel Error Page</h1>...</body></html>
```

- 状态码 404：你要的资源不存在
- SpringBoot 默认返回一个错误页面

### 例子 3：服务器代码报错

```http
HTTP/1.1 500 Internal Server Error
Content-Type: application/json

{"timestamp":"2026-07-27...","status":500,"error":"Internal Server Error"}
```

- 状态码 500：服务器内部错误（你的 Java 代码抛异常了）


---

## 三、案例实操：用户列表渲染展示

> 视频里这个案例的**完整需求**比我想的复杂得多，以下是实际内容。

### 1. 需求说明

访问前端静态页面（`http://localhost:8080/user.html`），页面会发 Ajax 请求到服务端（`http://localhost:8080/list`），服务端读取 `user.txt` 文件中的数据，返回 JSON 给前端，前端渲染成表格。

```
浏览器 → user.html → Ajax请求 /list → Controller → 读取 user.txt → 返回 JSON
```

### 2. 准备工作

#### 2.1 加依赖

本案例需要额外加两个依赖：**Lombok** 和 **Hutool**。

**生效范围**：`day02/pom.xml` 的 `<dependencies>` 标签内

```xml
<!-- Lombok：简化实体类代码 -->
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
</dependency>

<!-- Hutool：Java 工具包，用来读文件、转 JSON 等 -->
<dependency>
    <groupId>cn.hutool</groupId>
    <artifactId>hutool-all</artifactId>
    <version>5.8.27</version>
</dependency>
```

> Lombok 的版本由 SpringBoot 父工程管理，所以不用写 `<version>`。Hutool 不是 SpringBoot 官方管理的，所以要手动写版本。

#### 2.2 准备数据文件

在 `src/main/resources/` 下新建 **`user.txt`**，内容如下：

```
1,shyc,123456,史彦超,21,2026-07-27 10:00:00
2,zhangsan,abc123,张三,25,2026-07-27 10:00:00
3,lisi,pass456,李四,30,2026-07-27 10:00:00
```

每一列对应：`id, username, password, name, age, updateTime`

#### 2.3 准备前端页面

在 `src/main/resources/static/` 下新建 **`user.html`**（这里放一个简单的 Ajax 请求页面，具体代码后面实战会写）

### 3. 代码实现

#### 3.1 实体类 User

**位置**：`src/main/java/com/shyc/pojo/User.java`

```java
package com.shyc.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 封装用户信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Integer id;
    private String username;
    private String password;
    private String name;
    private Integer age;
    private LocalDateTime updateTime;
}
```

**Lombok 注解说明**：

| 注解 | 作用 |
|------|------|
| `@Data` | 自动生成 Getter/Setter/toString/equals/hashCode |
| `@NoArgsConstructor` | 自动生成**无参**构造方法 |
| `@AllArgsConstructor` | 自动生成**全参**构造方法 |

没有 Lombok 的话，这几个注解生成的代码你得自己写几十行。所以 Lombok 是 Java 后端**必装工具**。

#### 3.2 Controller

**位置**：`src/main/java/com/shyc/controller/UserController.java`

```java
package com.shyc.controller;

import cn.hutool.core.io.IoUtil;
import com.shyc.pojo.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UserController {

    @RequestMapping("/list")
    public List<User> list() {
        // 1. 加载并读取文件 user.txt
        InputStream in = this.getClass().getClassLoader()
                .getResourceAsStream("user.txt");
        List<String> lines = IoUtil.readLines(in, StandardCharsets.UTF_8, new ArrayList<>());

        // 2. 解析每行数据，组装成 User 对象
        List<User> userList = lines.stream().map(line -> {
            String[] parts = line.split(",");
            Integer id = Integer.parseInt(parts[0]);
            String username = parts[1];
            String password = parts[2];
            String name = parts[3];
            Integer age = Integer.parseInt(parts[4]);
            LocalDateTime updateTime = LocalDateTime.parse(
                    parts[5], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            return new User(id, username, password, name, age, updateTime);
        }).collect(Collectors.toList());

        // 3. 返回集合 → SpringBoot 自动转成 JSON 响应给浏览器
        return userList;
    }
}
```

#### 3.3 启动测试

启动 day02 项目，浏览器访问：
```
http://localhost:8080/user.html
```

页面上的 Ajax 会请求 `/list`，浏览器接收到的响应就是 JSON 格式的用户列表数据。

### 4. @ResponseBody 注解

**问题**：Controller 方法里 `return` 的结果，怎么就能直接响应给浏览器呢？

**答案**：靠 `@ResponseBody` 注解。

| 项目 | 说明 |
|------|------|
| 类型 | 方法注解、类注解 |
| 位置 | 写在 Controller 方法上或类上 |
| 作用 | 将方法返回值直接响应给浏览器。如果返回值是对象/集合，自动转成 JSON 格式 |

**@RestController = @Controller + @ResponseBody**

你在类上加了 `@RestController`，就等于给**所有方法**都加上了 `@ResponseBody`，所以每个方法的返回值都能自动响应给浏览器。

### 5. 问题分析（引出三层架构）

现在所有代码都写在 Controller 里：

```
UserController
    ├── 读取文件        ← 数据操作
    ├── 解析数据        ← 业务逻辑
    └── 返回响应        ← 请求处理
```

全混在一起，导致：
- 改数据操作 → 改 Controller
- 改业务逻辑 → 改 Controller
- 改响应方式 → 改 Controller

**这就是为什么需要 P46 讲的"三层架构"**——把这三件事拆到不同层去。

---

## 四、请求 vs 响应 对比

| 对比 | 请求（Request） | 响应（Response） |
|------|----------------|-----------------|
| 谁发的 | 浏览器（客户端） | 服务器 |
| 第一行 | 请求行（GET /hello HTTP/1.1） | 状态行（HTTP/1.1 200 OK） |
| 核心信息 | 请求方式、请求路径 | 状态码、状态描述 |
| 目的 | 告诉服务器我要什么 | 告诉浏览器我给你什么 |

---

## 五、小实验（浏览器里看响应）

继续用 F12 → Network：

1. 刷新 `http://localhost:8080/hello`
2. 点 `hello` 那一行
3. 看 **Response Headers**（响应标头）
4. 点 **Response**（响应）标签，能看到返回的 `hello world`

试试把 URL 改成 `http://localhost:8080/abc`（不存在的路径），再看状态码变成多少？

---

## 六、练习题

**Q1**：HTTP 响应由哪三部分组成？

**Q2**：状态码 200、404、500 分别代表什么意思？

**Q3**：Content-Type 响应头的作用是什么？

**Q4**：你在浏览器访问一个不存在的路径（比如 `/abc`），服务器会返回什么状态码？

**Q5**：`@RestController` 和 `@ResponseBody` 是什么关系？

<details>
<summary>点击查看答案</summary>

**A1**：**响应状态行 + 响应头 + 空行 + 响应体**

**A2**：
- 200：请求成功 ✅
- 404：资源不存在 ❌
- 500：服务器内部错误 💥

**A3**：告诉浏览器返回的数据是什么格式（HTML、JSON、图片等），浏览器根据这个决定怎么显示。

**A4**：**404 Not Found**，因为服务器找不到 `/abc` 对应的资源。

**A5**：`@RestController = @Controller + @ResponseBody`。加了 `@RestController`，就等于类上所有方法都自动带上了 `@ResponseBody`，返回值会直接响应给浏览器。

</details>

---

## 七、一句话总结

> **HTTP 响应 = 状态行（协议版本 + 状态码 + 描述）+ 响应头（键值对信息）+ 空行 + 响应体（真正返回的数据）。记住 200=成功，404=找不到，500=服务器报错。**

---

## 下一集预告

**P46《三层架构》**：代码怎么组织才不混乱？Controller、Service、Dao 各管什么？

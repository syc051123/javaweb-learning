# 黑马 JavaWeb 第 44 集学习指导：HTTP 协议 - 请求协议

> **视频**：BV1yGydYEE3H，P44（时长 24:04）
> **学完后你能搞懂**：浏览器发出去的请求里到底写了什么

---

## 一、大白话讲核心概念

### HTTP 请求到底长什么样？

你在浏览器输入 `http://localhost:8080/hello` 敲回车，浏览器实际上发出了一串**文本**，就像这样：

```http
GET /hello HTTP/1.1
Host: localhost:8080
Connection: keep-alive
User-Agent: Mozilla/5.0 (Windows NT 10.0) Chrome/120.0
Accept: text/html,application/xhtml+xml,application/xml;q=0.9
```

**这整段文本就是 HTTP 请求**，分三部分：

### 1. 请求行（第一行）

```http
GET      /hello       HTTP/1.1
↑         ↑            ↑
请求方式   请求路径      协议版本
```

- **GET**：请求方式，表示"我要拿东西"
- **/hello**：你要访问的资源路径
- **HTTP/1.1**：用的协议版本

### 2. 请求头（从第二行到空行之前）

```http
Host: localhost:8080              → 去哪台服务器
Connection: keep-alive             → 别断开连接，待会可能还要用
User-Agent: Mozilla/5.0 ...        → 我用的是 Chrome 浏览器
Accept: text/html,...              → 我能接收 HTML 格式
Accept-Language: zh-CN             → 我要中文
Accept-Encoding: gzip              → 压缩格式我能解
```

一行就是一个"键值对"：`键: 值`

**类比**：请求头就像快递面单——
- Host：收件地址
- User-Agent：快递公司名称
- Accept：你希望用什么包装
- Accept-Language：你读什么语言

### 3. 请求体（空行之后）

GET 请求**没有请求体**，POST 请求才有。

```http
POST /login HTTP/1.1
Host: localhost:8080
Content-Type: application/x-www-form-urlencoded

username=shiyc&password=123456
                   ↑
             这就是请求体
```

---

## 二、GET vs POST

| 对比 | GET | POST |
|------|-----|------|
| 干什么 | **获取**数据 | **提交**数据 |
| 数据放哪 | 拼在 URL 后面 `?name=xxx` | 放在请求体里 |
| 能带的数据量 | 有限（URL 长度限制） | 几乎无限 |
| 安全性 | 数据暴露在 URL 里（不安全） | 数据在请求体里（相对安全） |
| 例子 | 搜索、浏览网页 | 登录、注册、上传文件 |

**GET 示例**：

```
http://www.baidu.com/s?wd=springboot
                              ↑
                     URL 后面跟 ? 然后参数
```

**POST 示例**：

```
请求行：POST /login HTTP/1.1
请求头：Content-Type: application/x-www-form-urlencoded
请求体：username=shiyc&password=123456
```

**生活类比**：

- **GET** = 你站在店门口，透过玻璃看里面有没有你想要的商品（只看不碰）
- **POST** = 你走进店里，填了一张单子交给店员（提交数据）

---

## 三、一张图总结请求结构

```
┌─────────────────────────────────────────┐
│ 请求行  │ GET /hello HTTP/1.1           │
├─────────────────────────────────────────┤
│ 请求头  │ Host: localhost:8080          │
│         │ User-Agent: Chrome/120.0      │
│         │ Accept: text/html             │
│         │ Accept-Language: zh-CN        │
├─────────────────────────────────────────┤
│ 空行    │ （空一行，分隔头跟体）          │
├─────────────────────────────────────────┤
│ 请求体  │ （GET 没有，POST 才有）        │
└─────────────────────────────────────────┘
```

---

## 四、小实验（你可以在浏览器里亲眼看到）

打开你的 `http://localhost:8080/hello`，然后：
1. 按 **F12** 打开开发者工具
2. 点 **Network（网络）** 标签
3. 刷新页面
4. 点一下出现的 `hello` 那一行
5. 看 **Headers** 里面的 **Request Headers**

你就能看到刚才讲的那些请求头了。试试看？

---

## 五、练习题

**Q1**：HTTP 请求由哪三部分组成？

**Q2**：GET 和 POST 最主要的区别是什么？

**Q3**：你在百度搜索"java"时，浏览器发出的请求第一行大概长什么样？

<details>
<summary>点击查看答案</summary>

**A1**：**请求行 + 请求头 + 请求体**（注意请求行和请求头之间没有空行，请求头和请求体之间有一个空行）

**A2**：

- GET：数据拼在 URL 里，用于**获取**数据，数据量小，不安全
- POST：数据放在请求体里，用于**提交**数据，数据量大，相对安全

**A3**：
```
GET /s?wd=java HTTP/1.1
```

</details>

---

## 六、一句话总结

> **HTTP 请求 = 请求行（方式 + 路径 + 版本）+ 请求头（键值对信息）+ 空行 + 请求体（GET 没有，POST 才有）。**

---

## 下一集预告

**P45《HTTP 协议-响应协议》**：服务器返回的内容长什么样？状态码 200、404、500 是什么意思？

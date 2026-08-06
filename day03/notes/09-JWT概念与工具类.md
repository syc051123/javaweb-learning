# 🎫 JWT 登录认证 — 正式学习笔记(阶段一:概念与工具类)

> 🎯 **学完这一阶段你能做到**:说清 JWT 是什么、为什么用,并写好 JwtUtils 工具类(生成/解析令牌)。
> ✨ 风格延续员工笔记:emoji + 生活类比,方便复习。
> 📍 进度标记:标记本阶段已完成 ✅ / 待学 ⬜

---

## 🧱 一、JWT 是什么(拆词讲清)

### 拆词

```
JWT = JSON  +  Web  +  Token
       ↓       ↓        ↓
   数据格式  互联网/网页  令牌/凭证
```

**合起来**:JWT = **一种 JSON 格式的、在前后端之间传的令牌(token)。**

### 它是干嘛的(一句话)

> **JWT 把"你是谁 + 什么时候过期"写成一串 JSON,加密后做成一张网络令牌。登录时发给前端,以后前端带着它,服务器验证有效就放行。**

### 🎬 生活类比:电影票 🎫

| JWT 概念 | 电影票 |
|---------|--------|
| JWT 令牌 | 那张票 |
| 登录时发 | 门口售票 |
| 每次请求带上(放请求头) | 进场出示票据 |
| 拦截器验证 | 检票员查票 |
| 过期作废 | 票有时效 |

### 为什么叫"令牌"不叫"密码"

- 密码:你得**记住**,每次输
- 令牌:服务器**发给你**,你存着,请求时带着就行

**类比:密码是家门钥匙(记住),令牌是电影票(出示)。**

---

## 🔍 二、一个 JWT 长啥样(三段结构,先混个脸熟)

一个真 JWT 是一串用 `.` 隔开的长字符串:

```
eyJhbGciOiJIUzI1NiJ9.eyJpZCI6MSwidXNlcm5hbWUiOiJzaHljIn0.4UcB...密钥签名...
└──────┬─────┘  └──────────┬──────────┘  └──────────┬─────────┘
    Header            Payload                  Signature
   (票头说类型)       (你是谁/过期)              (防伪水印)
```

| 部分 | 存啥 | 类比 |
|------|------|------|
| **Header** | 类型 + 算法(HS256) | 票头(说明这是什么票) |
| **Payload** | 用户信息 + 过期时间 | 票正文(你是谁) |
| **Signature** | 用密钥签名,防篡改 | 防伪水印(改了就碎) |

> 🔗 **现在全懂三段不用急**,先记住:JWT 是 `.` 隔开的三段字符串,包着身份 + 过期,有防伪签名。

---

## 📦 三、JWT 依赖(3 个,scope 的含义)

Spring Boot 不自带 JWT,需要加 3 个 **jjwt** 库:

```xml
<!-- API:编译用,你写代码 import Jwts 靠它 -->
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-api</artifactId>
    <version>0.11.5</version>
</dependency>
<!-- 实现:运行时用 -->
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-impl</artifactId>
    <version>0.11.5</version>
    <scope>runtime</scope>
</dependency>
<!-- JSON 转换:运行时用 -->
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-jackson</artifactId>
    <version>0.11.5</version>
    <scope>runtime</scope>
</dependency>
```

### scope 是什么(重点理解)

**scope = 这个依赖参与哪一步。**

| scope | 什么时候用 | JWT 里谁 |
|-------|-----------|---------|
| compile(默认) | 编译 + 运行都要 | `jjwt-api`(你 import 它写代码) |
| runtime | 只在运行时,不用 import | `jjwt-impl`、`jjwt-jackson`(框架偷偷用) |
| test | 只在测试用 | spring-boot-starter-test |

> ✅ **记口诀**:你写代码 `import` 的 → compile;只是运行时被框架调、你不用 import 的 → runtime。

---

## 🛠️ 四、JwtUtils 工具类(阶段一核心)

**作用**:提供两个方法——生成 JWT、解析 JWT。

### 完整代码(带注释,建议敲一遍)

```java
package com.shyc.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtils {

    // 密钥字符串(签名用,保证别人伪造不了;HS256 要求至少 32 字节)
    private static final String SECRET = "shyc-javaweb-tlias-jwt-secret-key-2026-abcdefgh";

    // 把字符串转成"秘密钥匙"对象(规范写法,签名要传 Key 不放字符串)
    //   Secret(秘密) | 只有你知道的密钥; Key(钥匙) | 签名专用钥匙
    //   Keys.hmacShaKeyFor = Keys(钥匙) + hmacSha(HMAC算法) + keyFor(为...生成)
    private static final SecretKey SECRET_KEY =
            Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

    // 过期时间:1天(毫秒)
    private static final long EXPIRE_TIME = 24 * 60 * 60 * 1000;

    // ① 生成 JWT(发电影票)
    //   Jwts.builder() | JWT 的构建者,一步步搭出 token
    public static String generateToken(Integer id, String username){
        // claims = 票上要带的信息(乘客)
        Map<String,Object> claims = new HashMap<>();
        claims.put("id", id);
        claims.put("username", username);

        return Jwts.builder()
                .setClaims(claims)                                   // 装乘客(你的信息)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_TIME)) // 过期时间
                .signWith(SECRET_KEY)                                // 用密钥盖防伪章(规范写法)
                .compact();                                          // 生成最终那串
    }

    // ② 解析 JWT(检票)
    //   parserBuilder() | 解析器的"构建者",装配好配置再 build
    public static Claims parseToken(String token){
        return Jwts.parserBuilder()          // 解析器构建者(规范写法)
                .setSigningKey(SECRET_KEY)   // 用同一个密钥验签
                .build()                     // 造出解析器
                .parseClaimsJws(token)       // 解析
                .getBody();                  // 拿出乘客信息(claims)
    }
}
```

### 拆解(理解再背)

| 方法 | 干什么 | JWT 类比 |
|------|--------|---------|
| `generateToken` | 把 id/username 装进去,签发 JWT | 售票(你的信息做成票) |
| `parseToken` | 拿 JWT 解析出里面信息 | 检票(看票上的字) |
| `Jwts.builder()` | 创建/生成 | 建票 |
| `Jwts.parserBuilder()...build()` | 解析 | 验票(规范写法) |
| `.signWith(SECRET_KEY)` | 用密钥对象签名 | 盖防伪章(规范写法) |
| `.compact()` | 生成最终字符串 | 出票 |
| `setExpiration` | 设过期时间 | 票的时效 |
| `Keys.hmacShaKeyFor()` | 把密钥字符串转成密钥对象 | 准备"章" |

### 🚨 三个重点

1. **`generateToken` 和 `parseToken` 是一对**——生成用密钥对象签名,解析用**同一个密钥对象**验签
2. **SECRET_KEY 必须一致**:生成/解析、甚至重启后都要同一个,否则解析失败
3. **`Claims`** 是解析后拿到的"乘客信息盒子"→ `claims.get("id")` / `claims.get("username")` 取值

### 💡 为什么用规范写法(不写旧版)

旧写法 `.signWith(SignatureAlgorithm.HS256, SECRET)` 传的是字符串,已被标记废弃(deprecated)。
规范写法把密钥包装成 `SecretKey` 对象(`Keys.hmacShaKeyFor`),签名直接传对象,更明确、更安全。

---

## 🗺️ 阶段一小结(背 3 句)

1. **JWT = JSON + Web + Token = 一张 JSON 格式的网络令牌**,存"你是谁 + 过期时间",有防伪
2. **依赖 3 个**:api(编译)、(impl + jackson 运行);scope 是"参与哪一步"
3. **JwtUtils 核心**:`Jwts.builder().signWith(SECRET_KEY).compact()` 生成,`Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token).getBody()` 解析

---

## 📍 下一阶段预告(学了再补充本笔记)

```
阶段二:登录接口 POST /login
  → 查数据库用户 → BCrypt 验证密码 → 发 JWT 给前端
阶段三:拦截器 Interceptor
  → 每次请求"查票",没票/过期就拦下
阶段四:注册拦截器 WebConfig
```

> 等你把这几个写完,告诉我,我再把"阶段二~四"补充进这份笔记,让它变成完整的登录认证总结。

---

## 🔧 测试进度

- [ ] JwtUtils 写出来,`mvn compile` 通过
- [ ] 登录接口实测:登录成功拿到 JWT
- [ ] 拦截器生效:没令牌访问 /emps 被拦

> 完成一项可以把 `[ ]` 改成 `[x]`(勾选),边学边打勾,有成就感 🎯

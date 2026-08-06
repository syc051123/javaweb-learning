# 密码加密（MD5 入门 + BCrypt 实战）

> **一句话**：密码不能存明文，存之前先加密。数据库里存的是"乱码"，管理员（DataGrip）看到的也是乱码——谁都看不到真实密码。

---

## 一、为什么要加密（先理解动机）

🟢 **必懂**

密码明文存数据库的**危险**：
- 数据库被拖库（泄露）→ 所有人的密码直接暴露
- 管理员能看到所有密码 → 内部人员可滥用
- 用户喜欢"一个密码走天下" → 泄露一个，其他网站跟着沦陷

**加密后的效果**：

```
明文: 123456
加密: e10adc3949ba59abbe56e057f20f883e   ← 数据库里存这个
```

**谁看都是乱码** → 就算数据库泄露、管理员登录，也拿不到真实密码。

---

## 二、MD5 是什么（拆词根）

🟢 **必懂**

```
MD5 = Message-Digest Algorithm 5
     = 消息摘要算法 第5版
```

**大白话**：一个"搅碎机"——把任意长度的输入，搅成**固定 32 位**的乱码。

| 特点 | 说明 |
|------|------|
| **不可逆** | 从乱码推不回原密码 |
| **固定长度** | 不管密码多长，结果都是 32 位十六进制 |
| **相同输入→相同输出** | `123456` 永远 → `e10adc39...` |

**验证方式**：不是"解密还原"，而是"再加密一次，比结果"：

```
用户输入 123456 → MD5 → e10adc39...
数据库存的      → e10adc39...
两者相等 → 密码正确 ✅
```

---

## 三、Java 代码：怎么加密（MD5）

🟢 **必会**

Spring 自带工具类 `DigestUtils`，一行搞定：

```java
import org.springframework.util.DigestUtils;

// 加密：明文 → MD5
String md5 = DigestUtils.md5DigestAsHex("123456".getBytes());
// 结果: e10adc3949ba59abbe56e057f20f883e
```

**注意**：参数要传 `getBytes()`（字节数组），不能直接传字符串。

---

## 四、登录验证（关键：不能比明文）

🟢 **必会**（写登录模块时用）

```java
// ❌ 错误：直接比明文（数据库里是密文，永远比不中）
if (inputPassword.equals(emp.getPassword())) { }

// ✅ 正确：先把输入加密，再比密文
String inputMd5 = DigestUtils.md5DigestAsHex(inputPassword.getBytes());
if (inputMd5.equals(emp.getPassword())) {
    // 密码正确，登录成功
}
```

**流程**：

```
用户输入 123456
  ↓ MD5
e10adc39...
  ↓ 和数据库存的比
相等 → 登录成功
```

---

## 五、新增员工时（注册/新增时加密）

🟢 **必会**

```java
// 新增员工，密码要先加密再存
dept.setPassword(DigestUtils.md5DigestAsHex(passwordInput.getBytes()));
```

**规则**：**存入数据库之前**加密，存进去的就是密文。

---

## 六、存量数据怎么加密（手动批量）

🟡 **了解**

已经存在的明文密码，用工具算好 MD5 再 UPDATE（SQL 本身不算 MD5）：

```sql
-- 方法1:用 Java/Python 脚本算好每个密码的 MD5,再 UPDATE
UPDATE emp SET password = 'e10adc39...' WHERE username = 'shinaian';

-- 方法2:MySQL 8 自带 MD5() 函数(生产慎用,但学习可用)
UPDATE emp SET password = MD5(password);
```

**注意**：`UPDATE emp SET password = MD5(password)` 这招只有一次机会——跑完密码就变密文了，再跑就变成"密文的密文"（错）。

---

## 七、MD5 的坑（面试加分项）

🟡 **要知道**

| 问题 | 说明 |
|------|------|
| **可被撞库** | 有人预先把常见密码的 MD5 全算好（彩虹表），拿到密文一查就知道原文 |
| **相同密码结果相同** | 30 个人的 `123456` 都是同一个密文 → 泄露一个，全暴露 |
| **没有盐（salt）** | 真实项目会给每个密码加随机盐，再加密 |

**结论**：MD5 是**入门教学**用的，**真实项目用 BCrypt**。

---

## 八、BCrypt（真实项目方案）

🟡 **了解，会用它就加分**

```xml
<!-- 加依赖 -->
<dependency>
    <groupId>org.springframework.security</groupId>
    <artifactId>spring-security-crypto</artifactId>
</dependency>
```

```java
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

// 加密(每次结果都不同!自动带随机盐)
String hash = encoder.encode("123456");

// 验证(用 matches,不是 equals)
boolean ok = encoder.matches("123456", hash);
```

**BCrypt 比 MD5 强在哪**：
- **自带盐**：同一个密码每次加密结果都不同
- **抗撞库**：算得慢（故意的），彩虹表无效
- **行业标准**：Spring Security、真实项目标配

---

## 九、总结（背这 5 句）

1. **密码不能存明文**，存之前加密
2. **MD5 入门**：`DigestUtils.md5DigestAsHex(pwd.getBytes())`
3. **登录比密文**：输入先 MD5 再和库里的比（`matches`/`equals`）
4. **新增时加密**：入库前加密
5. **真实项目用 BCrypt**：自带盐、抗撞库（MD5 只是教学）

---

## 十、踩坑记录

| 坑 | 说明 |
|----|------|
| 登录直接比明文 | 数据库是密文，永远比不中 → 先加密再比 |
| UPDATE 跑两遍 MD5 | 变成"密文的密文"，永久锁死 → 只跑一次 |
| 测试数据不该随便加密 | 学习阶段明文更方便测登录 → 先问再动数据库 |

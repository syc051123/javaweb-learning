# 黑马 JavaWeb 第 36 集学习指导：单元测试 - 断言 & 常见注解

> **视频**：BV1yGydYEE3H，P36（时长 27:35）
> **章节位置**：单元测试章节第 2 集（35~38 集）
> **学完后你能做到**：
> 1. 用 `Assertions` 类替代 if-throw 写断言
> 2. 用 `@BeforeEach` 抽重复代码（比如 `new UserService()`）
> 3. 知道 `@BeforeAll` 和 `@BeforeEach` 的区别
> 4. 用 `@DisplayName` 让测试名可读
> 5. 用 `@ParameterizedTest` 实现参数化测试（一组数据跑多次）

---

## 一、大白话讲核心概念

### 1. 断言（Assertions）是啥

**大白话**：判断"对不对"的标准写法，不用自己写 if-throw 了。

**类比**：物理实验里，你以前用眼睛估读游标卡尺（if-throw），现在换成数字显示屏直接读数（Assertions），**一眼看出偏差，还能自定义报错信息**。

### 2. 为什么要用 Assertions

| 写法 | 代码量 | 报错信息 | 失败时定位 |
|------|--------|----------|-----------|
| if-throw | 3 行 | 自己拼字符串 | 知道哪行抛的 |
| `assertEquals` | 1 行 | 自动显示"期望 vs 实际" | 更精确 |

**关键差别**：`assertEquals(期望, 实际)` 失败时，JUnit 会自动告诉你"我期待 男，但拿到了 女"，不用你自己拼字符串。

### 3. 生命周期注解是干嘛的

**大白话**：测试方法执行前后的"自动准备"和"自动收尾"。

**类比**：做物理实验，每测一组数据前要调零（@BeforeEach），测完要擦桌子（@AfterEach）。

| 注解 | 执行时机 | 用途 |
|------|----------|------|
| `@BeforeEach` | **每个** `@Test` 之前 | 初始化对象、准备数据 |
| `@AfterEach` | **每个** `@Test` 之后 | 清理资源、重置状态 |
| `@BeforeAll` | **整个类**开始前一次 | 连数据库、启动服务 |
| `@AfterAll` | **整个类**结束后一次 | 关数据库、释放资源 |
| `@DisplayName` | 标记测试类/方法 | 自定义显示名称，代替方法名 |
| `@ParameterizedTest` | 替代 `@Test` | 参数化测试，多组数据复用同一段代码 |

**注意**：`@BeforeAll` / `@AfterAll` 必须是 `static` 方法，因为它在测试类实例化之前就执行。

---

## 二、动手实操（重构 day01 的测试）

### Step 0：当前代码回顾

**UserService.java**（业务代码，不变）：
```java
package com.shyc;

public class UserService {
    public String getUserGender_Male(String idCard) {
        if (idCard == null || idCard.length() != 18) {
            return "未知";
        }
        int sex = Integer.parseInt(idCard.substring(16, 17)) % 2;
        return sex == 0 ? "女" : "男";
    }
}
```

**UserServiceTest.java**（35 集写法，待重构）：
```java
package com.shyc;

import org.junit.jupiter.api.Test;

public class UserServiceTest {

    @Test
    public void testGetUserGender_Male() {
        UserService userService = new UserService();
        String idCard = "110101199001011234";
        String gender = userService.getUserGender_Male(idCard);
        if (!"男".equals(gender)) {
            throw new RuntimeException("测试失败：期望 男，实际 " + gender);
        }
    }

    @Test
    public void testGetUserGender_Female() {
        UserService userService = new UserService();
        String idCard = "110101199001011222";
        String gender = userService.getUserGender_Male(idCard);
        if (!"女".equals(gender)) {
            throw new RuntimeException("测试失败：期望 女，实际 " + gender);
        }
    }
}
```

**痛点**：每个方法里都 `new UserService()`，重复了 3 遍（如果再加测试方法，还得继续 copy）。

### Step 1：引入 Assertions，替换 if-throw

**修改 import**：
```java
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;  // ← 新增
```

**修改 testGetUserGender_Male**：
```java
@Test
public void testGetUserGender_Male() {
    UserService userService = new UserService();
    String idCard = "110101199001011234";
    String gender = userService.getUserGender_Male(idCard);
    
    // 原来 3 行 → 现在 1 行
    Assertions.assertEquals("男", gender);
}
```

**assertEquals 两种写法**：
```java
// 写法 A：只有期望和实际
Assertions.assertEquals("男", gender);

// 写法 B：加自定义失败消息（推荐）
Assertions.assertEquals("男", gender, "身份证号第17位为奇数，应为男性");
```

### Step 2：用 @BeforeEach 抽重复代码

**问题**：3 个测试方法，每个都 `new UserService()`，能不能只写一次？

**解决**：加个 `@BeforeEach` 方法，JUnit 会在**每个** `@Test` 之前自动执行它。

**完整重构后的代码**（含 `@DisplayName`）：

```java
package com.shyc;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("用户服务测试")
public class UserServiceTest {

    private UserService userService;

    @BeforeEach
    public void setUp() {
        userService = new UserService();
    }

    @DisplayName("测试-获取性别-男性")
    @Test
    public void testGetUserGender_Male() {
        String idCard = "110101199001011234";
        String gender = userService.getUserGender_Male(idCard);
        Assertions.assertEquals("男", gender, "第17位为3(奇数)，应为男");
    }

    @DisplayName("测试-获取性别-女性")
    @Test
    public void testGetUserGender_Female() {
        String idCard = "110101199001011222";
        String gender = userService.getUserGender_Male(idCard);
        Assertions.assertEquals("女", gender, "第17位为2(偶数)，应为女");
    }

    @DisplayName("测试-传入null应返回未知")
    @Test
    public void testGetUserGender_Null() {
        String gender = userService.getUserGender_Male(null);
        Assertions.assertEquals("未知", gender, "传入null应返回未知");
    }

    @DisplayName("测试-传入错误长度返回未知")
    @Test
    public void testGetUserGender_InvalidLength() {
        String gender = userService.getUserGender_Male("123456");
        Assertions.assertEquals("未知", gender, "长度不为18应返回未知");
    }
}
```

**`@DisplayName` 效果**：跑测试时，IDEA 控制台显示"测试-获取性别-男性"而不是方法名 `testGetUserGender_Male`。

**关键变化对照表**：

| 方面 | 35 集写法 | 36 集写法 |
|------|----------|----------|
| 断言 | if-throw 3 行 | `assertEquals` 1 行 |
| 初始化 | 每个方法里 new | `@BeforeEach` 统一 new |
| 代码行数 | 约 30 行 | 约 20 行 |
| 可读性 | 一般 | 清爽 |

### Step 3：跑测试验证

```bash
cd D:\desktop\javaweb\untitled\day01
mvn test
```

期望输出：
```
Tests run: 4, Failures: 0, Errors: 0, Skipped: 0
```

（4 个测试：男、女、null、长度异常）

### Step 4（可选）：用 @ParameterizedTest 一条方法测多组数据

黑马笔记里有 `@ParameterizedTest` 的演示，功能是**一次定义、跑多组输入**。

```java
@DisplayName("参数化-批量测试性别")
@ParameterizedTest
@ValueSource(strings = {
    "110101199001011234",  // 第17位=3 男
    "110101199001011222",  // 第17位=2 女
    ""                     // 空字符串 → "未知"
})
public void testGetGenderBatch(String idcard) {
    String gender = userService.getUserGender_Male(idcard);
    System.out.println(gender);  // 只看结果，不做硬断言
}
```

**对比**：
| 写法 | 代码行数 | 适用场景 |
|------|----------|----------|
| 每个场景一个 `@Test` | 4 个方法 × 5 行 | 不同断言逻辑 |
| `@ParameterizedTest` | 1 个方法 | 同一断言逻辑、多组输入 |

---

## 三、Assertions 常用方法速查

| 方法 | 用途 | 示例 |
|------|------|------|
| `assertEquals(期望, 实际)` | 相等判断 | `assertEquals("男", gender)` |
| `assertEquals(期望, 实际, "消息")` | 相等+自定义消息 | `assertEquals("男", gender, "应为男性")` |
| `assertTrue(条件)` | 真值判断 | `assertTrue(gender.equals("男"))` |
| `assertFalse(条件)` | 假值判断 | `assertFalse(gender.isEmpty())` |
| `assertNull(对象)` | 空值判断 | `assertNull(null)` |
| `assertNotNull(对象)` | 非空判断 | `assertNotNull(userService)` |
| `assertThrows(异常类, lambda)` | 异常断言 | 见下方 |

**assertThrows 示例**（验证"必须抛异常"）：
```java
@Test
public void testInvalidIdCard() {
    // 验证：传入 null 不会抛异常（你的代码返回"未知"）
    // 但如果你的代码会抛异常，就这么写：
    Assertions.assertThrows(NullPointerException.class, () -> {
        userService.getUserGender_Male(null);
    });
}
```

**注意**：你的 `UserService` 对 null 做了处理（返回"未知"），所以不会抛异常，这个测试会失败。只是演示语法。

---

## 四、生命周期注解对比（重点）

```java
public class UserServiceTest {

    @BeforeAll  // ← 整个类开始前，只执行 1 次
    public static void init() {
        System.out.println("=== 类开始 ===");
    }

    @BeforeEach  // ← 每个 @Test 前都执行
    public void setUp() {
        System.out.println("--- 方法开始 ---");
    }

    @Test
    public void test1() { System.out.println("测试1"); }

    @Test
    public void test2() { System.out.println("测试2"); }

    @AfterEach  // ← 每个 @Test 后都执行
    public void tearDown() {
        System.out.println("--- 方法结束 ---");
    }

    @AfterAll  // ← 整个类结束后，只执行 1 次
    public static void destroy() {
        System.out.println("=== 类结束 ===");
    }
}
```

**执行顺序**：
```
=== 类开始 ===      (@BeforeAll)
--- 方法开始 ---    (@BeforeEach)
测试1               (test1)
--- 方法结束 ---    (@AfterEach)
--- 方法开始 ---    (@BeforeEach)
测试2               (test2)
--- 方法结束 ---    (@AfterEach)
=== 类结束 ===      (@AfterAll)
```

**记忆口诀**：
- Each = 每个（方法级）
- All = 所有（类级）
- Before = 之前
- After = 之后

---

## 五、练习题（必做）

**Q1**：把 `testGetUserGender_Male` 里的 `assertEquals` 改成 `assertTrue`，怎么写？

**Q2**：`@BeforeEach` 和 `@BeforeAll` 有什么本质区别？

**Q3（编码题）**：给 `testGetUserGender_Male` 加上 `@DisplayName("测试男性身份证")`。

**Q4（编码题）**：用 `@ParameterizedTest` 和 `@ValueSource` 写一个测试，同时测三组身份证（男、女、null）输出"男" / "女" / "未知"。

---

### 答案

**Q1 答案**：
```java
Assertions.assertTrue("男".equals(gender));
// 或
Assertions.assertTrue(gender.equals("男"));
```

**Q2 答案**：
- `@BeforeEach`：每个 `@Test` 前执行，**非 static**，适合初始化对象
- `@BeforeAll`：整个类开始前执行**一次**，**必须 static**，适合连数据库等重量级操作

**Q3 答案**：
```java
@DisplayName("测试男性身份证")
@Test
public void testGetUserGender_Male() {
    // ... 方法体不变
}
```

**Q4 答案**：
```java
@DisplayName("批量测试性别")
@ParameterizedTest
@ValueSource(strings = {"110101199001011234", "110101199001011222", ""})
public void testGenderBatch(String idcard) {
    String gender = new UserService().getUserGender_Male(idcard);
    Assertions.assertNotNull(gender);
}
```

---

## 六、易踩的坑

1. **`@BeforeAll` / `@AfterAll` 忘记写 `static`** → 编译报错
2. **`assertEquals` 参数顺序写反**（实际, 期望）→ 报错信息反着看，容易晕
3. **`@BeforeEach` 方法里抛异常** → 所有测试都跑不起来
4. **在 `@BeforeEach` 里做断言** → 可以但没必要，那是测试方法的事

---

## 七、下一集预告（第 37 集）

- **企业开发规范**：测试类命名、包结构、测试覆盖率
- **AI 生成单元测试**：通义灵码/通义灵码自动生成测试代码
- 学完你就知道"正经公司怎么写测试"

---

## 八、一句话总结本集

> **`Assertions` 让断言变一行，`@BeforeEach` 让初始化复用，测试代码从"能跑"变"清爽"。**

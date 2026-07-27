# 黑马 JavaWeb 第 37 集学习指导：单元测试 - 企业开发规范 & AI 生成单元测试

> **视频**：BV1yGydYEE3H，P37（时长 28:31）
> **章节位置**：单元测试章节第 3 集（35~38 集）
> **学完后你能做到**：
> 1. 知道企业里单元测试的命名、覆盖标准
> 2. 用通义灵码自动生成测试代码
> 3. 会审核 AI 生成的测试，判断要不要

---

## 一、大白话讲核心概念

### 1. 这一集学什么

前两集（35、36）教你**怎么写**单元测试。这一集教你怎么**写得规范**、**写得快**。

**两个重点：**
- **规范**：命名怎么取、要测多少行代码才算合格
- **AI 辅助**：通义灵码一键生成测试代码，你审核一下就能用

### 2. 企业单元测试规范（不记也行，有个印象就够了）

| 规范点 | 要求 | 为什么 |
|--------|------|--------|
| 测试类命名 | `XxxTest`（如 `UserServiceTest`） | Maven 默认扫描这个模式 |
| 测试方法命名 | `testXxx_场景`（如 `testGetGender_Male`） | 一眼看出测什么 |
| 测试覆盖率 | 一般要求 **70% ~ 80%** 以上 | 关键业务逻辑必须覆盖 |
| 包名 | 跟业务代码一致（`com.shyc`） | 测试代码能找到业务类 |
| 测试独立性 | 每个测试方法**互不依赖** | 一个失败不影响其他 |
| 单方法单场景 | 一个测试只测一个"面" | 失败时精确定位 |

**覆盖率通俗理解**：你的代码里有多少行被测试跑到了。比如你的 `UserService` 有 6 行代码，测试覆盖了 4 行，覆盖率 ≈ 67%。

---

## 二、用 AI 生成单元测试（重点！）

### 通义灵码生成测试的方法

你在 VS Code 里装了通义灵码，可以一键生成测试。

**做法：**

1. 打开 `UserService.java`
2. 选中方法名 `getUserGender_Male`
3. 通义灵码有个"生成测试"按钮（或者右键 → 通义灵码 → 生成单元测试）
4. 它会自动给你生成类似这样的代码：

```java
// AI 生成示例（仅供参考，你需要自己审核）
package com.shyc;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class UserServiceTest {

    @Test
    void testGetUserGender_Male() {
        UserService userService = new UserService();
        String result = userService.getUserGender_Male("110101199001011234");
        Assertions.assertEquals("男", result);
    }
}
```

---

### AI 生成的测试，你审核什么？

**1. 方法名是否规范？**

AI 可能生成 `test1()`，你应该改成 `testGetUserGender_Male()`。

**2. 断言是否合理？**

AI 可能只生成了 `assertEquals` 但没有 `@DisplayName` 和自定义消息，自己补：

```java
Assertions.assertEquals("男", gender, "第17位奇数应为男性");
```

**3. 边界情况有没有漏？**

AI 可能只测了"男"和"女"，没测 `null` 和长度校验。需要你手动补：

```java
@Test
void testGetUserGender_Null() {
    Assertions.assertEquals("未知", new UserService().getUserGender_Male(null));
}
```

**4. `@BeforeEach` 要不要抽？**

如果 AI 在每个方法里都 `new UserService()`，你可以手动抽成成员变量。

---

### AI 生成 + 人工优化的完整示例

```java
@DisplayName("AI生成+人工优化示例")
public class UserServiceTest {
    private UserService userService;

    @BeforeEach
    public void setUp() {
        userService = new UserService();
    }

    @DisplayName("测试-获取性别-男性")
    @Test
    public void testGetUserGender_Male() {
        Assertions.assertEquals("男", userService.getUserGender_Male("110101199001011234"));
    }

    @DisplayName("测试-获取性别-女性")
    @Test
    public void testGetUserGender_Female() {
        Assertions.assertEquals("女", userService.getUserGender_Male("110101199001011222"));
    }

    @DisplayName("测试-传入null应返回未知")
    @Test
    public void testGetUserGender_Null() {
        Assertions.assertEquals("未知", userService.getUserGender_Male(null));
    }
}
```

---

## 三、练习题

**Q1**：你的 `UserService.getUserGender_Male` 有 6 行代码（第 8~14 行），目前 2 个测试方法覆盖了几行？覆盖率大概多少？

**Q2（实操题）**：用通义灵码对你的 `UserService.java` 自动生成一个测试，截图上来看。

---

### 答案

**Q1 答案**：覆盖了 4 行（第 12 行 `Integer.parseInt`、第 13 行 `% 2` 和 `return`），覆盖率 ≈ 67%。没有测到的：第 9 行 `if (idCard == null || idCard.length() != 18)` 和 `return "未知"`。加一个 null 测试就能把这 2 行也覆盖到。

**Q2**：自己实操，通义灵码在 VS Code 里直接点就行。

---

## 四、一句话总结本集

> **规范让测试可读，AI 让测试快写，但最终审核权在你手上。**

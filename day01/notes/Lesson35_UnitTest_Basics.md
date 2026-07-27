# 黑马 JavaWeb 第 35 集学习指导：单元测试 - 概述 & 入门

> **视频**：BV1yGydYEE3H，P35（时长 23:09）
> **章节位置**：Maven 章节末尾 → 单元测试章节（35~38 集，共 4 集）→ 之后进 SpringBootWeb
> **学完后你能做到**：
> 1. 说清楚单元测试跟手动 main 方法测试有啥差别
> 2. 在 day01 项目里写出第一个 JUnit 测试
> 3. 看到"绿条"知道这意味着测试通过

---

## 一、大白话讲核心概念

### 1. 什么是单元测试

**大白话**：给代码做体检，**一个方法一次只查一项**。

**类比**：你给学生出试卷（你的代码）之前，自己先把每道题（每个方法）做一遍，确认没出错。单元测试就是"出题前自己先做一遍"这件事的**自动化**。

### 2. 单元测试 vs 手动 main 方法测试

| 对比项 | 手动 main 测试 | 单元测试 |
|--------|----------------|----------|
| 触发方式 | 跑一次看一次输出 | 一键跑全部 |
| 结果反馈 | 看控制台猜对不对 | **绿条 = 通过，红条 = 失败** |
| 重复执行 | 改代码后要重写输出语句 | 写一次，永远复用 |
| 团队协作 | 每个人测法不一样 | 团队共用一套测试代码 |

### 3. JUnit 是啥

**大白话**：Java 里最流行的单元测试"工具包"。

**类比**：物理实验里，别人已经把"游标卡尺"做好了，你拿来就用。JUnit 就是 Java 单元测试的"标准工具包"。

**当前版本**：JUnit 5（也叫 Jupiter）。黑马教程用的就是它。

---

## 二、动手实操（事无巨细）

### Step 0：确认前置条件

- [x] day01 Maven 项目已建好（`day01/pom.xml` 存在）
- [x] 已有被测试的代码：`com.shyc.UserService.getUserGender(String idCard)`

### Step 1：修改 pom.xml，加 JUnit 依赖

**文件位置**：`D:\desktop\javaweb\untitled\day01\pom.xml`

在 `<properties>` 标签**下方**，**新增** `<dependencies>` 标签（整个加在 `</project>` 之前）：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.shyc</groupId>
    <artifactId>day01</artifactId>
    <version>1.0-SNAPSHOT</version>

    <properties>
        <maven.compiler.source>21</maven.compiler.source>
        <maven.compiler.target>21</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>

    <!-- ↓ 新增段：放在 </project> 之前 -->
    <dependencies>
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter</artifactId>
            <version>5.10.2</version>
            <scope>test</scope>
        </dependency>
    </dependencies>
    <!-- ↑ 新增段结束 -->

</project>
```

**关键点说明**：
- `<scope>test</scope>` 意思是"只在跑测试时用到，最终打包 jar 不带它"
- 版本号 `5.10.2` 是当前稳定版
- 改完后 IDEA 右下角会弹出 "Reload Maven"，**点一下让 Maven 下载依赖**（第一次会慢，因为要去拉 JUnit 的 jar 包）

### Step 2：创建测试代码目录

测试代码**不能**跟业务代码混着放。Maven 标准布局：

```
day01/
├── src/
│   ├── main/java/com/shyc/      ← 业务代码（已有）
│   └── test/java/com/shyc/      ← 测试代码（这次新建！）
└── pom.xml
```

**手动创建**：在 `day01/src/` 下新建 `test/java/com/shyc/` 这个嵌套目录。

> IDEA 里建好后，`test/java` 目录会显示成**绿色**——这是 IDEA 识别"测试源码根"的标志。

### Step 3：写第一个测试类

**文件位置**：`D:\desktop\javaweb\untitled\day01\src\test\java\com\shyc\UserServiceTest.java`

完整代码（**生效范围**：整个类）：

```java
package com.shyc;

import org.junit.jupiter.api.Test;

public class UserServiceTest {

    @Test
    public void testGetUserGender_Male() {
        // 1. 准备（Arrange）：身份证号第 17 位是奇数 → 应为男
        UserService userService = new UserService();
        String idCard = "110101199001011234";  // 第 17 位是 3

        // 2. 执行（Act）：调用被测方法
        String gender = userService.getUserGender(idCard);

        // 3. 断言（Assert）：结果必须是"男"
        // （第 35 集还没讲 Assertions，先这么验证）
        if (!"男".equals(gender)) {
            throw new RuntimeException("测试失败：期望 男，实际 " + gender);
        }
    }

    @Test
    public void testGetUserGender_Female() {
        UserService userService = new UserService();
        String idCard = "110101199001011222";  // 第 17 位是 2

        String gender = userService.getUserGender(idCard);

        if (!"女".equals(gender)) {
            throw new RuntimeException("测试失败：期望 女，实际 " + gender);
        }
    }
}
```

**关键点对照表**：

| 元素 | 作用 |
|------|------|
| `@Test` 注解 | 告诉 JUnit"这是测试方法，跑测试时要执行" |
| 方法命名 `test方法名_场景` | 看一眼就知道测什么 |
| 三段式（AAA） | Arrange（准备）→ Act（执行）→ Assert（断言） |

### Step 4：跑测试

**方式 A：IDEA 里跑（推荐）**
1. 打开 `UserServiceTest.java`
2. 类名左边有**绿色三角**图标，点一下
3. 选 "Run 'UserServiceTest'"
4. 下方测试面板出现**绿条** = 全过；**红条** = 有失败

**方式 B：命令行跑**

```bash
cd D:\desktop\javaweb\untitled\day01
mvn test
```

看到 `Tests run: 2, Failures: 0` 就 OK。

---

## 三、练习题（必做，先自己写再对答案）

**Q1**：身份证号 `32010319950505999X`，调用 `getUserGender` 应返回什么？为什么？

**Q2**：身份证号 `44011120001212444X`，调用 `getUserGender` 应返回什么？

**Q3（编码题）**：写一个测试方法 `testGetUserGender_AnotherMale()`，用身份证号 `510103198808083456`（第 17 位是 5），验证返回"男"。

---

### 答案

**Q1 答案**：男
- 身份证号共 18 位，索引从 0 起，第 17 位字符 = `9`
- `9 % 2 = 1` → 奇数 → 返回"男"

**Q2 答案**：女
- 第 17 位字符 = `4`
- `4 % 2 = 0` → 偶数 → 返回"女"

**Q3 答案**：

```java
@Test
public void testGetUserGender_AnotherMale() {
    UserService userService = new UserService();
    String idCard = "510103198808083456";
    String gender = userService.getUserGender(idCard);
    if (!"男".equals(gender)) {
        throw new RuntimeException("测试失败");
    }
}
```

---

## 四、易踩的坑（重点）

1. **测试目录必须是 `src/test/java/`**，不是 `src/test/`——Maven 默认布局
2. **测试包名要跟业务包名一致**（都是 `com.shyc`），不然找不到 `UserService`
3. **测试类命名约定**：`XxxTest`（JUnit 默认扫描这个模式）
4. **`@Test` 注解必须来自 `org.junit.jupiter.api.Test`**——别导错包（有个老的 `org.junit.Test` 是 JUnit 4 的）
5. **不要在测试方法里 `try-catch` 把异常吃掉**——测试就是要让异常暴露
6. **不要用 `System.out.println` 当断言**——第 36 集学的 `Assertions` 才是正道

---

## 五、下一集预告（第 36 集）

- **断言 API**：`Assertions.assertEquals(期望, 实际)`，一行代替 if-throw
- **常用注解**：`@BeforeEach` / `@AfterEach` / `@BeforeAll` / `@AfterAll`（生命周期管理）
- 学完你就不用再写 `if (!"男".equals(gender)) throw ...` 这种啰嗦的断言了

---

## 六、一句话总结本集

> **单元测试 = 给代码每个方法单独做体检；JUnit 5 = 体检标准工具包；`@Test` = 告诉工具"这是体检项目"。**

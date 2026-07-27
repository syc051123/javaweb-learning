# 黑马 JavaWeb 第 38 集学习指导：单元测试 - Maven 依赖范围

> **视频**：BV1yGydYEE3H，P38（时长 10:12，**最短的一集**）
> **章节位置**：单元测试章节第 4 集（35~38 集），**单元测试章节最后一集**

---

## 一、大白话讲核心概念

### 1. `<scope>` 是干什么的

**大白话**：控制这个依赖**在什么时候能用**，**打包时带不带**。

**类比**：你租房子（依赖），房东规定：
- 客厅（`compile`）→ 随时能用，搬家也带走（打包）
- 车位（`test`）→ 只有练车的时候用，搬家不带
- 车库工具（`provided`）→ 练车用，但工具是房东的，不带走

### 2. 常用的 3 种 scope（其他 3 种基本用不到）

| scope | 编译 | 测试 | 运行/打包 | 什么时候用 |
|-------|------|------|-----------|-----------|
| `compile`（默认） | ✅ | ✅ | ✅ | 绝大多数依赖 |
| `test` | ❌ | ✅ | ❌ | JUnit、Mockito 等测试框架 |
| `provided` | ✅ | ✅ | ❌ | Servlet API、Lombok（运行时由服务器提供） |

**另外 3 种**（`runtime`、`system`、`import`）——知道有就行，现阶段用不到。

---

## 二、看你的 day01 pom.xml

```xml
<dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter</artifactId>
    <version>5.10.3</version>
    <scope>test</scope>
</dependency>
```

`scope=test` 意味着：
- ✅ 测试代码（`src/test/java/`）能导入 JUnit
- ❌ 业务代码（`src/main/java/`）不能导入 JUnit
- ❌ 打包成 jar 时不带 JUnit（生产环境不需要）

**你试一下**：在 `UserService.java`（业务代码）里写 `import org.junit.jupiter.api.Test;`，IDEA 会报红——因为 scope=test 限制了只能在测试目录用。

---

## 三、完整依赖范围速查

| scope | 编译 | 测试 | 运行 | 打包 | 传递性 |
|-------|------|------|------|------|--------|
| `compile`（默认） | ✅ | ✅ | ✅ | ✅ | ✅ |
| `test` | ❌ | ✅ | ❌ | ❌ | ❌ |
| `provided` | ✅ | ✅ | ✅ | ❌ | ❌ |
| `runtime` | ❌ | ✅ | ✅ | ✅ | ✅ |
| `system` | ✅ | ✅ | ❌ | ❌ | ❌ |
| `import` | - | - | - | - | 只用于 dependencyManagement |

**记忆窍门**：每次加依赖时，先默认 `compile`，只有 JUnit 类测试框架才加 `scope=test`，只有 Servlet 等服务器自带库才加 `scope=provided`。

---

## 四、练习题

**Q1**：如果你在 `pom.xml` 里把 JUnit 的 scope 从 `test` 改成 `compile`，会发生什么？

**Q2**：假设你引入了一个第三方工具库（比如 Apache Commons Lang），打包时要带走，该用什么 scope？

---

### 答案

**Q1**：照样能跑。但 `compile` 会把 JUnit 打包进 jar，生产环境多了一个无用的依赖，体积变大。所以 JUnit 必须用 `test`。

**Q2**：`compile`（默认，不写 scope 就是 compile）。

---

## 五、一句话总结本集

> **`scope` 控制依赖"什么时候能用 + 打包带不带"；JUnit 固定 `test`，其他默认 `compile`。**

---

## 📌 单元测试章节完（35~38 集）

下一站：**第 40 集《Web基础-课程安排》** → 进 SpringBootWeb。

# 黑马 JavaWeb 第 49 集学习指导：DI 详解

> **视频**：BV1yGydYEE3H，P49
> **章节位置**：分层解耦第 4 集
> **学完后你能搞懂**：三种注入方式怎么选、多个 Bean 时怎么处理

---

## 一、`@Autowired` 的三种注入方式

### 1. 属性注入（最简洁）

🟢 **现在就用这个，以后熟悉了再换**

```java
@RestController
public class UserController {

    @Autowired                    // 直接在字段上写
    private UserService userService;
}
```

像高手写法：⭐ 两颗星（新手友好，老手也常用）

### 2. 构造器注入（最规范）

🟡 **看得懂就行，后面再切**

```java
@RestController
public class UserController {

    private final UserService userService;

    // 如果只有一个构造器，@Autowired 可以省略
    public UserController(UserService userService) {
        this.userService = userService;
    }
}
```

**拆词根**：**Constructor** = Con（共同）+ struct（建造）+ or（者）→ 共同建造者 → 构造方法。

像高手写法：⭐⭐⭐ 三颗星（官方推荐，规范）

**为什么 IDEA 提示 Field injection is not recommended？** 就是因为构造器注入更规范：
- 依赖关系更清晰（一看构造器就知道要什么）
- 更容易写测试
- 字段可以用 `final`，不可变更安全

### 3. Setter 注入（用得少）

🔴 **了解即可**

```java
@RestController
public class UserController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
```

像高手写法：⭐ 一颗星（写得少，也不如构造器规范）

---

## 二、一句话总结三种方式

| 方式 | 代码量 | 规范程度 | 高手评价 |
|------|--------|---------|---------|
| 属性注入 | 最少 | ⭐⭐ | 简洁方便，适合初学者和快速开发 |
| 构造器注入 | 中等 | ⭐⭐⭐ | **最规范，官方推荐** |
| Setter 注入 | 最多 | ⭐ | 用得少，了解就行 |

🟢 **你现在用属性注入完全 ok，等以后写正式项目再切构造器注入。**

---

## 三、多个相同类型 Bean 的处理

上集已经细讲过了，这里回顾：

🔴 **遇到时回来看，不用背**

| 方案 | 像高手写法 | 说明 |
|------|-----------|------|
| `@Primary` | ⭐⭐⭐ | 在实现类上标记"默认用我" |
| `@Qualifier` + `@Autowired` | ⭐⭐ | Spring 原生，精确指定 |
| `@Resource` | ⭐ | 老项目风格，不过也能用 |

---

## 四、练习题

**Q1**：依赖注入有哪三种方式？高手推荐用哪一种？

**Q2**：为什么构造器注入比属性注入更规范？

**Q3**：如果容器中有两个相同类型的 Bean，怎么办？

<details>
<summary>点击查看答案</summary>

**A1**：属性注入、构造器注入、Setter 注入。**高手推荐构造器注入**（官方最规范）。

**A2**：
- 构造器能明确看到类的依赖关系
- 可以用 `final` 保证注入后不可变
- 更容易编写单元测试

**A3**：三种方案——
- `@Primary`：指定默认实现
- `@Qualifier` + `@Autowired`：精确指定名字
- `@Resource`：按名称注入

</details>

---

## 五、一句话总结

> **属性注入最简洁（新手先用），构造器注入最规范（高手推荐），Setter 注入用得少。**

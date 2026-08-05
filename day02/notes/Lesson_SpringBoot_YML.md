# SpringBoot 配置文件：YML

> **视频**：SpringBoot 配置文件章节
> **学完后你能做到**：用 `application.yml` 代替 `application.properties`

---

## 一、为什么用 YML

**properties** 的写法：扁平、重复前缀多

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/javaweb_learning
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.username=root
spring.datasource.password=123456
mybatis.configuration.log-impl=org.apache.ibatis.logging.stdout.StdOutImpl
```

**YML** 的写法：层级清晰、不重复

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/javaweb_learning
    driver-class-name: com.mysql.cj.jdbc.Driver
    username: root
    password: 123456
mybatis:
  configuration:
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
```

---

## 二、语法规则

🟡 **看得懂就行**

| 规则 | 说明 |
|------|------|
| `key: value` | 冒号后**必须有一个空格** |
| 缩进 | 用空格，**不能用 Tab**（IDEA 会自动转） |
| 缩进对齐 | 同层级的缩进空格数一样就行 |
| `#` | 注释 |
| `- value` | 表示数组/列表的一项 |

---

## 三、练习

把项目里的 `application.properties` 改成 `application.yml`：

1. 把 `application.properties` 重命名为 `_application.properties`
2. 在同目录新建 `application.yml`
3. 写入以下内容：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/javaweb_learning
    driver-class-name: com.mysql.cj.jdbc.Driver
    username: root
    password: 123456
mybatis:
  configuration:
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
    map-underscore-to-camel-case: true
```

4. 跑一下测试，看能不能正常运行：

```bash
mvn test -Dtest=MyBatisTest#testFindAll
```

---

<details>
<summary>参考答案</summary>

Spring Boot 会自动读取 `application.yml`（优先级高于 `application.properties`），所以改完后直接跑测试就行，不用改任何代码。

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/javaweb_learning
    driver-class-name: com.mysql.cj.jdbc.Driver
    username: root
    password: 123456

mybatis:
  configuration:
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
    map-underscore-to-camel-case: true
```
</details>

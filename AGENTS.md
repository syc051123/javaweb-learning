# JavaWeb 学习项目 (untitled)

黑马程序员 JavaWeb 教程配套练习项目。初学者项目，逐步演进中。

## Project

- **堆栈**: Java 21 + Maven 4.0，纯 Java SE（尚未引入 Spring 等框架）
- **目录结构**: 根目录是 IntelliJ 项目容器，`day01/` 是第一个学习模块（Maven 子模块）
- **入口**: `day01/src/main/java/com/shyc/UserService.java` — 目前只有 `getUserGender(String idCard)` 方法根据身份证号判断性别
- **静态资源**: `day01/src/main/resources/static/` — 预留目录，当前为空

## Commands

```bash
# 编译
cd day01 && mvn compile

# 打包
cd day01 && mvn package

# 清理
cd day01 && mvn clean

# 清除并重新编译
cd day01 && mvn clean compile
```

> 所有 Maven 命令需在 `day01/` 目录下执行，因为它是 pom.xml 所在位置。

## Architecture

- **day01/** — 当前活跃的学习模块，Maven 项目
  - `src/main/java/com/shyc/` — Java 源代码包
  - `src/main/resources/static/` — 静态资源目录（预留）
  - `src/test/` — 测试目录（当前为空）
- **后续模块** — 预计会有 day02、day03 等模块随课程推进添加

## Conventions

- **包名**: `com.shyc` 为根包（对应用户 shiyc）
- **命名**: 类名使用 PascalCase，方法名使用 camelCase
- **无测试**: 目前尚无法测试框架，后续可能引入 JUnit
- **编码**: UTF-8
- **每课一模块**: 按 day01、day02… 组织，每个模块独立 Maven 项目

## Notes

（此处可随时添加快速笔记）

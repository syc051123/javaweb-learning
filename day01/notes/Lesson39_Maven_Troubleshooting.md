# 黑马 JavaWeb 第 39 集学习指导：Maven - 常见问题解决方案

> **视频**：BV1yGydYEE3H，P39（时长 05:38）
> **说明**：这集很短，教你 Maven 出现问题时怎么修。

---

## 最常见的 Maven 问题

### 依赖报红（IDEA 里依赖下方出现红色波浪线）

**原因**：网络波动导致依赖没下载完整，Maven 本地仓库生成了 `xxx.lastUpdated` 文件，Maven 看到这个文件就不会再重新下载。

**解决**：

**方法 A：手动删**
1. 找到 `~/.m2/repository/` 下对应的目录
2. 删除所有 `.lastUpdated` 文件
3. IDEA 点刷新（Reload Maven）

**方法 B：一键删**
```bash
del /s *.lastUpdated
```
在 `~/.m2/repository/` 目录下执行。

**方法 C：如果还不行**
- 关闭 IDEA，重新打开
- 或者 `mvn clean` 后再次 `mvn compile`

---

### 下载依赖特别慢

**原因**：Maven 默认去中央仓库（国外），网速慢。

**解决方法**：你已经配了阿里云镜像了吗？在你的 `D:\java\maven\conf\settings.xml` 里的 `<mirrors>` 标签中加这个：

```xml
<mirror>
    <id>alimaven</id>
    <name>aliyun maven</name>
    <url>http://maven.aliyun.com/nexus/content/groups/public/</url>
    <mirrorOf>central</mirrorOf>
</mirror>
```

以后下载依赖就从阿里云走，快很多。

---

### 命令行 mvn 找不到或提示 ClassNotFoundException

**原因**：`MAVEN_HOME` 或 `Path` 环境变量配置不对。

**检查方法**：
```bash
echo MAVEN_HOME=D:\java\maven
```

看路径是否指向 Maven 解压目录。

---

### 其他常见问题速查

| 现象 | 可能原因 | 解决 |
|------|---------|------|
| IDEA Maven 面板一片红 | IDEA 没找到你的 Maven 配置 | 设置 → Build Tools → Maven → 重新指定 Maven 路径 |
| `mvn compile` 报 Java 版本不对 | pom.xml 里的 `<maven.compiler.source>` 跟系统 JDK 对不上 | 统一改成 21 |
| 依赖导入了但代码里 `import` 报红 | 没刷新 | 点 Maven 面板的刷新按钮 |

---

## 一句话总结

> **Maven 出问题 90% 是网络或缓存问题，删 `.lastUpdated` 或配阿里云镜像能解决大部分。**

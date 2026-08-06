# Tlias 智能学习辅助系统 — 项目搭建 + 部门查询

> **学完后你能做到**：从头搭建 SpringBoot 项目，完成 RESTful 风格的部门查询接口

---

## 一、前后端分离开发

🟡 **概念理解就行**

以前前后端代码写在一起，叫**前后台混合开发**：

```
一个项目里既有 Java 代码又有 HTML/CSS/JS
→ 前端改了要整个项目重新部署
→ 分工不明确
```

现在主流是**前后端分离**：

```
前端工程（Vue/React）  ←→  后端工程（SpringBoot）
         ↓                         ↓
    只负责页面渲染            只负责提供数据接口
    通过 API 调用获取数据       返回 JSON 格式数据
```

两端通过**接口文档**约定数据格式。

---

## 二、RESTful API

🟢 **必须掌握**

REST = **Representational State Transfer**（表述性状态转换）

**拆词根**：
- **Representational** — 表述性的（用 JSON/XML 表述资源）
- **State Transfer** — 状态转换（通过 HTTP 方法改变数据状态）

**核心思想**：**URL 定位资源，HTTP 方法描述操作**

### 传统 vs REST

| 操作 | 传统 URL | REST URL |
|------|---------|----------|
| 查询全部 | `GET /dept/findAll` | `GET /api/depts` |
| 查询单个 | `GET /dept/getById?id=1` | `GET /api/depts/1` |
| 新增 | `POST /dept/save` | `POST /api/depts` |
| 修改 | `POST /dept/update` | `PUT /api/depts` |
| 删除 | `GET /dept/delete?id=1` | `DELETE /api/depts/1` |

**四种请求方法**：

| 请求方式 | 含义 |
|---------|------|
| `GET` | 查询 |
| `POST` | 新增 |
| `PUT` | 修改 |
| `DELETE` | 删除 |

---

## 三、工程搭建

🟢 **知道配了什么就行，配置文件我负责**

### 项目结构

```
day03/
├── pom.xml                        → SpringBoot + MyBatis + MySQL + Lombok
├── src/main/resources/
│   └── application.yml            → 数据源 + MyBatis 配置
└── src/main/java/com/shyc/
    ├── TliasApplication.java      → 启动类
    ├── pojo/
    │   └── Dept.java              → 实体类（Lombok @Data）
    ├── mapper/
    │   └── DeptMapper.java        → MyBatis Mapper 接口
    ├── service/
    │   ├── DeptService.java       → Service 接口
    │   └── impl/
    │       └── DeptServiceImpl.java → Service 实现类
    └── controller/
        └── DeptController.java    → 接收 HTTP 请求
```

### 数据库

```sql
CREATE TABLE dept (
  id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT COMMENT 'ID, 主键',
  name VARCHAR(10) NOT NULL UNIQUE COMMENT '部门名称',
  create_time DATETIME DEFAULT NULL COMMENT '创建时间',
  update_time DATETIME DEFAULT NULL COMMENT '修改时间'
) COMMENT '部门表';
```

6 条初始数据：学工部、教研部、咨询部、就业部、人事部、行政部。

---

## 四、部门查询 — 三层架构

🟢 **这一套流程必须熟练，这是面试必问**

请求流程：

```
浏览器/前端
    ↓ GET /api/depts
DeptController  (接收请求)
    ↓ 调用
DeptService     (业务逻辑)
    ↓ 调用
DeptMapper      (操作数据库)
    ↓ SQL
MySQL
```

### 4.1 DeptMapper —— 持久层

```java
@Mapper
public interface DeptMapper {
    @Select("select * from dept")
    List<Dept> findAll();
}
```

### 4.2 DeptService —— 业务层

**接口**：
```java
public interface DeptService {
    List<Dept> findAll();
}
```

**实现类**：
```java
@Service
public class DeptServiceImpl implements DeptService {

    @Autowired
    private DeptMapper deptMapper;

    @Override
    public List<Dept> findAll() {
        return deptMapper.findAll();
    }
}
```

### 4.3 DeptController —— 控制层

```java
@RestController
@RequestMapping("/api/depts")
public class DeptController {

    @Autowired
    private DeptService deptService;

    @GetMapping
    public List<Dept> findAll() {
        return deptService.findAll();
    }
}
```

---

## 五、测试

启动 `TliasApplication`，访问：

```
http://localhost:8080/api/depts
```

应该返回 JSON 数据：

```json
[
  {"id": 1, "name": "学工部", "createTime": "2023-09-25T09:47:40", "updateTime": "..."},
  {"id": 2, "name": "教研部", ...},
  ...
]
```

---

## 总结

🟢 **你要记住的**：

| 注解 | 作用 |
|------|------|
| `@RestController` | 标记控制层，返回 JSON |
| `@RequestMapping("/api/depts")` | 给 Controller 设置统一路径前缀 |
| `@GetMapping` | 处理 GET 请求 |
| `@Service` | 标记业务层，交给 Spring 管理 |
| `@Autowired` | 自动注入依赖 |
| `@Mapper` | 标记 MyBatis 的 Mapper 接口 |

🟡 **理解就行**：
- 前后端分离的概念
- RESTful API 风格（GET/POST/PUT/DELETE）

# Tlias 部门管理模块 — 完整总结（查询/删除/新增/修改 + 日志）

> **学完后你能做到**：独立完成一个模块的增删改查后端接口（三层架构 + Result 统一响应 + 日志），这是后端开发最核心的套路，员工管理、登录等模块都是同一套骨架。

---

## 一、模块全景

部门管理 = 4 个接口 + 1 套统一响应 + 日志：

| 操作 | 请求方式 | 路径 | 参数在哪 | 返回 |
|------|---------|------|---------|------|
| 查列表 | `GET` | `/depts` | 无 | `success(deptList)` 带数据 |
| 查单个（回显） | `GET` | `/depts/{id}` | 路径里 | `success(dept)` 带数据 |
| 新增 | `POST` | `/depts` | 请求体 JSON | `success()` 不带数据 |
| 修改 | `PUT` | `/depts` | 请求体 JSON | `success()` 不带数据 |
| 删除 | `DELETE` | `/depts?id=1` | URL 问号后 | `success()` 不带数据 |

🟢 **这张表就是 RESTful 的核心，必须背下来。**

---

## 二、三层架构（饭店模型）

🟢 **必须熟练，面试必问**

```
Controller（前台服务员）→ Service（后厨主管）→ Mapper（切菜师傅）→ MySQL
```

**规则**：
- 只能上层调下层，不能反过来
- Controller 不碰数据库，Mapper 不碰 HTTP
- **写代码从下往上**（Mapper → Service → Controller），因为上层依赖下层

**为什么分层**：分工明确（出问题找对应的人）、代码复用（一个 Mapper 方法多个接口用）、改动隔离（换数据库只改 Mapper）。

---

## 三、Result 统一响应类

🟢 **必须掌握**

**为什么需要**：所有接口返回同一个"壳子"，前端统一处理。

```java
@Data
public class Result {
    private Integer code;    // 1=成功，0=失败
    private String message;  // 提示信息
    private Object data;     // 数据（没有就是 null）

    public static Result success() { ... }            // 无数据（增删改）
    public static Result success(Object data) { ... } // 带数据（查询）
    public static Result error(String message) { ... } // 失败，code=0
}
```

**核心点**：
- 三个静态工厂方法，不用手动 new + set
- **查询返回数据（success(data)），增删改只回成功（success()）**
- code 是 1/0，不是 HTTP 的 200/500（业务码和 HTTP 码是两回事）

---

## 四、参数接收的三种方式（重点对比）

🟢 **必须掌握**，三种方式按"参数在哪"区分：

### 1. 简单参数（URL 问号后）

```
DELETE /depts?id=1
```

```java
public Result deleteById(Integer id)   // 参数名和 URL 参数名一致，自动绑定
```

### 2. JSON 参数（请求体里）—— @RequestBody

```
POST /depts   请求体: {"name":"研发部"}
```

```java
public Result addDept(@RequestBody Dept dept)  // 拆 JSON → 装进对象
```

**规则**：JSON 键名 = 对象属性名；`@RequestBody` 加在参数前。

**类比**：请求体是快递箱，JSON 是货，`@RequestBody` 是让 Spring 拆箱装货。

### 3. 路径参数（URL 路径里）—— @PathVariable

```
GET /depts/3
```

```java
@GetMapping("/{id}")
public Result findById(@PathVariable Integer id)  // 从路径洞 {id} 取 3
```

**规则**：路径用 `{id}` 挖洞，`@PathVariable` 把洞里值装进参数。

### 一句话区分

| 参数在哪 | 用什么 |
|---------|--------|
| `?id=1`（问号后） | 直接写参数，自动绑定 |
| 请求体 JSON | `@RequestBody` |
| 路径里 `/3` | `@PathVariable` + `/{id}` |

---

## 五、Mapper 注解四件套

🟢 **必须掌握**

| 注解 | 用途 | 示例 |
|------|------|------|
| `@Select` | 查询 | `@Select("select * from dept where id = #{id}")` |
| `@Insert` | 插入 | `@Insert("insert into dept(name, create_time, update_time) values(#{name},#{createTime},#{updateTime})")` |
| `@Update` | 修改 | `@Update("update dept set name=#{name}, update_time=#{updateTime} where id=#{id}")` |
| `@Delete` | 删除 | `@Delete("delete from dept where id = #{id}")` |

**关键**：
- `#{属性名}` 是占位符（防 SQL 注入），写的是**对象属性名**（驼峰），不是表字段名
- `#{createTime}` 不能写成 `#{creatTime}` 或 `#{upDateTime}` —— 拼错就报错/插空
- 参数是对象时（如 `Dept dept`），`#{}` 里写它的属性名

---

## 六、Service 层的业务逻辑

🟢 **必须掌握"什么时候 Service 要干活"**

- **删除/查询**：没业务逻辑，直接转手 Mapper
- **新增**：要补时间（`createTime` + `updateTime`）
- **修改**：要补时间（只补 `updateTime`，`createTime` 不能动）

```java
// 新增
public void addDept(Dept dept){
    dept.setCreateTime(LocalDateTime.now());
    dept.setUpdateTime(LocalDateTime.now());
    deptMapper.addDept(dept);
}

// 修改
public void updateDept(Dept dept){
    dept.setUpdateTime(LocalDateTime.now());
    deptMapper.updateDept(dept);
}
```

**为什么时间在 Service 补**：前端不知道服务器时间，补全基础属性是"业务规则"，归 Service 管。

---

## 七、日志技术（Logback + @Slf4j）

🟡 **看得懂、会用 log.info 就行**

**为什么不用 System.out.println**：
1. 不能关（上线想关只能删代码）
2. 不分级（分不清正常/报错）
3. 不能存文件（崩溃后查不到）

**三个名词**：
- **Slf4j** = Simple Logging Facade for Java = "日志门面"（统一喊话口）
- **Logback** = 真正干活的引擎（SpringBoot 自带）
- **`@Slf4j`** = Lombok 注解，自动生成 `log` 对象

**用法三步**：

```java
import lombok.extern.slf4j.Slf4j;  // ① import

@Slf4j                             // ② 类上加注解
public class DeptController {
    public void xxx(){
        log.info("查询部门列表");          // ③ 方法里用 log.info
        log.info("根据ID查询部门, id: {}", id);  // {} 占位符自动填值
    }
}
```

**日志级别**（低→高）：TRACE < DEBUG < **INFO** < WARN < ERROR
- 日常用 `log.info(...)`，出错用 `log.error(...)`
- 级别可配置过滤，这是"可开关"的关键

**日志输出长这样**（自动带时间/线程/级别/类名）：
```
2026-08-01T20:46:12 INFO [nio-8080-exec-1] com.shyc.controller.DeptController : 查询所有部门信息
```

---

## 八、本模块踩过的坑（记下来）

| 坑 | 原因 | 解法 |
|----|------|------|
| `#{upDateTime}` 报错 | 属性名拼错（是 `updateTime`） | `#{}` 里写对象属性名，注意大小写 |
| Mapper 方法少分号 | 语法错误 | 方法结尾 `;` |
| Apifox 400 | Body 没选 raw+JSON | Body → raw → 右侧下拉选 JSON |
| 服务没重启就测 | 跑的是旧代码 | 改代码后必须重启 |

---

## 九、套路总结（一图流）

```
写任何 CRUD 接口 = 同一个骨架：
1. Mapper：注解 + SQL（从下往上写）
2. Service 接口：声明方法
3. Service 实现：补业务（时间等）+ 调 Mapper
4. Controller：映射注解 + 参数接收注解 + 调 Service + 返回 Result
```

**你已掌握的全部注解**：
`@RestController` `@RequestMapping` `@GetMapping` `@PostMapping` `@PutMapping` `@DeleteMapping` `@RequestBody` `@PathVariable` `@Mapper` `@Select` `@Insert` `@Update` `@Delete` `@Autowired` `@Service` `@Override` `@Slf4j` `@Data`

---

## 十、下一步预告

- **员工管理**：多表查询、分页、条件查询（复用本模块套路 + 新知识点）
- **登录认证**：JWT、会话技术
- **AOP 日志**：用切面自动记录操作日志（比手动 log.info 更高级）

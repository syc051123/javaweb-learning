# 🚀 Tlias 员工管理 — 有趣版总结笔记

> 🎯 **学完你能做到**:独立写出员工管理的"分页、条件查询、新增、批量删除、修改、文件上传"六个接口,还懂日志分级。这是后端 CRUD 的**终极套路**,部门、员工、日志、登录全是这套骨架。
>
> ✨ **风格说明**:每节有 emoji + 生活类比,看不懂代码时想想例子。

---

## 🗺️ 一、员工管理全景(先看地图,别迷路)

```
员工管理 = 6 个接口,全在 /emps 下
                ┌─────────────────────────┐
   分页查询 ────┤ GET    /emps             │  (查一页)
   条件查询 ────┤ GET    /emps?name=张      │  (筛人)
   新增      ────┤ POST   /emps             │  (加人)
   批量删除  ────┤ DELETE /emps?ids=1,2,3  │  (勾选删)
   修改      ────┤ PUT    /emps             │  (改信息)
   回显      ────┤ GET    /emps/{id}        │  (编辑前先查)
   文件上传  ────┤ POST   /upload           │  (传头像图片)
                └─────────────────────────┘
             全部返回 Result 统一响应壳
```

🎭 **一句话把六个接口活起来**:**查一页 → 筛几个人 → 加个新人 → 批量不要了 → 改信息 → 传个头像**,这就是员工管理的日常。

---

## 🍔 二、参数"三个注解"——参数住哪,决定用谁(必背)

**核心问题:前端的数据放在请求的哪个位置?**

```
URL http://localhost:8080/emps/3?name=张 + 请求体 JSON
                       └┬─┘       └┬──┘    └────┬───┘
                    路径里        问号后         body里
                  @PathVariable @RequestParam  @RequestBody
                    (单个资源)   (批量/条件)    (一整坨数据)
```

### 类比:寄快递 🚚

| 注解 | 参数住哪 | 生活例子 | 项目例子 |
|------|---------|---------|---------|
| `@PathVariable` | **路径里** `/emps/3` | 门牌号(定位具体房间) | 查单个 `/emps/{id}` |
| `@RequestParam` | **问号后** `?ids=1,2,3` | 快递单备注(额外信息) | 批量删/条件查 |
| `@RequestBody` | **body(请求体)** | 包裹里的货(一坨) | 新增/修改传整个对象 |

### 判断口诀(背这个)

> **单个具体资源 → 路径 `@PathVariable`**
> **多个/批量/条件 → 问号 `@RequestParam`**
> **一整坨数据 → 请求体 `@RequestBody`**

### 对照所有接口(你自己写过的,验证)

```java
@GetMapping("/{id}")   selectById(@PathVariable Integer id)   // 路径,查单个
@GetMapping            page(@RequestParam ... )                // 问号,条件分页
@DeleteMapping         deleteEmp(@RequestParam List<Integer> ids)  // 问号,批量删
@PostMapping           insertEmp(@RequestBody Emp emp)         // body,新增
@PutMapping            updateEmp(@RequestBody Emp emp)         // body,修改
```

🔥 **铁律**:`@PathVariable`/`@RequestParam`/`@RequestBody` 是 **Controller 的专属**,只能用在 Controller 层的方法参数上!Mapper 里只能写普通类型参数。看 import 包名:web.bind 是 Controller 的,ibatis 是 Mapper 的。

---

## 📦 三、Mapper 命名规范(阿里手册,看着专业)

| 操作 | SQL | Mapper 方法名(推荐) |
|------|-----|---------------------|
| 查全部 | SELECT | `selectAll` |
| 查单个 | SELECT WHERE id | `selectById` |
| 增 | INSERT | `insert` / `insertEmp` |
| 删 | DELETE | `deleteById` / `deleteByIds`(批量) |
| 改 | UPDATE | `update` / `updateEmp` |

✅ **你项目里已经统一成这套**(部门+员工),GitHub 上看很规范。

---

## 🔁 四、批量删除 + `<foreach>`(第一个新知识点)

**需求**:前端勾选几个员工,`?ids=1,2,3` 一次删掉。

**最终 SQL 长这样**:
```sql
DELETE FROM emp WHERE id IN (1,2,3)
```

**问题**:`IN` 里面几个数不固定,不能写死 → 用 `<foreach>` 动态拼。

### `<foreach>` 拆解(背属性表)

```xml
<foreach collection="ids" item="id" open="(" separator="," close=")">
    #{id}
</foreach>
```

| 属性 | 值 | 意思 | Java 类比 |
|------|-----|------|-----------|
| `collection` | `ids` | 遍历哪个集合 | `for(Integer id : ids)` |
| `item` | `id` | 每次的变量名 | `for(Integer id : ...)` |
| `open` | `(` | 开头拼啥 | `sb.append("(")` |
| `separator` | `,` | 中间拼啥 | `if(i>0) sb.append(",")` |
| `close` | `)` | 结尾拼啥 | `sb.append(")")` |

**执行效果**:`ids=[1,2,3]` → 拼出 `(1,2,3)`

### 🚨 三个易错点(你踩过的)

1. **属性之间用空格,不是逗号** → `open="(" separator=","`(中间空格)
2. **`collection` 必须和参数名一致** → 参数 `List<Integer> ids`,collection 写 `ids`
3. **SQL 前缀别漏** → `<foreach>` 前要写 `delete from emp where id in`

### 为什么用 `List` 不用数组

| | List | 数组 |
|--|------|------|
| Spring 绑 `?ids=1,2,3` | ✅ 方便 | 麻烦 |
| MyBatis `<foreach>` | `collection` 写参数名 | 得写死 `array` |
| 增删/处理 | 灵活 | 死板 |

---

## ✏️ 五、修改 + 回显(为什么 selectById 和 update 是一对)

**前端编辑员工,实际发出两次请求:**

```
点"编辑"         →  GET /emps/3     ← selectById:把数据填进表单(回显)
                    (先查出来给你看)
改几个字段
点"保存"         →  PUT /emps       ← updateEmp:整个对象写回数据库
                    (把改完的存回去)
```

### `selectById`(回显)

```java
// GET /emps/3
@GetMapping("/{id}")
public Result selectById(@PathVariable Integer id){
    return Result.success(empService.selectById(id));
}
```

### `updateEmp`(更新)

```java
// PUT /emps, body 传整个 Emp
@PutMapping
public Result updateEmp(@RequestBody Emp emp){
    empService.updateEmp(emp);
    return Result.success();
}
```

### ⚠️ UPDATE 字段选择(重要)

**只更新表单里能改的字段**,排除 username/password:

```sql
UPDATE emp SET
    name=#{name}, gender=#{gender}, phone=#{phone},
    job=#{job}, salary=#{salary}, image=#{image},
    entry_date=#{entryDate}, update_time=#{updateTime},
    dept_id=#{deptId}
WHERE id=#{id}
```

**为什么 username/password 不写**:① 前端编辑时不传这俩 → 会变 null → 数据库被清空!② 有安全风险。

### "整条提交" vs "部分更新"

- **Tlias 是整条提交**:前端回显后再整体 PUT,所有字段都有值 → **全量 UPDATE 就够**
- **只改部分字段**(PATCH):才需要用 `<set>`+`<if>` 只更新非 null(现在不用学,了解)

---

## 📤 六、文件上传(`MultipartFile`,第二个新知识点)

**需求**:传一张头像图片,存到服务器,返回 URL 给前端存到 image 字段。

### 流程

```
前端选图
  → POST /upload (form-data,字段名 file)
  → MultipartFile 收到文件
  → UUID 改名 + 存到 D:/tlias-imgs/
  → 返回 http://localhost:8080/images/文件名
```

### 核心代码逻辑(你来写的)

```java
@PostMapping("/upload")
public Result upload(MultipartFile file) throws Exception {
    String originalFilename = file.getOriginalFilename();  // ① 原始文件名 "头像.jpg"
    String ext = originalFilename.substring(originalFilename.lastIndexOf(".")); // ② ".jpg"
    String newFileName = UUID.randomUUID() + ext;          // ③ uuid.jpg
    File dir = new File(UPLOAD_DIR);
    if (!dir.exists()) dir.mkdirs();                       // ④ 建目录
    file.transferTo(new File(dir, newFileName));           // ⑤ 存盘
    return Result.success("http://localhost:8080/images/" + newFileName); // ⑥ 返回URL
}
```

### 三个安全细节(记下来)

| 点 | 为什么 |
|----|--------|
| **UUID 重命名** | 防重名覆盖、防路径攻击(`../../` 逃逸) |
| **只留后缀** | 保留图片类型,主名不可控 |
| **存独立目录** | 不污染项目,重启不丢 |

### 静态资源映射(WebConfig,让图片能被浏览器访问)

```java
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**")      // 请求 /images/xx.jpg
                .addResourceLocations("file:D:/tlias-imgs/"); // 去 D盘找
    }
}
```

**作用**:文件在 `D:/tlias-imgs/`,但浏览器要 `http://localhost:8080/images/xx.jpg` 能打开 → 靠这个映射"指路"。

### ⚠️ 测试坑:上传用 form-data,不是 JSON!

Apifox 里:Baby(错误) → Body **选 form-data**,key 填 `file`(要和 Controller 参数名一致),value 选文件。

---

## 📋 七、日志(第三个小知识点,别怕)

### 日志是啥
程序写的"运行日记",记下"干了啥、出啥事"。程序不能说话,靠日志告诉你。

### 级别(从低到高)
```
TRACE < DEBUG < INFO < WARN < ERROR
```

**怎么选**:
- `log.info` → 正常操作("查询所有部门")
- `log.error` → 出错了("数据库连不上")

### 怎么"拦住"噪音(挡 MyBatis SQL)

**原理:设个"门槛",低于它的日志不打。**

```xml
<logger name="org.apache.ibatis" level="warn" />
```

MyBatis 打 SQL 是 DEBUG 级(< WARN),被拦;你的 `log.info`(com.shyc 包)门槛还是 info,照常打。

**生活类比**:夜店查身份证 → 年龄(级别)够门槛才放进来。

### 最重要的:会看报错(排错核心)

出错时日志末端那堆红色的,只看**最上面几行**:
```
Exception: xxx                  ← 什么错
    at com.shyc.xxx.method(x.java:30)  ← 哪行错
```

**现在会这些就够了**:打 `log.info` + 看报错堆栈前几行 + 理解"级别门槛过滤"。别的用到再查。

---

## ⚠️ 八、你踩过的坑(复习必看)

| 坑 | 原因 | 解法 |
|----|------|------|
| Mapper 里用 `@PathVariable` | 那是 Controller 的注解 | Mapper 只写普通参数,`Integer id` |
| `@RequestParam` 写成 `@RequestBody`(删除) | 参数在 URL 不在 body | 看参数在哪:URL→RequestParam,body→RequestBody |
| `<foreach>` 属性间用逗号 | XML 属性用空格 | `open="(" separator=","` |
| update 漏逗号 | SQL 多个 SET 字段 | 前几个字段逗号,最后的不逗 |
| update 写 username/password | 会被清空/不安全 | 只更新表单可改字段 |
| Mapper 参数用 `String` 而 `Integer` | id 是整数 | 主键用 `Integer` |
| URL 写 `/imgs` 而不是 `/images` | 和静态映射不一致 | 两端统一 |
| 上传用 JSON | 文件不能塞 JSON | 用 form-data |
| 改代码不重启 | 跑旧代码 | 改完必须重启 |

---

## 🧠 九、套路总结:员工管理 = 部门管理的"升级版"

**你已经会的一套底座**(部门管理学的):
```
Controller → Service → Mapper → 数据库
写代码从下往上:Mapper → Service → Controller
统一返回 Result
```

**员工管理在底座上加的三个新料**:
1. `<foreach>` 批量操作(IN 列表)
2. `MultipartFile` 文件上传
3. 日志级别过滤

**部门 + 员工都通关 = CRUD 套路彻底掌握。** 下一个模块(登录认证 JWT)会在这套骨架上再加"拦截器"。

---

## 🎯 十、一句话总结(背 3 句)

1. **参数三兄**:单个走路径 `@PathVariable`,批量/条件走问号 `@RequestParam`,一坨数据走 body `@RequestBody`
2. **批量用 `<foreach>`**:把 Java 的 List 拼成 SQL 的 `IN (1,2,3)`
3. **日志**:会打 `log.info`、会看报错堆栈前几行、懂"级别门槛过滤"

**你学完员工管理,已经能独立完成后端 CRUD 的绝大部分——这很了不起。** 🎉

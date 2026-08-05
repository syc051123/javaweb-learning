# -*- coding: utf-8 -*-
"""
生成 JavaWeb 学习 Anki 卡组(.apkg) —— 精简版
只保留:面试必问 + 写代码必用且容易混的知识点
运行: python note/anki/generate_cards.py
输出: D:\desktop\JavaWeb学习卡组.apkg
"""
import genanki

CSS = """
:root {
  --bg-top: #667eea;
  --bg-bottom: #764ba2;
  --accent: #5b6ee8;
  --accent-soft: #eef1fd;
  --text-main: #1f2430;
  --text-sub: #7b8194;
  --text-on-gradient: #ffffff;
  --code-bg: #f4f6fb;
  --code-border: #e3e7f2;
  --answer-color: #2f3542;
}

/* ---------- 卡片整体 ---------- */
.card {
  font-family: -apple-system, "Segoe UI", "PingFang SC", "Microsoft YaHei", sans-serif;
  font-size: 19px;
  line-height: 1.85;
  color: var(--text-main);
  background: #ffffff;
  border-radius: 20px;
  box-shadow: 0 10px 30px rgba(90, 100, 160, 0.18);
  padding: 30px 34px 34px;
  margin: 6px auto;
  max-width: 760px;
  text-align: left;
  border: 1px solid #eef0f8;
}

/* ---------- 顶部渐变条 ---------- */
.card::before {
  content: "";
  display: block;
  height: 6px;
  margin: -30px -34px 26px;
  border-radius: 20px 20px 0 0;
  background: linear-gradient(90deg, var(--bg-top), var(--bg-bottom));
}

/* ---------- 标签行 ---------- */
.topbar {
  display: flex;
  gap: 8px;
  margin-bottom: 18px;
}
.badge {
  display: inline-block;
  font-size: 12.5px;
  font-weight: 600;
  letter-spacing: 0.4px;
  color: var(--text-on-gradient);
  background: linear-gradient(135deg, var(--bg-top), var(--bg-bottom));
  padding: 4px 14px;
  border-radius: 999px;
  box-shadow: 0 3px 8px rgba(102, 126, 234, 0.35);
}
.badge.soft {
  background: var(--accent-soft);
  color: var(--accent);
  box-shadow: none;
}

/* ---------- 问题 ---------- */
.front-q {
  font-size: 23px;
  font-weight: 700;
  color: var(--text-main);
  line-height: 1.6;
  margin-bottom: 6px;
}
.answer-q {
  font-size: 17px;
  font-weight: 600;
  color: var(--text-sub);
  line-height: 1.6;
  margin-bottom: 4px;
  padding-bottom: 16px;
  border-bottom: 1px dashed #e4e8f4;
}

/* ---------- 答案 ---------- */
.answer {
  margin-top: 18px;
  font-size: 19px;
  color: var(--answer-color);
}
.answer b {
  color: #3d52d5;
}

/* ---------- 代码 ---------- */
code {
  font-family: "JetBrains Mono", "Cascadia Code", Consolas, monospace;
  font-size: 0.88em;
  background: var(--code-bg);
  border: 1px solid var(--code-border);
  color: #d6336c;
  padding: 2px 7px;
  border-radius: 7px;
}
pre {
  font-family: "JetBrains Mono", "Cascadia Code", Consolas, monospace;
  background: var(--code-bg);
  border: 1px solid var(--code-border);
  border-left: 4px solid var(--accent);
  color: #27406e;
  padding: 12px 16px;
  border-radius: 10px;
  font-size: 16px;
  line-height: 1.6;
  overflow-x: auto;
}

/* ---------- 分隔线 ---------- */
hr {
  border: none;
  border-top: 1px dashed #e4e8f4;
  margin: 20px 0;
}

/* ---------- 提示文字(正面底部) ---------- */
.hint {
  margin-top: 26px;
  font-size: 13px;
  color: var(--text-sub);
  text-align: center;
  letter-spacing: 1px;
}
.hint::before {
  content: "▼ ";
  color: var(--accent);
}

/* ---------- 夜间模式 ---------- */
.nightMode .card {
  background: #232733;
  border-color: #30364a;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.4);
}
.nightMode .front-q,
.nightMode .answer,
.nightMode .answer-q {
  color: #e8eaf2;
}
.nightMode .answer b {
  color: #9fb0ff;
}
.nightMode .badge.soft {
  background: #2c3150;
  color: #a8b4ff;
}
.nightMode code {
  background: #1b1f2b;
  border-color: #2f3550;
  color: #ff8fb8;
}
.nightMode pre {
  background: #1b1f2b;
  border-color: #2f3550;
  color: #b8c6e8;
}
.nightMode .answer-q,
.nightMode .hint {
  color: #8b93ad;
}
.nightMode .answer-q {
  border-bottom-color: #333a52;
}
.nightMode hr {
  border-top-color: #333a52;
}
"""

# ---------- 模板(正反面) ----------
QFMT = """
<div class="topbar"><span class="badge">JavaWeb</span><span class="badge soft">{{Subdeck}}</span></div>
<div class="front-q">{{Question}}</div>
<div class="hint">点击显示答案</div>
""".strip()

AFMT = """
<div class="topbar"><span class="badge">JavaWeb</span><span class="badge soft">{{Subdeck}}</span></div>
<div class="answer-q">{{Question}}</div>
<div class="answer">{{Answer}}</div>
""".strip()

MODEL = genanki.Model(
    1699010001,
    "JavaWeb 问答",
    fields=[
        {"name": "Question"},
        {"name": "Answer"},
    ],
    templates=[
        {
            "name": "问答卡片",
            "qfmt": QFMT,
            "afmt": AFMT,
        },
    ],
    css=CSS,
)

DECKS = [
    ("JavaWeb学习::01 HTML与CSS", [
        ("<code>div</code> 和 <code>span</code> 有什么区别?",
         "<code>div</code> 是<b>块级</b>元素,独占一行,用来分块布局。<br><code>span</code> 是<b>行内</b>元素,不换行,用来包一段文字。"),
        ("CSS 盒模型由哪四部分组成?",
         "<b>content</b>(内容)→ <b>padding</b>(内边距)→ <b>border</b>(边框)→ <b>margin</b>(外边距)"),
        ("<code>display: flex</code> 一行内水平居中怎么写?",
         "父容器:<code>display: flex</code> + <code>justify-content: center</code>(水平)+ <code>align-items: center</code>(垂直)。管理后台布局核心。"),
    ]),
    ("JavaWeb学习::02 JavaScript", [
        ("<code>let</code> 和 <code>var</code> 的区别?",
         "<code>let</code>:<b>块级作用域</b>,不能重复声明,推荐。<br><code>var</code>:函数作用域,可重复声明,易踩坑。"),
        ("<code>const</code> 和 <code>let</code> 的区别?",
         "<code>const</code> 声明<b>常量</b>,赋值后不能改、声明时必须赋值;<br><code>let</code> 声明变量,可重新赋值。"),
        ("函数定义的两种方式?",
         "① 声明式:<code>function f() {}</code><br>② 箭头函数:<code>const f = () => {}</code>(现代写法,Vue 里常用)"),
        ("JS 对象和 JSON 怎么互转?",
         "对象→JSON:<code>JSON.stringify(obj)</code><br>JSON→对象:<code>JSON.parse(json)</code><br>JSON 本质是字符串,前后端传数据都用它。"),
        ("获取页面元素 + 绑定点击事件的写法?",
         "获取:<code>document.getElementById('id')</code> 或 <code>querySelector('选择器')</code><br>绑定:<code>btn.onclick = function() {}</code> 或 <code>btn.addEventListener('click', fn)</code>"),
    ]),
    ("JavaWeb学习::03 Maven", [
        ("Maven 是干什么的?",
         "项目<b>构建</b>和<b>依赖管理</b>工具:自动下载 jar、编译、测试、打包。"),
        ("Maven 坐标由哪三部分组成?",
         "<code>groupId</code>(公司域名反写,如 com.shyc)+ <code>artifactId</code>(项目名)+ <code>version</code>(版本号)"),
        ("常用 Maven 命令有哪些?",
         "<code>clean</code> 清理 / <code>compile</code> 编译 / <code>test</code> 测试 / <code>package</code> 打包 / <code>install</code> 装进本地仓库"),
    ]),
    ("JavaWeb学习::04 HTTP协议", [
        ("HTTP 四种请求方式分别对应什么操作?",
         "<code>GET</code> 查询<br><code>POST</code> 新增<br><code>PUT</code> 修改<br><code>DELETE</code> 删除"),
        ("状态码 200 / 404 / 500 分别代表什么?",
         "<code>200</code> 请求成功<br><code>404</code> 资源不存在(路径写错)<br><code>500</code> 服务器内部错误(后端代码异常)"),
        ("GET 和 POST 的区别?",
         "GET:参数拼在 URL,<b>有长度限制、不安全</b>,用于查询。<br>POST:参数在<b>请求体</b>,更安全、无长度限制,用于提交。"),
        ("HTTP 无状态是什么意思?怎么解决?",
         "服务器<b>不记得</b>上次请求,每次独立。<br>解决:Session/Cookie 或 JWT 令牌(后面 Tlias 登录认证用 JWT)。"),
    ]),
    ("JavaWeb学习::05 SpringBoot", [
        ("<code>@RestController</code> 的作用?",
         "标记<b>控制层</b>,= @Controller + @ResponseBody。<br>方法返回值直接转成 JSON 返回给前端。"),
        ("<code>@RequestMapping</code> / <code>@GetMapping</code> / <code>@PostMapping</code> 的区别?",
         "<code>@RequestMapping</code>:通用映射(类上写=统一前缀)。<br>@GetMapping/@PostMapping:@RequestMapping 的快捷注解,限定只处理 GET/POST。"),
        ("启动类上的 <code>@SpringBootApplication</code> 包含什么?",
         "三个合一:<br>@SpringBootConfiguration(配置)+ @EnableAutoConfiguration(自动配置)+ @ComponentScan(组件扫描)"),
    ]),
    ("JavaWeb学习::06 三层架构与IOC/DI", [
        ("三层架构是哪三层?一次请求怎么流转?",
         "Controller(接收请求)→ Service(业务)→ Mapper/Dao(数据库),数据再逐层返回。<br><b>依赖方向:只能上层调下层</b>,写代码从底层往上。"),
        ("IOC 和 DI 分别是什么?",
         "<b>IOC</b>(控制反转):对象创建权从程序员交给 Spring 容器,不用 new。<br><b>DI</b>(依赖注入):Spring 把你需要的对象自动塞进来。"),
        ("三层各用什么注解声明 Bean?",
         "Controller 层:<code>@RestController</code><br>Service 层:<code>@Service</code><br>Mapper 层:<code>@Mapper</code>(MyBatis)<br>不属于三层:<code>@Component</code>"),
        ("<code>@Autowired</code> 是干什么的?",
         "<b>依赖注入</b>:告诉 Spring\"我需要这个类型的对象,帮我注入进来\"。"),
    ]),
    ("JavaWeb学习::07 MySQL", [
        ("SQL 按功能分哪几类?",
         "<b>DDL</b> 定义表结构 / <b>DML</b> 增删改数据 / <b>DQL</b> 查询数据 / <b>DCL</b> 权限控制"),
        ("主键是什么?有什么特点?",
         "主键(PRIMARY KEY)= <b>唯一标识一行</b>的字段。<br>唯一、非空,通常配 <code>AUTO_INCREMENT</code> 自增。"),
        ("常用的约束有哪些?",
         "<code>NOT NULL</code> 非空<br><code>UNIQUE</code> 唯一<br><code>PRIMARY KEY</code> 主键<br><code>FOREIGN KEY</code> 外键(关联别的表)"),
    ]),
    ("JavaWeb学习::08 MyBatis", [
        ("MyBatis 是什么?",
         "<b>持久层框架</b>,简化 JDBC:SQL 写在注解/XML 里,自动完成参数映射和结果封装。"),
        ("<code>#{}</code> 和 <code>${}</code> 的区别?(面试常问)",
         "<code>#{}</code>:<b>预编译占位符</b>,防 SQL 注入,推荐。<br><code>${}</code>:字符串直接拼接,有注入风险,尽量不用。"),
        ("实体字段名和表字段名对不上(驼峰 vs 下划线)怎么办?",
         "① 开启 <b>camel-case 驼峰映射</b>(application.yml 配置)<br>② 或 SQL 里给列起别名。"),
    ]),
    ("JavaWeb学习::09 Tlias与RESTful", [
        ("RESTful 的核心思想?",
         "<b>URL 定位资源,HTTP 方法描述操作</b>。<br>URL 只写名词,动词交给 GET/POST/PUT/DELETE。"),
        ("查询、新增、修改、删除的 REST 写法?",
         "查询:<code>GET /depts</code><br>新增:<code>POST /depts</code><br>修改:<code>PUT /depts</code><br>删除:<code>DELETE /depts/{id}</code>"),
        ("统一响应体 Result 的三个字段?",
         "<code>code</code>:1 成功 / 0 失败<br><code>msg</code>:提示信息<br><code>data</code>:真正的数据(失败为 null)<br>前端先看 code,再取 data。"),
        ("前后端分离开发是什么?",
         "前端工程、后端工程<b>分开</b>开发,通过<b>接口文档</b>约定格式。<br>前端调 API 拿 JSON,后端只提供接口,互不干扰。"),
    ]),
    ("JavaWeb学习::10 JUnit单元测试", [
        ("单元测试是什么?和 main 方法测试的区别?",
         "给代码<b>每个方法单独做体检</b>。<br>JUnit:一键跑全部,<b>绿条=通过 红条=失败</b>,写一次永远复用;main 测试跑一次看一次。"),
        ("<code>@Test</code> 和 <code>Assertions.assertEquals(期望, 实际)</code> 怎么用?",
         "<code>@Test</code>:标记测试方法。<br><code>assertEquals</code>:断言相等,失败自动提示\"期望 男,拿到 女\"。<br>导包注意是 <code>org.junit.jupiter.api</code>(JUnit5)。"),
        ("<code>@BeforeEach</code> 和 <code>@BeforeAll</code> 的区别?",
         "<code>@BeforeEach</code>:<b>每个</b> @Test 前执行(非 static),初始化对象。<br><code>@BeforeAll</code>:类开始前执行<b>一次</b>(必须 static),连数据库。"),
    ]),
    ("JavaWeb学习::11 JDBC", [
        ("JDBC 是什么?",
         "Java DataBase Connectivity(<b>Java 数据库连接</b>)。<br>Java 操作数据库的最底层标准 API,MyBatis 是它的封装。"),
        ("<code>PreparedStatement</code> 为什么比拼 SQL 安全?",
         "用 <code>?</code> 占位符 + <code>setXxx()</code> 设参,<b>预编译、防 SQL 注入</b>。"),
        ("<code>executeQuery()</code> 和 <code>executeUpdate()</code> 的区别?",
         "<code>executeQuery()</code>:执行 <b>SELECT</b>,返回 <code>ResultSet</code> 结果集。<br><code>executeUpdate()</code>:执行增删改,返回<b>影响行数</b>。"),
    ]),
    ("JavaWeb学习::12 MyBatis参数与XML", [
        ("<code>@Param</code> 注解是干什么的?",
         "Mapper 传<b>多个参数</b>时给参数起名,SQL 用 <code>#{名字}</code> 引用。<br>企业开发建议写:@Param 语义清晰、不依赖编译参数。"),
        ("MyBatis XML 映射文件的三大规范?",
         "① <b>同包同名</b>:XML 和 Mapper 接口同包同名<br>② <b>namespace</b> = 接口全限定名<br>③ SQL 标签 <b>id</b> = 方法名"),
        ("什么是动态 SQL?什么时候用?",
         "SQL 按条件<b>动态拼接</b>,适合复杂查询。<br>常用标签:<code>&lt;if&gt;</code> 条件 / <code>&lt;where&gt;</code> 自动加 WHERE / <code>&lt;foreach&gt;</code> 遍历集合。"),
    ]),
]

deck_list = []
for deck_name, cards in DECKS:
    deck = genanki.Deck(1699020000 + len(deck_list), deck_name)
    for q, a in cards:
        note = genanki.Note(model=MODEL, fields=[q, a])
        deck.add_note(note)
    deck_list.append(deck)

out = r"D:\desktop\JavaWeb学习卡组.apkg"
genanki.Package(deck_list).write_to_file(out)
total = sum(len(c) for _, c in DECKS)
print(f"生成成功: {out}")
print(f"卡片总数: {total} 张,卡组 {len(DECKS)} 个")

# ---------- 导出样式 JSON(供 Anki addon 更新模板用,不改卡片数据) ----------
import json
style = {
    "css": CSS,
    "qfmt": QFMT,
    "afmt": AFMT,
}
with open(r"D:\javaweb-learning\note\anki\card_style.json", "w", encoding="utf-8") as f:
    json.dump(style, f, ensure_ascii=False, indent=2)
print("样式已导出: note/anki/card_style.json")

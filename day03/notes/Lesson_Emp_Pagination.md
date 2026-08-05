# 员工分页查询 — 通俗笔记（PageHelper + PageInfo）

> **一句话**：分页 = 不一次查全部，只查"第几页的几条"，同时告诉前端"总共有多少条"。PageHelper 帮你自动干这个。

---

## 一、为什么要分页（先懂动机）

🟢 **必懂**

假设员工有 30 条。**不分页** = 一次全查出来返回前端：

```
❌ 30条一次全返回 → 前端表格一页放不下,滚动半天;数据到 10 万条时,直接卡死
```

**分页** = 前端说"我要第 1 页,每页 10 条"：

```
✅ 前端: GET /emps?page=1&pageSize=10
   后端返回: { total: 30, rows: [10条] }   ← 总共30条 + 本页10条
```

**为什么还要 total(总数)?** 前端要算"共几页"来显示页码按钮（1 2 3...），所以分页必须返回"总数 + 本页数据"两样。

---

## 二、分页的"手动版"（理解原理）

🟡 **了解原理，才能懂 PageHelper 帮你省了啥**

如果不用 PageHelper，你得自己写：

```sql
-- 查第1页,每页10条
SELECT * FROM emp LIMIT 0, 10;
-- 还要单独数总数
SELECT COUNT(*) FROM emp;
```

```java
// 手动分页的逻辑(offset = 跳过的条数)
int offset = (page - 1) * pageSize;   // 第2页 → 跳过10条 → LIMIT 10, 10
List<Emp> list = empMapper.page(offset, pageSize);  // SQL 里写 LIMIT
Long total = empMapper.count();                     // 单独查总数
```

**麻烦在哪**：① 每个分页查询都要写 LIMIT ② 还要单独写 count ③ offset 要自己算。

**PageHelper 就是把这些全自动了**——这就是插件存在的意义。

---

## 三、PageHelper 三步曲（核心，背下来）

🟢 **必须掌握**

```java
PageHelper.startPage(page, pageSize);          // ① 登记分页
List<Emp> list = empMapper.findAll();          // ② 正常查(插件自动加LIMIT)
PageInfo<Emp> info = new PageInfo<>(list);     // ③ 自动算total
```

### ① startPage(page, pageSize)
**"登记"**：告诉 PageHelper"下一条查询要分页"。它不立刻执行，只是记下"第几页、每页几条"。

### ② empMapper.findAll()
**你写的是正常 SQL（没 LIMIT）**——但 PageHelper 拦截了这次查询，**偷偷在 SQL 末尾加 LIMIT**：

```
你写的: select e.*, d.name dept_name from emp e left join dept d ...
实际执行: 上面那条 + LIMIT 0, 10    ← 插件加的!
```

### ③ new PageInfo<>(list)

**PageHelper 又偷偷执行了 `SELECT COUNT(*)`**，把"总数 30"和"本页 10 条"都塞进 PageInfo。

**你写的 SQL 里没有 COUNT——插件帮你数了。** 这就是插件的魔法。

---

## 四、PageInfo 是什么（重点，之前没讲透）

🟢 **必懂**

**PageInfo = PageHelper 给你的"分页信息盒子"**，里面装着所有分页相关信息：

```java
PageInfo<Emp> info = new PageInfo<>(empList);
//            │              │
//            │              └─ 把查到的"本页10条"喂给它
//            └─ 它自动算出 total, 存进自己里面

// 盒子(info)里有什么:
info.getTotal();    // 30   ← 总数(插件数出来的)
info.getList();     // [10条] ← 本页数据
info.getPageNum();  // 1    ← 第几页(这次不用)
info.getPageSize(); // 10   ← 每页几条(这次不用)
info.getPages();    // 3    ← 共几页(这次不用)
```

**类比：快递站取件单**

```
你取快递: 快递站先数"你有几件"(COUNT) → 30
         再给你"今天的10件"(本页)     → [10条]
         都写在取件单(PageInfo)上
你只需要从单子上抄两项: 总件数(total)、今天的件(list)
```

**你只用它两样**：`getTotal()`（总数）+ `getList()`（本页数据）。

---

## 五、为什么还要"绕一圈"转到 PageBean？

🟢 **必懂**

你可能会想：直接返回 PageInfo 不就行了？**不行，三个原因**：

| 原因 | 说明 |
|------|------|
| ① PageInfo 是第三方的 | 前端不该依赖 PageHelper 的东西 |
| ② PageInfo 字段太多 | total/list/pageNum/pages... 前端只要 total+rows |
| ③ 接口文档规定了格式 | 文档写死 `{total, rows}`，你的 PageBean 正好是这个 |

**类比**：取件单(PageInfo)信息很多，但你只告诉客户"共30件、这次10件"——抄到自己的简洁单子(PageBean)再给客户。

**所以**：
```java
return new PageBean(info.getTotal(), info.getList());
//             │               │
//             │               └─ 本页10条
//             └─ 总数30
//   装进自己的 PageBean {total:30, rows:[10条]} 返回
```

---

## 六、完整代码（带详尽注释）

🟢 **照这个写，每行注释都看**

```java
package com.shyc.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shyc.mapper.EmpMapper;
import com.shyc.pojo.Emp;
import com.shyc.pojo.PageBean;
import com.shyc.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service                                  // ① 交给Spring管理(必须!)
public class EmpServiceImpl implements EmpService {

    @Autowired
    private EmpMapper empMapper;          // ② 注入Mapper(调用它的findAll)

    @Override
    public PageBean page(Integer page, Integer pageSize) {
        // ③ 登记分页:告诉插件"第page页,每页pageSize条"
        PageHelper.startPage(page, pageSize);

        // ④ 正常查(插件偷偷加 LIMIT,只返回本页数据)
        List<Emp> empList = empMapper.findAll();

        // ⑤ 包装:插件偷偷数了总数,塞进 PageInfo
        PageInfo<Emp> info = new PageInfo<>(empList);

        // ⑥ 取"总数+本页数据",装进自己的PageBean返回
        return new PageBean(info.getTotal(), info.getList());
    }
}
```

---

## 七、PageBean（分页结果的"壳"）

🟢 **必懂**

```java
package com.shyc.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor          // 无参构造: new PageBean()
@AllArgsConstructor         // 全参构造: new PageBean(total, rows)
public class PageBean {
    private Long total;      // 总记录数(30)
    private List<?> rows;    // 本页数据(10条)
}
```

**两个构造器注解**：
- `@NoArgsConstructor`：能 `new PageBean()` 空盒子，再慢慢 set
- `@AllArgsConstructor`：能 `new PageBean(total, rows)` 一步装好（Service 里用的这个）

**`List<?>`**：问号 = 任意类型。这个盒子以后装员工、装日志都行，不用每个都写新类。

---

## 八、完整流程串一遍（背这个）

```
前端: GET /emps?page=1&pageSize=10
  ↓
Controller 收到 page=1, pageSize=10
  ↓
调 EmpService.page(1, 10)
  ↓
PageHelper.startPage(1,10)      ← 登记
  ↓
empMapper.findAll()             ← 实际执行: select ... LIMIT 0,10 + select count(*)
  ↓
PageInfo: total=30, list=[10条]
  ↓
new PageBean(30, [10条])
  ↓
返回: {code:1, data:{total:30, rows:[10条]}}
```

---

## 九、踩坑记录

| 坑 | 说明 |
|----|------|
| 忘加 `@Service` | Spring 不认识类,Controller 注入报错 |
| PageHelper 黄色警告 | "try-with-resources"提示是误报,忽略 |
| 忘 import PageInfo/PageHelper | 编译报"找不到符号" |
| startPage 后必须紧跟查询 | 插件拦截"下一条SQL",中间不能插别的查询 |

---

## 十、总结（背这 5 句）

1. **分页 = 查"第几页几条" + 返回"总数和本页"**
2. **PageHelper 三步曲**：startPage → 正常查 → new PageInfo
3. **PageInfo 是"分页信息盒子"**：getTotal() 总数, getList() 本页
4. **转到自己的 PageBean**：接口文档要 {total, rows}，不直接返回第三方对象
5. **写不出来的解法**：背 4 行骨架,默写 3 次,自然就会

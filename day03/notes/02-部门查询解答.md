# Tlias 部门查询 — 三层完整参考答案

## 1. DeptMapper（持久层）✅ 你已写完

```java
package com.shyc.mapper;

import com.shyc.pojo.Dept;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface DeptMapper {
    @Select("select * from dept")
    List<Dept> findAll();
}
```

## 2. DeptService（业务层接口）

```java
package com.shyc.service;

import com.shyc.pojo.Dept;
import java.util.List;

public interface DeptService {
    List<Dept> findAll();
}
```

## 3. DeptServiceImpl（业务层实现类）

```java
package com.shyc.service.impl;

import com.shyc.mapper.DeptMapper;
import com.shyc.pojo.Dept;
import com.shyc.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

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

## 4. DeptController（控制层）

```java
package com.shyc.controller;

import com.shyc.pojo.Dept;
import com.shyc.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

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

## 调用链回顾

```
GET /api/depts
    ↓
DeptController.findAll()        ← 接收请求
    ↓
DeptServiceImpl.findAll()       ← 调 Mapper
    ↓
DeptMapper.findAll()            ← 执行 SQL
    ↓
MySQL: select * from dept
    ↓
结果原路返回 → Controller → 浏览器 JSON
```

## 启动验证

1. 启动 `TliasApplication`
2. 浏览器访问 `http://localhost:8080/api/depts`
3. 应返回 6 条部门的 JSON 数据

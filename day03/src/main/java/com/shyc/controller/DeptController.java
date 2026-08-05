package com.shyc.controller;

import com.shyc.pojo.Dept;
import com.shyc.pojo.Result;
import com.shyc.service.DeptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author shiyc
 * @date 2026/7/31 17:05
 */
@Slf4j
@RestController
@RequestMapping("/depts")
public class DeptController {
    @Autowired
    private DeptService deptService;

    @GetMapping
    public Result selectAll(){
        log.info("查询所有部门信息");
        List<Dept> deptList = deptService.selectAll();
        return Result.success(deptList);
    }

    @GetMapping("/{id}")
    public Result selectById(@PathVariable Integer id){
        log.info("查询部门id为{}的部门信息",id);
        Dept dept = deptService.selectById(id);
        return Result.success(dept);
    }


    @PutMapping
    public Result updateDept(@RequestBody Dept dept){
        log.info("修改部门信息{}",dept);
        deptService.updateDept(dept);
        return Result.success();
    }
    @DeleteMapping
    public Result deleteById(Integer id){
        log.info("删除部门id为{}的部门信息",id);
        deptService.deleteById(id);
        return Result.success();
    }

    @PostMapping
    public Result insert( @RequestBody Dept dept){
        log.info("添加部门信息{}",dept);
        deptService.insert(dept);
        return Result.success();
    }

}

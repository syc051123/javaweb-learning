package com.shyc.controller;

import com.shyc.pojo.Emp;
import com.shyc.pojo.EmpQueryParam;
import com.shyc.pojo.PageBean;
import com.shyc.pojo.Result;
import com.shyc.service.EmpService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author shiyc
 * @date 2026/8/3 21:00
 */
@RequestMapping("/emps")
@RestController
public class EmpController {
    @Autowired
    private EmpService empService;

    @GetMapping
    public Result page( EmpQueryParam param){
        PageBean pageBean = empService.page( param);
        return Result.success(pageBean);
    }

    @PostMapping
    public Result insertEmp( @RequestBody Emp emp){
        empService.insertEmp( emp);
        return Result.success();
    }

    @DeleteMapping
    public Result deleteEmp(@RequestParam List<Integer> ids){
        empService.deleteEmp( ids);
        return Result.success();
    }

}

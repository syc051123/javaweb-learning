package com.shyc.controller;

import com.shyc.pojo.Emp;
import com.shyc.pojo.Result;
import com.shyc.service.EmpService;
import com.shyc.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author shiyc
 * @date 2026/8/7 0:51
 */
@RestController
public class LoginController {

    @Autowired
    private EmpService empService;

    @PostMapping("/login")
    public Result login(@RequestBody Emp emp) {
        Emp loginEmp = empService.login(emp);
        if (loginEmp != null) {
            String token = JwtUtils.generateToken(loginEmp.getId(), loginEmp.getUsername());
            return Result.success("登录成功", token);
        }
        return Result.error("用户名或密码错误");
    }

}

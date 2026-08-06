package com.shyc.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 员工实体类
 */
@Data
public class Emp {
    private Integer id;           // 主键
    private String username;        // 用户名
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ToString.Exclude
    private String password;      // 密码

    private String name;          // 姓名
    private Short gender;         // 性别 1男 2女
    private String phone;         // 手机号
    private Short job;            // 职位 1班主任 2讲师 3学工主管 4教研主管 5咨询师
    private Integer salary;       // 薪资
    private String image;         // 头像
    private LocalDate entryDate;  // 入职日期(只有日期,没有时间)
    private Integer deptId;       // 部门id
    private String deptName;      // 部门名称(JOIN查出来的,表里没有!)
    private LocalDateTime createTime;  // 创建时间
    private LocalDateTime updateTime;  // 修改时间
}

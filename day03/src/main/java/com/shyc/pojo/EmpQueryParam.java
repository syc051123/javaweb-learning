package com.shyc.pojo;

import lombok.Data;

import java.time.LocalDate;

/**
 * @author shiyc
 * @date 2026/8/3 23:05
 */
@Data
public class EmpQueryParam {

    private Integer page;
    private Integer pageSize;
    private String name;// 姓名,可空
    private Short gender;// 性别,可空
    private LocalDate begin;// 入职日期,可空
    private LocalDate end;// 入职日期,可空

}

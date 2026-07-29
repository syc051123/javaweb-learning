package com.shyc.pojo;

import lombok.Data;

/**
 * 学生实体类，对应 student 表
 */
@Data
public class Student {

    /**
     * 学生学号
     */
    private String studentId;

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别（男/女）
     */
    private String gender;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 手机号
     */
    private String phone;

}

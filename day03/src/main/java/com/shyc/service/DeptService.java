package com.shyc.service;

import com.shyc.pojo.Dept;

import java.util.List;

/**
 * @author shiyc
 * @date 2026/7/31 16:49
 */
public interface DeptService {
    List<Dept> selectAll();

    Dept selectById(Integer id);

    void updateDept(Dept dept);

    void deleteById(Integer id);

    void insert(Dept dept);
}

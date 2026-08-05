package com.shyc.service.impl;

import com.shyc.mapper.DeptMapper;
import com.shyc.pojo.Dept;
import com.shyc.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author shiyc
 * @date 2026/7/31 16:50
 */
@Service
public class DeptServiceImpl implements DeptService {

    @Autowired
    private DeptMapper deptMapper;

    @Override
    public List<Dept> selectAll(){
        return deptMapper.selectAll();
    }

    @Override
    public Dept selectById(Integer id){
        return deptMapper.selectById(id);
    }


    @Override
    public void updateDept(Dept dept){
        dept.setUpdateTime(LocalDateTime.now());
        deptMapper.updateDept(dept);
    }

    @Override
    public void deleteById(Integer id){
        deptMapper.deleteById(id);
    }

    @Override
    public void insert(Dept dept){
        dept.setCreateTime(LocalDateTime.now());
        dept.setUpdateTime(LocalDateTime.now());
        deptMapper.insertDept(dept);
    }

}

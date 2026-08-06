package com.shyc.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shyc.mapper.EmpMapper;
import com.shyc.pojo.Emp;
import com.shyc.pojo.EmpQueryParam;
import com.shyc.pojo.PageBean;
import com.shyc.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author shiyc
 * @date 2026/8/3 20:38
 */
@Service
public class EmpServiceImpl implements EmpService {
    @Autowired
    private EmpMapper empMapper;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Override
    public PageBean page(EmpQueryParam param) {
        PageHelper.startPage(param.getPage(),param.getPageSize());
        List<Emp> empList =  empMapper.selectAll(param);
        PageInfo<Emp> pageInfo = new PageInfo<>(empList);
        return new PageBean(pageInfo.getTotal(),pageInfo.getList());
    }

    @Override
    public Emp login(Emp emp) {
        Emp empDB = empMapper.selectByUsername(emp.getUsername());

        if(empDB==null){
            return null;
        }
        boolean check=passwordEncoder.matches(emp.getPassword(),empDB.getPassword());
      return check?empDB:null;
    }

    @Override
    public void insertEmp(Emp emp) {
        emp.setCreateTime(LocalDateTime.now());
        emp.setUpdateTime(LocalDateTime.now());
        empMapper.insertEmp(emp);
    }

    @Override
    public void deleteEmp(List<Integer> ids) {
        empMapper.deleteByIds(ids);
    }


    @Override
    public void updateEmp(Emp emp) {
        emp.setUpdateTime(LocalDateTime.now());
        empMapper.updateEmp(emp);
    }

    @Override
    public Emp selectById(Integer id) {
        return empMapper.selectById(id);
    }



}

package com.shyc.service;

import com.shyc.pojo.Emp;
import com.shyc.pojo.EmpQueryParam;
import com.shyc.pojo.PageBean;

import java.util.List;

/**
 * @author shiyc
 * @date 2026/8/3 20:35
 */

public interface EmpService {
    //新增员工
    void insertEmp(Emp emp);

    //删除员工
    void deleteEmp(List<Integer> ids);


//分页查询员工
    PageBean page( EmpQueryParam param);
}

package com.shyc.mapper;

import com.shyc.pojo.Dept;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author shiyc
 * @date 2026/7/31 0:57
 */
@Mapper
public interface DeptMapper {
    @Select("select * from dept")
    List<Dept> selectAll();


    @Select("select *from dept where id=#{id}")
    Dept selectById(Integer id);



    @Update("UPDATE  dept  SET  name=#{name},update_time=#{updateTime} WHERE id=#{id} ")
    void updateDept(Dept dept);

    @Delete("DELETE from dept where id=#{id}")
    void deleteById(Integer id);


  @Insert("INSERT INTO dept ( name, create_time, update_time) " +
          "VALUES (#{name},#{createTime},#{updateTime})")
    void insertDept(Dept dept);
}

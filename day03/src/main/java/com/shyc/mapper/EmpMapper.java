package com.shyc.mapper;

import com.shyc.pojo.Emp;
import com.shyc.pojo.EmpQueryParam;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author shiyc
 * @date 2026/8/3 19:05
 */
@Mapper
public interface EmpMapper {

    @Insert("INSERT INTO emp ( username, password, name, gender, phone, job, salary, image, entry_date, dept_id ) " +
            "values(#{username}, #{password}, #{name}, #{gender}, #{phone}, #{job}, #{salary}, #{image}, #{entryDate}, #{deptId})")
    void insertEmp(Emp emp);

    @Delete("""
                <script>
                    delete  from emp where  id in
                    <foreach collection="ids" item="id" open="(" separator="," close=")">
                    #{id}
                    </foreach>
                </script>
            """)
    void deleteByIds(List<Integer> ids);

    @Select("select *from emp where id =#{id}")
    Emp selectById(Integer id);

    @Update("""
                <script>
                update emp set
                               name=#{name},
                               gender=#{gender},
                               phone=#{phone},
                               job=#{job},
                               salary=#{salary},
                               image=#{image},
                               entry_date=#{entryDate},
                               update_time=#{updateTime},
                               dept_id=#{deptId}
                           where id=#{id}
                </script>
            """)

    void updateEmp(Emp emp);

    @Select("""
                 <script>
                  select e.*,d.name dept_name
                  from emp e left join dept d
                  on d.id = e.dept_id
                  <where>
                   <if test="name!=null and name !=''">
                       and e.name like concat('%',#{name},'%')
                   </if>

                    <if test="gender!=null">
                       and e.gender=#{gender}
                    </if>

                    <if test="begin!=null">
                       and e.entry_date &gt;= #{begin}
                    </if>

                    <if test="end !=null">
                       and e.entry_date &lt;= #{end}
                    </if>

                </where>
            </script>
            """)
    List<Emp> selectAll(EmpQueryParam param);

}

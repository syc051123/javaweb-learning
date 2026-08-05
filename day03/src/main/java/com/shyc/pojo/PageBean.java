package com.shyc.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author shiyc
 * @date 2026/8/3 20:32
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageBean {
    private Long total;
    private List<?> rows;

}

package com.zzh.coursehub.entity.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResult<T> implements Serializable {
    //当前页数据
    private List<T> records;
    //总条数
    private Long total;
    //每页条数
    private Integer pageSize;
    //当前页码
    private Integer pageNum;
    //总页数
    private Integer pages;

    public static <T> PageResult<T> build(List<T> records,Long total,Integer pageSize,Integer pageNum) {
        PageResult<T> result = new PageResult<>();
        result.setRecords(records);
        result.setTotal(total);
        result.setPageSize(pageSize);
        result.setPageNum(pageNum);
        return result;
    }
}

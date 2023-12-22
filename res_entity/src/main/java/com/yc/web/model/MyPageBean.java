package com.yc.web.model;

import com.yc.bean.Resfood;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyPageBean<T>{
    private long pageno;//当前页数
    private long pagesize;//每页多少
    private String sortby="fid";
    private String sort="desc";

    private long total;//总记录数
    private long totalpages;//总页数

    private int code;
    private List<Resfood> dataset;

}

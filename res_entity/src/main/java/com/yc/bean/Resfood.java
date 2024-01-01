package com.yc.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor //生成无参构造方法
@AllArgsConstructor//生成所有参数的构造方法
public class Resfood implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer fid;
    private String fname;
    private Double normprice;
    private Double realprice;
    private String detail;
    private String fphoto;
    private String status;

    @TableField(exist = false)
    private Long detail_count;//浏览数量,这个数据是Redis而不是数据库的
}

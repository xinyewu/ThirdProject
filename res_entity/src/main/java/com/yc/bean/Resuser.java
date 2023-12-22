package com.yc.bean;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Resuser implements Serializable {
    @TableId(type = IdType.AUTO)//表的主键  PO:与数据库绑定
    private Integer userid ;
    private String username;
    private String pwd ;
    private String email;
    //为了与页面传过来的属性yzm对应，在这里增加一个验证码
    @TableField(exist = false)
    private String yzm; //同时又是一个vo
}

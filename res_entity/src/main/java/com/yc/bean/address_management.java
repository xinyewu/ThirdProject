package com.yc.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class address_management implements Serializable {
    private Integer usid;
    @TableId(type = IdType.AUTO)

    private Integer adid;
    private String isdefault;
    private String address;
    private String detailed_address;
    private String tel;
    private String landline_telephone;
    private String email;

}

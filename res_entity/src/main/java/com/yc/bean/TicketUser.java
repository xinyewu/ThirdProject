package com.yc.bean;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("resticket_user")
public class TicketUser implements Serializable {
    @TableId
    Integer no;
    Integer uno;
    Integer tno;
    Integer fullMoney;
    Double money;
    String type;
    Integer state;
    String gettime;
    String overtime;
}

package com.yc.bean;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ticket implements Serializable {
    Integer tno;
    Integer fullmoney;
    Double money; // 总金额
    Integer ticketCount; // 券数量
    String type; // 券的类型
    String starttime;
    String endtime;
    Double remainmoney; // 剩余金额
    Integer remainticket; // 剩余券数量
    Integer state;
}

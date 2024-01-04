package com.yc.ticket.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketType {
    public  String FullDiscountCoupon = "满减券";
    public String NoThresholdCoupons = "无门槛券";
}

package com.yc.bean;

import lombok.Data;

import java.io.Serializable;

@Data
public class MessageObj implements Serializable {
    String fromUser;
    String toUser;
    String content;
    Integer readed;
    String date;
}

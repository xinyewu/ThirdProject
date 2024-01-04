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
@TableName("reschat")
public class Reschat implements Serializable {
    @TableId
    Integer id;
    String content;
    String fromuser;
    String touser;
    String type;
    String time;
    Integer readed;

}

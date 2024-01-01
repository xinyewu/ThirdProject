package com.yc.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yc.bean.Resorder;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface ResorderMapper extends BaseMapper<Resorder> {
    @Select("select SUBSTR(deliverytime,6,2)name,dealprice*num value\n" +
            "from resorder,resorderitem where resorder.roid=resorderitem.roid and SUBSTR(deliverytime,1,4)=#{year}  and resorderitem.fid=#{roid} ")
    public List<Map<String ,Object>>findOrder(Integer roid, String year);

    @Select("select distinct SUBSTR(deliverytime,1,4)years from resorder")
    public List<String> findMonths();

    @Select("select  SUBSTR(deliverytime,6,2)name,sum(dealprice*num)value from resorder,resorderitem \n" +
            "where resorder.roid=resorderitem.roid and  SUBSTR(deliverytime,1,4)=#{year} GROUP BY name")
    public List<Map<String ,Object>>findMoney(String year);

}

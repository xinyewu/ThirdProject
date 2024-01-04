package com.yc.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yc.bean.Resorder;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface ResorderMapper extends BaseMapper<Resorder> {
    @Select("select SUBSTR(deliverytime,6,2)name,num value\n" +
            "from resorder,resorderitem where resorder.roid=resorderitem.roid and SUBSTR(deliverytime,1,4)=#{year}  and resorderitem.fid=#{fid} ")
    public List<Map<String ,Object>>findOrder(Integer fid, String year);

    @Select("select distinct SUBSTR(deliverytime,1,4)years from resorder")
    public List<String> findMonths();

    @Select("select  SUBSTR(deliverytime,6,2)name,sum(dealprice*num)value from resorder,resorderitem \n" +
            "where resorder.roid=resorderitem.roid and  SUBSTR(deliverytime,1,4)=#{year} GROUP BY name")
    public List<Map<String ,Object>>findMoney(String year);

    @Select("select resfood.fid,resorder.username,resorder.roid,resorder.ordertime,resfood.fname,resfood.fphoto,resorderitem.num,resorder.address,resorder.tel,resorderitem.dealprice from" +
            " resfood,resorder,resorderitem where resorder.roid = resorderitem.roid and resorderitem.fid = resfood.fid and  DATEDIFF(NOW(), resorder.ordertime) <= #{the_time} LIMIT #{Pageno},#{PageSize}")
    public List<Map<String,Object>>findOldAll(@Param("Pageno")Integer Pageno, @Param("PageSize")Integer PageSize, @Param("the_time")String the_time, @Param("userid")Integer userid);

    @Select("select resorder.username,resorder.roid,resorder.ordertime,resfood.fname,resfood.fphoto,resorderitem.num,resorder.address,resorder.tel,resorderitem.dealprice from" +
            " resfood,resorder,resorderitem where resorder.roid = resorderitem.roid and resorderitem.fid = resfood.fid and  DATEDIFF(NOW(), resorder.ordertime) <= #{the_time} ")
    public List<Map<String,Object>>findOldAll1(@Param("the_time")String the_time,@Param("userid")Integer userid);

}

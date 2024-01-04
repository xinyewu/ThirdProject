package com.yc.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yc.bean.Resorderitem;
import com.yc.web.model.OrderItems;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface ResorderitemMapper extends BaseMapper<Resorderitem> {
    @Select("select fname,realprice,sc.num from (select fid,dealprice,num from resorderitem where roid=#{roid})sc,resfood\n" +
            " where sc.fid=resfood.fid")
    public List<OrderItems> findItemByRoid(Integer roid);

    @Delete("delete from resorderitem where roid = #{roid}")
    public Integer deleteByRoiid(@Param("roid") Integer roid);

}

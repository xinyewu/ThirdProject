package com.yc.biz;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface address_manageBizImpl {
    public List<Map<String, Object>> findById(@Param("usid") Integer usid, @Param("pageno_l") Integer pageno_l, @Param("pagesize_l") Integer pagesize_l);

    public Integer updata_default(@Param("adid") Integer adid, @Param("usid") Integer usid);

    public Integer add_local(@Param("usid") Integer usid, @Param("address") String address, @Param("detailed_address") String detailed_address, @Param("tel") String tel, @Param("landline_telephone") String landline_telephone, @Param("email") String email, @Param("alias") String alias, @Param("username") String username);

    public List<Map<String, Object>> findByIdNot(@Param("usid") Integer usid);

    Integer update_local(@Param("username") String username, @Param("address") String address, @Param("detailed_address") String detailed_address, @Param("tel") String tel, @Param("landline_telephone") String landline_telephone, @Param("email") String email, @Param("alias") String alias, @Param("adid") Integer adid);

    Integer delete_local(@Param("adid") Integer adid);

    List<Map<String, Object>> findByIdDe(@Param("usid") Integer usid);

}

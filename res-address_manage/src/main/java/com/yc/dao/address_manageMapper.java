package com.yc.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yc.bean.address_management;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

public interface address_manageMapper extends BaseMapper<address_management> {
    @Select("select address_management.alias,address_management.isdefault,address_management.username,address_management.* from resuser , address_management where resuser.userid = address_management.usid and address_management.usid = #{usid} LIMIT #{pageno_l},#{pagesize_l}")
    List<Map<String,Object>> findById(@Param("usid")Integer usid,@Param("pageno_l")Integer pageno_l,@Param("pagesize_l")Integer pagesize_l);
    @Select("select address_management.alias,address_management.isdefault,address_management.username,address_management.* from resuser , address_management where resuser.userid = address_management.usid and address_management.usid = #{usid} ")
    List<Map<String,Object>> findByIdNot(@Param("usid")Integer usid);
    @Update("UPDATE address_management SET isdefault = 0 WHERE usid = #{usid};")
    Integer updata_default(@Param("usid")Integer usid);
    @Update("UPDATE address_management SET isdefault = 1 WHERE adid = #{adid};")
    Integer updata_defalut_two(@Param("adid")Integer adid);
    @Insert("insert into address_management (usid,address,detailed_address,tel,landline_telephone,email,isdefault,alias,username) values (#{usid},#{address},#{detailed_address},#{tel},#{landline_telephone},#{email},0,#{alias},#{username})")
    Integer add_local(@Param("usid")Integer usid,@Param("address")String address,@Param("detailed_address")String detailed_address,@Param("tel")String tel,@Param("landline_telephone")String landline_telephone,@Param("email")String email,@Param("alias")String alias,@Param("username")String username);
    @Update("UPDATE address_management set username =#{username},address=#{address},detailed_address=#{detailed_address},tel=#{tel},landline_telephone=#{landline_telephone},email=#{email},alias=#{alias} WHERE adid=#{adid}")
    Integer update_local(@Param("username")String username,@Param("address")String address,@Param("detailed_address") String detailed_address,@Param("tel")String tel,@Param("landline_telephone")String landline_telephone,@Param("email")String email,@Param("alias")String alias,@Param("adid")Integer adid);

    @Select("select address_management.alias,address_management.isdefault,address_management.username,address_management.* from resuser , address_management where resuser.userid = address_management.usid and address_management.usid = #{usid} and address_management.isdefault = 1")
    List<Map<String,Object>> findByIdDe(@Param("usid")Integer usid);
    /* this.adid = adid
                this.recipient1 = username
                this.show_location_cas1 = address
                this.the_address1 =detailed_address
                this.the_phone1 = tel
                this.landline_telephone1 = landline_telephone
                this.email_address1 = email
                this.address_alias1 = alias*/
}

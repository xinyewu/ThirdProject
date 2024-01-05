package com.yc.web.controller;

import com.alibaba.nacos.api.naming.pojo.healthcheck.impl.Http;
import com.yc.bean.Resuser;
import com.yc.biz.address_manageBizImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("resAddressManage")
public class address_manageController {
    @Autowired
    private address_manageBizImpl addressManageBiz;

    @RequestMapping("findById")
    private Map<String, Object> findById(@RequestParam Integer usid, @RequestParam Integer pageno_l, @RequestParam Integer pagesize_l) {
        Map<String, Object> map = new HashMap<>();
        if (usid == null) {
            map.put("code", -2);
            map.put("msg", "非登录用户不能下单");
            return map;
        }
        Integer pageno = pageno_l * pagesize_l - pagesize_l;
        List<Map<String, Object>> list1 = new ArrayList<>();
        list1 = addressManageBiz.findByIdNot(usid);
        List<Map<String, Object>> list = new ArrayList<>();
        if (!addressManageBiz.findById(usid, pageno, pagesize_l).isEmpty()) {
            list = addressManageBiz.findById(usid, pageno, pagesize_l);
            map.put("total_l", list1.size());
            map.put("data", list);
            map.put("code", 1);
        } else {
            map.put("code", 0);
        }
        return map;
    }

    @RequestMapping("update_default")
    public Integer update_default(@RequestParam Integer usid, @RequestParam Integer adid) {
        if (addressManageBiz.updata_default(adid, usid) != 0) {
            return 1;
        } else return 0;
    }

    //    @Param("usid")Integer usid,@Param("address")String address,@Param("detailed_address")String detailed_address,@Param("tel")String tel,@Param("landline_telephone")String landline_telephone,@Param("email")String email,@Param("alias")String alias,@Param("username")String username
    @RequestMapping("add_local")
    public Integer add_local(@RequestParam Integer usid, @RequestParam String address, @RequestParam String detailed_address, @RequestParam String tel, @RequestParam String landline_telephone, @RequestParam String email, @RequestParam String alias, @RequestParam String username) {
        if (addressManageBiz.add_local(usid, address, detailed_address, tel, landline_telephone, email, alias, username) != 0) {
            return 1;
        } else return 0;
    }

    //        Integer update_local(@Param("username")String username,@Param("address")String address,@Param("detailed_address") String detailed_address,@Param("tel")String tel,@Param("landline_telephone")String landline_telephone,@Param("email")String email,@Param("alias")String alias,@Param("adid")Integer adid);
    @RequestMapping("update_local")
    public Integer update_local(@RequestParam String username, @RequestParam String address, @RequestParam String detailed_address, @RequestParam String tel, @RequestParam String landline_telephone, @RequestParam String email, @RequestParam String alias, @RequestParam Integer adid) {
        if (addressManageBiz.update_local(username, address, detailed_address, tel, landline_telephone, email, alias, adid) != 0) {
            return 1;
        } else {
            return 0;
        }
    }

    @RequestMapping("detele_local")
    public Integer delete_local(@RequestParam Integer adid) {
        if (addressManageBiz.delete_local(adid) != 0) {
            return 1;
        } else {
            return 0;
        }
    }

    @RequestMapping("findByusidDe")
    public Map<String, Object> findByusiddDe(@RequestParam Integer usid) {
        Map<String, Object> map = new HashMap<>();
        if (!addressManageBiz.findByIdDe(usid).isEmpty()) {
            map.put("code", 1);
            map.put("data", addressManageBiz.findByIdDe(usid));
            return map;
        } else {
            map.put("code", 0);
            return map;
        }
    }
}

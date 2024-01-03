package com.yc.web.controller;

import com.yc.bean.Resadmin;
import com.yc.bean.SystemHardwareInfo;
import com.yc.biz.ResadminBiz;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("resadmin")
@Slf4j

public class ResadminController {
    @Autowired
    private ResadminBiz resadminBiz;

    @RequestMapping(value = "login", method = {RequestMethod.GET, RequestMethod.POST})
    public Map<String, Object> login(String raname, String rapwd, HttpSession session) {
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isEmpty(raname) || StringUtils.isEmpty(rapwd)) {
            map.put("code", -2);
            map.put("msg", "用户名或密码为空");
            return map;
        }
        //处理密码:用md5加密
        String md5Pass = DigestUtils.md5DigestAsHex(rapwd.getBytes());
        //访问业务层，login
        Resadmin ru = resadminBiz.login(raname, md5Pass);
        if (ru == null) {
            //失败，则code=0
            map.put("code", -3);
            map.put("msg", "用户名或密码错误");
            return map;
        }
        //成功，则code=1
        map.put("code", 1);
        //在session中维持用户登录状态
        session.setAttribute("resadmin", ru);
        //回送一个数据给客户端
        ru.setRapwd("");
        map.put("obj", ru);
        return map;
    }

    @RequestMapping(value = "sys", method = {RequestMethod.GET, RequestMethod.POST})
    public Map<String, Object> sys() throws Exception {
        Map<String, Object> map = new HashMap<>();
        SystemHardwareInfo s = new SystemHardwareInfo();
        s.copyTo();
        map.put("data", s);
        return map;
    }
}

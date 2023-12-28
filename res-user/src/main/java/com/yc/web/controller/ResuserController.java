package com.yc.web.controller;


import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.yc.bean.Resuser;
import com.yc.biz.ResuserBizImpl;
import com.yc.web.model.MyPageBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

//静态导入：导入静态常量和静态属性
@RestController
@RequestMapping("resuser")
@Slf4j

public class ResuserController {
    @Autowired
    private ResuserBizImpl resuserBiz;

    @RequestMapping(value = "logout", method = {RequestMethod.GET, RequestMethod.POST})
    public Map<String, Object> logout(HttpSession session) {
        session.removeAttribute("resuser");
        session.invalidate();
        Map<String, Object> map = new HashMap<>();
        map.put("code",1);
        return map;
    }

    @RequestMapping(value = "isLogin", method = {RequestMethod.GET, RequestMethod.POST})
    public Map<String, Object> isLogin(HttpSession session) {
        Map<String, Object> map = new HashMap<>();
        if (session.getAttribute("resuser")==null){
            map.put("code",0);
        }else {
            map.put("code",1);
            Resuser resuser= (Resuser) session.getAttribute("resuser");
            map.put("obj", resuser);
        }
        return map;
    }

    @RequestMapping(value = "login", method = {RequestMethod.GET, RequestMethod.POST})
    public Map<String, Object> login(Resuser user, HttpSession session) {
        Map<String, Object> map = new HashMap<>();
        String code = (String) session.getAttribute("code");
        if (!user.getYzm().equals(code)) {
            map.put("code", -1);
            map.put("msg", "验证码错误");
            return map;
        }
        if (StringUtils.isEmpty(user.getUsername()) || StringUtils.isEmpty(user.getPwd())) {
            map.put("code", -2);
            map.put("msg", "用户名或密码为空");
            return map;
        }
        //处理密码:用md5加密
        String md5Pass = DigestUtils.md5DigestAsHex(user.getPwd().getBytes());
        //访问业务层，login
        Resuser ru = resuserBiz.findByName(user.getUsername(), md5Pass);
        if (ru == null) {
            //失败，则code=0
            map.put("code", -3);
            map.put("msg", "用户名或密码错误");
            return map;
        }
        //成功，则code=1
        map.put("code", 1);
        //在session中维持用户登录状态
        session.setAttribute("resuser", ru);
        //回送一个数据给客户端
        ru.setPwd("");
        map.put("obj", ru);
        return map;
    }

    @RequestMapping(value = "login1", method = {RequestMethod.GET, RequestMethod.POST})
    public Map<String, Object> login1(Resuser user, HttpSession session) {
        Map<String,Object>map=new HashMap<>();
        if (resuserBiz.findByName("c", "c")!=null){
            map.put("code",1);
        }else {
            map.put("code",0);
        }
        return map;
    }
    @RequestMapping(value = "findAll", method = {RequestMethod.GET, RequestMethod.POST})
    public Map<String, Object> findAll() {
        Map<String,Object>map=new HashMap<>();
        map.put("data", resuserBiz.findAll());
        map.put("code", 1);
        return map;
    }

    @RequestMapping(value = "upStatus", method = {RequestMethod.GET, RequestMethod.POST})
    public Map<String, Object> upStatus(@RequestParam Integer userid,@RequestParam String username,@RequestParam String pwd) {
        Map<String,Object>map=new HashMap<>();
        int i= resuserBiz.upStatus(userid,username,pwd);
        if (i<=0){
            map.put("code", 0);
            map.put("msg","拉黑失败");
        }else {
            map.put("code", 1);
            map.put("msg","拉黑成功");
        }
        return map;
    }

}

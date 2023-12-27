package com.yc.web.controller;

import com.yc.biz.ResOrderItemBiz;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("resorder/resorderitem")
@Slf4j
public class ResorderItemController {
    @Autowired
    private ResOrderItemBiz resOrderItemBiz;

    @RequestMapping(value = "findByRoid", method = {RequestMethod.GET, RequestMethod.POST})
    public Map<String, Object> findByRoid(@RequestParam Integer roid) {
        Map<String, Object> map = new HashMap<>();
        map.put("data", resOrderItemBiz.findByRoid(roid));
        map.put("code", 1);
        return map;
    }

    @RequestMapping(value = "findItemByRoid", method = {RequestMethod.GET, RequestMethod.POST})
    public Map<String, Object> findItemByRoid(@RequestParam Integer roid) {
        Map<String, Object> map = new HashMap<>();
        map.put("data", resOrderItemBiz.findItemByRoid(roid));
        map.put("code", 1);
        return map;
    }
}

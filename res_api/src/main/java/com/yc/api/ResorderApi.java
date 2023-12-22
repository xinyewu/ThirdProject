package com.yc.api;

import com.yc.api.configuration.ResorderApiConfiguration;
import com.yc.bean.Resorder;
import com.yc.bean.Resuser;
import com.yc.web.model.CartItem;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;
import java.util.Set;

@FeignClient(value = "resorder",configuration = ResorderApiConfiguration.class)
public interface ResorderApi {
    @RequestMapping(value = "resorder/addCart", method = {RequestMethod.GET})
    public Map<String, Object> addCart(@RequestParam Integer fid, @RequestParam Integer num);

    @RequestMapping(value = "resorder/order", method = {RequestMethod.GET})
    public int order(@RequestParam Resorder resorder,@RequestParam Set<CartItem> cartItemSet,@RequestParam Resuser resuser);
}

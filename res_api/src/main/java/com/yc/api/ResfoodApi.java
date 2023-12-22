package com.yc.api;


import com.yc.api.configuration.ResfoodApiConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
@FeignClient(name = "resfood",configuration = ResfoodApiConfiguration.class)//服务名：http://
public interface ResfoodApi {
    @RequestMapping(value = "resfood/detailCountAdd",method = {RequestMethod.GET})
    public Map<String, Object> detailCountAdd(Integer fid);

    @RequestMapping(value = "resfood/findById",method = {RequestMethod.GET})//{fid}路径参数 ,headers = {"Origin=resorder"} openfeign
    public Map<String, Object> findById(@RequestParam Integer fid);

    @GetMapping(value = "resfood/findAll")
    public Map<String, Object> findAll();

    @RequestMapping(value = "resfood/findPage", method = {RequestMethod.GET})
    public Map<String, Object> findByPage(@RequestParam("pageno") int pageno, @RequestParam("pagesize") int pagesize, @RequestParam(required = false) String sortby, @RequestParam(required = false) String sort);
}

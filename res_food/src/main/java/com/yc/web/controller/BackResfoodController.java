package com.yc.web.controller;

import com.yc.bean.Resfood;
import com.yc.biz.FastDFSBiz;
import com.yc.biz.ResfoodBiz;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/resfood/back/")//localhost:9000/resfood/+xxx
@Slf4j
public class BackResfoodController {

    @Autowired
    private FastDFSBiz fastDFSBiz;//fastDFS文件服务器 1.依赖， 2.bootstrap 配置
    @Autowired
    private ResfoodBiz resfoodBiz;//操作数据库

    @RequestMapping(value = "/addNewFood", method = {RequestMethod.POST})
    public Map<String, Object> addNewFood(String fname, Double normprice, Double realprice, String detail, MultipartFile fphoto) {
        Map<String, Object> map = new HashMap<>();
        Resfood resfood = new Resfood();
        try{
            //步骤一 ：将图片上传到fastDfs中，返回图片地址，拼接
            //http://storage服务器中的Nginx:8888+ group1/M00/00/00/rBIABWVLayaAbqu_AAOA1dwT4AY608.jpg
            //以配置文件形式在springboot配置   存到数据resfood表中fphoto
            //<img src="http://storage服务器中的Nginx:8888+ group1/M00/00/00/rBIABWVLayaAbqu_AAOA1dwT4AY608.jpg"/>
            String path = this.fastDFSBiz.uploadFile(fphoto);//group1/M00/00/00/rBIABWVLayaAbqu_AAOA1dwT4AY608.jpg
            //步骤二：操作数据
            resfood.setFname(fname);
            resfood.setNormprice(normprice);
            resfood.setRealprice(realprice);
            resfood.setDetail(detail);
            resfood.setFphoto(path);
            resfoodBiz.addResfood(resfood);
        }catch (Exception e){
            e.printStackTrace();
            map.put("code",0);
            map.put("msg",e.getMessage());
            return map;
        }
        map.put("code",1);
        map.put("obj",resfood);
        return map;
    }
}

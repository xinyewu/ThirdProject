package com.yc.config;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.RequestOriginParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

@Component
@Slf4j
public class HeaderOriginParser implements RequestOriginParser {
    @Override
    public String parseOrigin(HttpServletRequest request) {
        Enumeration enu=request.getHeaderNames();
        while (enu.hasMoreElements()){
            String h= (String) enu.nextElement();
            log.info("接到对header:"+h+",对应的值为："+request.getHeader(h.toString()));
        }
        //1.获取请求头
        //String origin= request.getHeader("Origin");
        String origin= request.getHeader("Rere");
        //2.非空判断
        if (origin==null||"".equals(origin)){
            origin="blank";
        }
        log.info("收到origin:"+origin);

        String token=request.getHeader("token");
        if (token==null||"".equals(token)){
            token="blank";
        }
        log.info("收到token:"+token);

        return origin;
    }
}

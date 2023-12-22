package com.yc.config;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException;
import com.alibaba.csp.sentinel.slots.system.SystemBlockException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

//sentinel异常BlockException及子类（各种流控异常） -》统一转换处理
@Component
public class MySentinelExceptionHandler implements BlockExceptionHandler {
    //j2ee容器中DI进来 HttpServletRequest request, HttpServletResponse response
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, BlockException ex) throws Exception {
        String msg = null;
        if (ex instanceof FlowException) {
            msg = "流控异常，访问频繁，请稍候再试";
        } else if (ex instanceof DegradeException) {
            msg = "系统降级异常，系统降级";
        } else if (ex instanceof ParamFlowException) {
            msg = "热点参数异常:" + ex.getMessage() + "," + ((ParamFlowException) ex).getResourceName() + "," + ex.getRule();
        }
//ParamFlowException异常需要额外的依赖包
//         <dependency>
//            <groupId>com.alibaba.csp</groupId>
//            <artifactId>sentinel-parameter-flow-control</artifactId>
//        </dependency>

//            } else if (ex instanceof ParamFlowException) {
//                msg = "热点参数限流";

        else if (ex instanceof SystemBlockException) {
            msg = "系统规则限流或降级";

        } else if (ex instanceof AuthorityException) {
            msg = "授权规则不通过";

        } else {
            msg = "未知限流降级";
        }
        // http状态码
        response.setStatus(200);
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-Type", "application/json;charset=utf-8");
        response.setContentType("application/json;charset=utf-8");
        Map map = new HashMap();
        map.put("code", 0);
        map.put("msg", msg);
        //利用ObjectMapper将Map对象转换为json字符串
        ObjectMapper om = new ObjectMapper();
        String json = om.writeValueAsString(map);
        PrintWriter writer = response.getWriter();
        writer.write(json);
        writer.flush();
    }

}

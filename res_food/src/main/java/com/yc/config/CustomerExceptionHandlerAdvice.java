package com.yc.config;

import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

//自定义springboot针对业务异常的统一处理的切面
@ControllerAdvice    //  Controller控制器,ioc,  Advice: aop中的增强
@Order(-100000)    //切面顺序
//   AOP技术
public class CustomerExceptionHandlerAdvice {
    @ExceptionHandler(RuntimeException.class)//dao -》service(事物回滚针对RuntimeException)-》controller ->到这来被拦截
    @ResponseBody
    public Map<String, Object> handleRuntimeException(RuntimeException exception) {
        Map<String, Object> map = new HashMap<>();
        map.put("code", 0);
        map.put("msg", "异常信息：" + exception.getMessage()+"，msg");
        return map;
    }
}
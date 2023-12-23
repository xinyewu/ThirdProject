package com.yc.web.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.sun.org.apache.xpath.internal.operations.Bool;
import com.yc.bean.Resfood;
import com.yc.biz.ResfoodBizImpl;
import com.yc.config.RedisKeys;
import com.yc.web.model.MyPageBean;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/resfood")//localhost:9000/resfood/+xxx
@Slf4j
@RefreshScope

public class ResfoodController {

    @Autowired
    private ResfoodBizImpl resfoodBiz;
    @Autowired
    private RedisTemplate redisTemplate;

    @Value("${res.pattern.dateFormat}")
    private String dateFormatStirng;//利用di机制从属性文件读取配置信息

    public Set<Thread> set = new HashSet<>();

    @GetMapping("/test")
    public Object test() throws InterruptedException {
        Thread thread = Thread.currentThread();
        set.add(thread);
        Thread.sleep(1000);
        log.info("线程数为:" + set.size() + ",当前线程编号为:" + thread.getId());
        return thread.toString();
    }//然后访问服务，查看输出信息

    @RequestMapping(value = "timeService", method = {RequestMethod.GET})

    public Map<String, Object> timeService(Integer fid) {
        Date d = new Date();
        DateFormat df = new SimpleDateFormat(dateFormatStirng);
        String dString = df.format(d);
        Map<String, Object> map = new HashMap<>();
        map.put("code", 1);
        map.put("obj", dString);
        return map;
    }

    @RequestMapping(value = "detailCountAdd", method = {RequestMethod.GET, RequestMethod.POST})
    // @ApiOperation(value = "查看详情次数增加")
    // @ApiImplicitParams({@ApiImplicitParam(name = "fid", value = "菜品号", required = true)})
    public Map<String, Object> detailCountAdd(Integer fid) {
        Map<String, Object> map = new HashMap<>();
        Long count = 1L;
        if (redisTemplate.hasKey(RedisKeys.RESFOOD_DETAIL_COUNT_FID_ + fid) == false) {
            redisTemplate.opsForValue().set(RedisKeys.RESFOOD_DETAIL_COUNT_FID_ + fid, 1);
        } else {
            count = redisTemplate.opsForValue().increment(RedisKeys.RESFOOD_DETAIL_COUNT_FID_ + fid, 1);
        }
        map.put("code", 1);
        map.put("obj", count);
        return map;
    }

    @RequestMapping(value = "findAll", method = {RequestMethod.GET, RequestMethod.POST})
    // @ApiOperation(value = "查询所有菜品")
    public Map<String, Object> findAll() {
        Map<String, Object> map = new HashMap<>();
        map.put("data", resfoodBiz.findAll());
        map.put("code", 1);
        return map;
    }

    private Map<String, Object> handleBlockForFindByID(Integer fid, BlockException exception) {
        exception.printStackTrace();//控制台中输出异常堆栈信息
        Map<String, Object> map = new HashMap<>();
        map.put("code", 0);
        String errinfo = "调用根据Id查询错误:fid:" + fid + ",exception:" + exception.getRule() + "," + exception.getMessage();
        map.put("msg:", errinfo);
        return map;
    }

    //@RequestMapping(value = "findById/{fid}",method = {RequestMethod.GET,RequestMethod.POST})//localhost:9000/resfood/findById/1
    @RequestMapping(value = "findById", method = {RequestMethod.GET, RequestMethod.POST})//{fid}路径参数
    //@SentinelResource(value = "hotkey-Id", blockHandler = "handleBlockForFindByID")  //流控资源名
    // @ApiOperation(value = "根据菜品编号查询操作")
    // @ApiImplicitParams({@ApiImplicitParam(name = "fid", value = "菜品号", required = true)})
    public Map<String, Object> findById(@RequestParam Integer fid) {
        Map<String, Object> map = new HashMap<>();
        map.put("data", resfoodBiz.findById(fid));
        map.put("code", 1);
        return map;
    }

    private Map<String, Object> handleBlock(int pageno, int pagesize, String sortby, String sort, BlockException exception) {
        exception.printStackTrace();//控制台中输出异常堆栈信息
        Map<String, Object> map = new HashMap<>();
        map.put("code", 0);
        String errinfo = "调用分页查询错误:pageno:" + pageno + ",pagesize:" + pagesize +
                ",sortby:" + sortby + ",sort:" + sort + ",exception:" + exception.getRule() + "," + exception.getMessage();
        map.put("msg:", errinfo);
        return map;
    }

    private Map<String, Object> exceptionFallback(int pageno, int pagesize, String sortby, String sort, Throwable ex) {
        Map<String, Object> map = new HashMap<>();
        map.put("code", 0);
        map.put("msg", "pageno:" + pageno + ",pagesize:" + pagesize +
                ",sortby:" + sortby + ",sort:" + sort + "出异常了,异常信息为：" + ex.getMessage());
        return map;
    }

    @RequestMapping(value = "findPage", method = {RequestMethod.GET, RequestMethod.POST})
    @SentinelResource(value = "hotkey-page")
    public Map<String, Object> findByPage(@RequestParam("pageno") int pageno, @RequestParam("pagesize") int pagesize, @RequestParam(required = false) String sortby, @RequestParam(required = false) String sort) {
//        Boolean f = true;
//        if (f) {
//            throw new RuntimeException("业务出异常了");
//        }
        Map<String, Object> map = new HashMap<>();
        MyPageBean page = null;
//        try {
            sort = "desc";
            sortby = "fid";
            page = this.resfoodBiz.findByPage(pageno, pagesize, sortby, sort);
//        } catch (Exception e) {
//            map.put("code", 0);
//            map.put("msg", e.getCause());
//            e.printStackTrace();
//            return map;
//        }
        map.put("code", 1);
        map.put("data", page);
        return map;
        //return resfoodBiz.findByPage(pageno, pagesize, sortby, sort);
    }

    @RequestMapping(value = "addNewFood", method = {RequestMethod.GET, RequestMethod.POST})
    public Map<String, Object> addResfood(@RequestParam Resfood food) {
        Map<String, Object> map = new HashMap<>();
        if (resfoodBiz.addResfood(food)>=1){
            map.put("code", 1);
            map.put("msg", "上传新菜成功");
        }
        return map;
    }
}

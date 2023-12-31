package com.yc.web.controller;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.yc.bean.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yc.api.ResfoodApi;
import com.yc.biz.GoodsBiz;
import com.yc.biz.ResOrderItemBiz;
import com.yc.biz.ResorderBiz;
import com.yc.web.model.CartItem;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.ognl.IntHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


@RestController
@RequestMapping("resorder")
@Slf4j
public class ResorderController {
    @Autowired
    private RestTemplate restTemplate;//spring的东西，不是cloud的东西
    @Autowired
    private ResfoodApi resfoodApi;
    @Autowired
    private ResorderBiz resorderBiz;
    @Autowired
    private ResOrderItemBiz resOrderItemBiz;
    @Autowired
    private GoodsBiz goodsBiz;

    //    public ResorderController() {
//        this.resfoodApi = Feign.builder()
//                .client(new OkHttpClient())
//                .decoder(new GsonDecoder())
//                .contract(new JAXRSContract())
//                .target(GitHub2_javax.class, "https://api.github.com");
//    }
    //链路测试
    @RequestMapping(value = "serviceA", method = {RequestMethod.GET, RequestMethod.POST})
    public Map<String, Object> serviceA() {
        Map<String, Object> map = new HashMap<>();
        goodsBiz.goodsInfo();
        map.put("code", 1);
        return map;
    }

    @RequestMapping(value = "serviceB", method = {RequestMethod.GET, RequestMethod.POST})
    public Map<String, Object> serviceB() {
        Map<String, Object> map = new HashMap<>();
        goodsBiz.goodsInfo();
        map.put("code", 1);
        return map;
    }

    @GetMapping("payAction")
    public Map<String, Object> payAction(Integer flag) throws InterruptedException {
        //Thread.sleep(1000);  慢调用
        Random r = new Random();//异常数
        int a = r.nextInt(5);
        if (a == 0 || a == 1) {
            throw new RuntimeException("异常了");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("code", 1);
        return map;
    }

    @RequestMapping(value = "getCartInfo", method = {RequestMethod.GET, RequestMethod.POST})
    public Map<String, Object> getCartInfo(HttpSession session) {
        Map<String, Object> map = new HashMap<>();
        if (session.getAttribute("cart") == null || ((Map<Integer, CartItem>) session.getAttribute("cart")).size() <= 0) {
            map.put("code", 0);
            return map;
        }
        Map<Integer, CartItem> cart = (Map<Integer, CartItem>) session.getAttribute("cart");
        map.put("code", 1);
        map.put("obj", cart.values());//返回的是map的值的set
        return map;
    }

    @RequestMapping(value = "clearAll", method = {RequestMethod.GET, RequestMethod.POST})
    public Map<String, Object> clearAll(HttpSession session) {
        Map<String, Object> map = new HashMap<>();
        session.removeAttribute("cart");
        map.put("code", 1);
        return map;
    }

    //@RolesAllowed(value = "r1")
    @RequestMapping(value = "addCart", method = {RequestMethod.GET, RequestMethod.POST})
    public Map<String, Object> addCart(@RequestParam Integer fid, @RequestParam Integer num, HttpSession session) {
        Map<String, Object> map = new HashMap<>();
        //方案一:直接使用服务ip：端口，访问固定的一个服务结点
        //Map<String,Object> result=this.restTemplate.getForObject("http://localhost:9000/resfood/findById/"+fid,Map.class);
        //方案二：利用@LoadBalanced
        //String url = "http://resfood/resfood/findById/" + fid;
        //Map<String, Object> result = this.restTemplate.getForObject(url, Map.class);
        //方案三：利用openfeign发送请求
        Map<String, Object> result = this.resfoodApi.findById(fid);
        log.info("发送请求后得到商品信息：" + result);
        if (result == null) {
            map.put("code", -1);
            map.put("msg", "查无此商品");
        }

        ObjectMapper objectMapper = new ObjectMapper();
        Resfood food = objectMapper.convertValue(result.get("data"), Resfood.class);

        //从session取出Cart(map)
        Map<Integer, CartItem> cart = new HashMap<Integer, CartItem>();
        if (session.getAttribute("cart") != null) {
            cart = (Map<Integer, CartItem>) session.getAttribute("cart");//引用对象
        } else {
            session.setAttribute("cart", cart);
        }
        CartItem ci;
        //  判断这个商品在map中是否有
        if (cart.containsKey(fid)) {
            //有，加数量，
            ci = cart.get(fid);
            ci.setNum(ci.getNum() + num);
            cart.put(fid, ci);
        } else {
            //没有，则创建一个CartItem,存到map中
            ci = new CartItem();
            ci.setNum(num);
            ci.setFood(food);
            cart.put(fid, ci);
        }
        //处理数量
        if (ci.getNum() <= 0) {
            cart.remove(fid);
        }
        session.setAttribute("cart", cart);//->spring httpSession redis->  监听器监听值的变化
        map.put("code", 1);
        map.put("obj", cart.values());
        return map;
    }

    @RequestMapping(value = "confirmOrder", method = {RequestMethod.GET, RequestMethod.POST})
    public Map<String, Object> confirmOrder(@RequestParam Double discount,@RequestParam String address,@RequestParam String username,@RequestParam String detailed_address,@RequestParam String tel,@RequestParam String deliverytime1,@RequestParam String ps, HttpSession session) {
        Resorder order = new Resorder();
        order.setAddress(address);
        order.setUsername(username);
        order.setDetailed_address(detailed_address);
        order.setPs(ps);
        order.setTel(tel);
        order.setDiscount(discount);
        Map<String, Object> map = new HashMap<>();
        if (session.getAttribute("cart") == null || ((Map<Integer, CartItem>) session.getAttribute("cart")).size() <= 0) {
            map.put("code", -1);
            map.put("msg", "暂无任何商品");
            return map;
        }
        Map<Integer, CartItem> cart = (Map<Integer, CartItem>) session.getAttribute("cart");
        if (session.getAttribute("resuser") == null) {
            map.put("code", -2);
            map.put("msg", "非登录用户不能下单");
            return map;
        }
        Resuser resuser = (Resuser) session.getAttribute("resuser");
        order.setUserid(resuser.getUserid());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        order.setOrdertime(formatter.format(now));
        if (order.getDeliverytime() == null || "".equals(order.getDeliverytime())) {

            LocalDateTime deliverytime = now.plusHours(1);
            order.setDeliverytime(formatter.format(deliverytime));
        }
        order.setStatus(0);
        try {
            resorderBiz.order(order, new HashSet(cart.values()), resuser);
        } catch (Exception e) {
            map.put("code", -3);
            map.put("msg", e.getMessage());
            e.printStackTrace();
            return map;
        }
        session.removeAttribute("cart");
        map.put("code", 1);
        return map;
    }

    @RequestMapping(value = "findAll", method = {RequestMethod.GET, RequestMethod.POST})
    public Map<String, Object> findAll() {
        Map<String, Object> map = new HashMap<>();
        map.put("data", resorderBiz.findAll());
        map.put("code", 1);
        return map;
    }

    @RequestMapping(value = "updateByRoid", method = {RequestMethod.GET, RequestMethod.POST})
    public Map<String, Object> deleteByRoid(@RequestParam Integer roid) {
        Map<String, Object> map = new HashMap<>();
        int i = resorderBiz.updateByRoid(roid);
        if (i <= 0) {
            map.put("code", 0);
            map.put("msg", "发货错误");
        } else {
            map.put("code", 1);
            map.put("msg", "成功发货");
        }
        return map;
    }

    @RequestMapping(value = "drawback", method = {RequestMethod.GET, RequestMethod.POST})
    public Map<String, Object> drawback(@RequestParam Integer roid) {
        Map<String, Object> map = new HashMap<>();
        int i = resorderBiz.drawback(roid);
        if (i <= 0) {
            map.put("code", 0);
            map.put("msg", "退款出错");
        } else {
            map.put("code", 1);
            map.put("msg", "成功退款");
        }
        return map;
    }

    @RequestMapping(value = "findByUid", method = {RequestMethod.GET, RequestMethod.POST})
    public Map<String, Object> findByUid(@RequestParam Integer userid,@RequestParam("pageno") int pageno, @RequestParam("pagesize") int pagesize, @RequestParam(required = false) String sortby, @RequestParam(required = false) String sort) {
        Map<String,Object>map=new HashMap<>();
        if (sort.equals("")||sort.isEmpty()&&sortby.equals("")||sortby.isEmpty()){
            sort = "desc";
            sortby = "fid";
        }
        map.put("data", resorderBiz.findByUid(userid,pageno, pagesize, sortby, sort));
        map.put("code", 1);
        return map;
    }

    @RequestMapping(value = "findOrder", method = {RequestMethod.GET, RequestMethod.POST})
    public List<Map<String ,Object>> findItemByFid(@RequestParam Integer fid,@RequestParam String year) {
        List<Map<String ,Object>> list = new ArrayList<>();
        list=resorderBiz.findOrder(fid,year);
        List<Map<String ,Object>> list1 = new ArrayList<>();
        Map<String ,Object>map=new HashMap<>();
        map.put("name","");
        map.put("type","line");
        Map<String ,Object>map3=new HashMap<>();
        map3.put("show",true);
        map3.put("position","top");
        map.put("label",map3);
        Map<String ,Object>map0=new HashMap<>();
        map.put("areaStyle",map0);
        Map<String ,Object>map1=new HashMap<>();
        map1.put("focus","series");
        map.put("emphasis",map1);
        int data[]={0,0,0,0,0,0,0,0,0,0,0,0};
        if (list.size()<=0){
            map.put("data",data);
            list1.add(map);
        }else{
            for (int i = 0; i < list.size(); i++) {
                String month= (String) list.get(i).get("name");
                for (int j = 1; j <= data.length; j++) {
                    if (month.equals("0"+j+"")||month.equals(j+"")){
                        data[j-1]= (int) list.get(i).get("value");
                    }
                }
            }
            map.put("data",data);
            list1.add(map);
        }
        return list1;
    }

    @RequestMapping(value = "findMonths", method = {RequestMethod.GET, RequestMethod.POST})
    public Map<String, Object> findMonths() {
        Map<String,Object>map=new HashMap<>();
        List<String> l= resorderBiz.findMonths();
        if (l.size()<=0){
            map.put("code", 0);
            map.put("msg","查询全部年份出错");
        }else {
            map.put("code", 1);
            map.put("data",l);
        }
        return map;
    }

    @RequestMapping(value = "findMoney", method = {RequestMethod.GET, RequestMethod.POST})
    public List<Map<String ,Object>> findMoney(@RequestParam String year) {
        List<Map<String ,Object>> list = new ArrayList<>();
        list= resorderBiz.findMoney(year);
        if (list.size()<=0){
            Map<String ,Object>map=new HashMap<>();
            map.put("code",0);
            map.put("msg","查询失败");
            list.add(map);
        }else{
            for (int i = 0; i < list.size(); i++) {
                Map<String ,Object>map=new HashMap<>();
                String month= (String) list.get(i).get("name");
                month=month+"月";
                map.put("name",month);
                map.put("value",list.get(i).get("value"));
                list.set(i,map);
            }
        }
        return list;
    }

    @RequestMapping(value = "sys", method = {RequestMethod.GET, RequestMethod.POST})
    public Map<String, Object> sys() throws Exception {
        Map<String, Object> map = new HashMap<>();
        SystemHardwareInfo s = new SystemHardwareInfo();
        s.copyTo();
        map.put("data", s);
        return map;
    }

    @RequestMapping("findOldAll")
    public Map<String,Object>findOldAll(@RequestParam Integer PageNo,@RequestParam Integer PageSize,@RequestParam String the_time ,HttpSession session){
        Map<String, Object> map = new HashMap<>();

        if (session.getAttribute("resuser") == null) {
            map.put("code", -2);
            map.put("msg", "非登录用户不能下单");
            return map;
        }
        Resuser resuser = (Resuser) session.getAttribute("resuser");
        Integer userid = resuser.getUserid();
        System.out.println(PageNo+"pageno:"+PageSize+"pagesize");
        Integer Pageno = PageNo*PageSize-PageSize;
        List<Map<String,Object>>list = new ArrayList<>();

        List<Map<String,Object>>list1 = resorderBiz.findOldAll1(the_time,userid);
        System.out.println();
        if(!resorderBiz.findOldAll(Pageno,PageSize,the_time,userid).isEmpty()) {
            list = resorderBiz.findOldAll(Pageno, PageSize,the_time,userid);
            System.out.println(list.size());
            map.put("data", list);
            map.put("code",1);
            map.put("ru",resuser);
            map.put("total",list1.size());
        }else {
            map.put("code",0);
        }
        return map;
    }
    @RequestMapping("deleteOldByRoid")
    public void deleteOldByRoid(@RequestParam Integer roid){
        resorderBiz.delete_orderOldBy_roid(roid);
    }
    @RequestMapping("updataStarByRoid")
    public Map<String,Object> updataStarByRoid(@RequestParam Integer roid,@RequestParam Integer star,HttpSession session){
        Map<String,Object>map = new HashMap<>();
        if (session.getAttribute("star_roid")!=null){
            if(Integer.parseInt(session.getAttribute("star_roid").toString())==roid){
                map.put("code",2);
                map.put("msg","该订单已评价");
            }
        }else{
            if (resorderBiz.updataByRoidStar(roid,star)!=0){
                map.put("code",1);
                session.setAttribute("star_roid",roid);
            }
        }
        return map;
    }
}

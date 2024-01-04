package com.yc.biz;


import com.yc.bean.Resuser;
import com.yc.web.model.MyPageBean;

import java.util.List;
import java.util.Map;

public interface ResuserBiz {
    public Resuser findByName(String name);
    public Resuser findByName(String name,String password);
    public Resuser findById(Integer userid);
    //刘帅
    public List<Resuser> findAll();
    public int upStatus(Integer userid,String username,String pwd);
    public int upStatus1(Integer userid,String username,String pwd);
    //陈荣
    public Boolean updateLastOnline(Integer userid);

    /**
     * 用户最后一次在线的时间
     */
    String lastOnlineTime(String username);
    /**
     * 用户的下单次数
     */
    Integer orderCount(String username);

    /**
     * 用户一次消费的时间
     */
    String firstOrderTime(String username );
    /**
     * 查询用户历史评价中好评、中评和差评的数量
     */
    public Map<String,Integer> selectUserAppraise(String username);


}

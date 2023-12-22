package com.yc.biz;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yc.bean.Resfood;
import com.yc.config.RedisKeys;
import com.yc.dao.ResFoodMapper;
import com.yc.web.model.MyPageBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@Transactional(readOnly = true)
public class ResfoodBizImpl implements ResfoodBiz {
    @Value("${nginx.address}")
    private String nginxAddress="http://localhost:8888/";
    @Autowired
    private ResFoodMapper resFoodMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public List<Resfood> findAll() {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.orderByDesc("fid");
        return resFoodMapper.selectList(wrapper);
    }

    @Override
    public Resfood findById(Integer fid) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("fid", fid);
        Resfood resfood = resFoodMapper.selectOne(wrapper);
        return resfood;
    }

    @Override
    public MyPageBean findByPage(int pageno, int pagesize, String sortby, String sort) {
        QueryWrapper wrapper = new QueryWrapper();
        if (sort.equalsIgnoreCase("desc")) {
            wrapper.orderByDesc(sortby);
        } else {
            wrapper.orderByAsc(sortby);
        }
        // 设置分页信息, 查第3页, 每页2条数据
        Page<Resfood> page = new Page<>(pageno, pagesize);
        // 执行分页查询
        // 获取分页查询结果
        Page page1 = resFoodMapper.selectPage(page, wrapper);
        MyPageBean myPageBean = new MyPageBean();
        if (page1 != null) {
            //到redis中查询这些Resfood浏览数
            List<Resfood> list = page1.getRecords();
            List<String> keys = new ArrayList<String>();
            for (Resfood rf : list) {
                keys.add(RedisKeys.RESFOOD_DETAIL_COUNT_FID_ + rf.getFid());
            }
           // 一次查询多个键的值（浏览数）
            List<Integer> allFoodDetailCountValues = redisTemplate.opsForValue().multiGet(keys);
            for (int i = 0; i < list.size(); i++) {
                if (allFoodDetailCountValues.get(i) == null) {
                    list.get(i).setDetail_count(0L);
                } else {
                    list.get(i).setDetail_count(Long.valueOf(allFoodDetailCountValues.get(i).toString()));
                }//再修改图片的地址
                list.get(i).setFphoto(nginxAddress+list.get(i).getFphoto());
            }
            //还要查询 下订数
            myPageBean.setCode(1);
            myPageBean.setPageno(page1.getCurrent());//当前页数
            myPageBean.setPagesize(page1.getSize());//每页多少
            myPageBean.setTotal(page1.getTotal());//总记录数
            myPageBean.setTotalpages(page1.getPages());//总页数
            myPageBean.setDataset(page1.getRecords());//查询页码的菜品
        } else {
            myPageBean.setCode(2);
        }
        return myPageBean;
    }

    @Transactional(
            propagation = Propagation.SUPPORTS,//
            isolation = Isolation.DEFAULT,//隔离级别和数据库一致
            timeout = 2000,//超市时间
            readOnly = false,
            rollbackFor = RuntimeException.class
    )
    @Override
    public Integer addResfood(Resfood food) {
        return this.resFoodMapper.insert(food);
    }

}

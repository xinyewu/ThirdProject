package com.yc.biz;

import com.yc.dao.address_manageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class address_manageBiz implements address_manageBizImpl {
    @Autowired
    private address_manageMapper addressManageMapper;

    @Override
    public List<Map<String, Object>> findById(Integer usid, Integer pageno_l, Integer pagesize_l) {

        return addressManageMapper.findById(usid, pageno_l, pagesize_l);
    }

    @Override
    public Integer updata_default(Integer adid, Integer usid) {

        return addressManageMapper.updata_default(usid) + addressManageMapper.updata_defalut_two(adid);
    }

    @Override
    public Integer add_local(Integer usid, String address, String detailed_address, String tel, String landline_telephone, String email, String alias, String username) {
        return addressManageMapper.add_local(usid, address, detailed_address, tel, landline_telephone, email, alias, username);
    }

    @Override
    public List<Map<String, Object>> findByIdNot(Integer usid) {
        return addressManageMapper.findByIdNot(usid);
    }

    @Override
    public Integer update_local(String username, String address, String detailed_address, String tel, String landline_telephone, String email, String alias, Integer adid) {
        return addressManageMapper.update_local(username, address, detailed_address, tel, landline_telephone, email, alias, adid);
    }

    @Override
    public Integer delete_local(Integer adid) {
        return addressManageMapper.deleteById(adid);
    }

    @Override
    public List<Map<String, Object>> findByIdDe(Integer usid) {
        return addressManageMapper.findByIdDe(usid);
    }
}

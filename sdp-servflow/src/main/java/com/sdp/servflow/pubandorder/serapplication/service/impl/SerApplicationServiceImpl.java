package com.sdp.servflow.pubandorder.serapplication.service.impl;

import com.sdp.frame.base.dao.DaoHelper;
import com.sdp.servflow.pubandorder.serapplication.model.SerApplicationBean;
import com.sdp.servflow.pubandorder.serapplication.service.SerApplicationService;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Description:
 *
 * @author Niu Haoxuan
 * @date Created on 2017/11/10.
 */
@Service
public class SerApplicationServiceImpl implements SerApplicationService{

    /**
     * DaoHelper
     */
    @Resource
    private DaoHelper daoHelper;

    @Override
    public List<SerApplicationBean> getAllByCondition(Map<String, String> map) {
        return daoHelper.queryForList("com.bonc.frame.web.mapper.puborder.SerApplicationMapper.getAllByCondition", map);
    }

    @Override
    public Map<String, Object> selectPage(Map<String, Object> paramMap, String start, String length) {
        return daoHelper.queryForPageList("com.bonc.frame.web.mapper.puborder.SerApplicationMapper.selectPage", paramMap, start, length);
    }

    @Override
    public int insert(SerApplicationBean serApplicationBean) {
        return daoHelper.insert("com.bonc.frame.web.mapper.puborder.SerApplicationMapper.insert", serApplicationBean);
    }

    @Override
    public int update(SerApplicationBean serApplicationBean) {
        return daoHelper.update("com.bonc.frame.web.mapper.puborder.SerApplicationMapper.updateByPrimaryKey", serApplicationBean);
    }

    @Override
    public int delete(String applicationId) {
        return daoHelper.delete("com.bonc.frame.web.mapper.puborder.SerApplicationMapper.deleteByPrimaryKey", applicationId);
    }
}

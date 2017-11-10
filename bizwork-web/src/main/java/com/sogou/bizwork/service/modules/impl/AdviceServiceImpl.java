package com.sogou.bizwork.service.modules.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sogou.bizwork.bo.modules.Advice;
import com.sogou.bizwork.bo.modules.AdvicePo;
import com.sogou.bizwork.dao.modules.AdviceMapper;
import com.sogou.bizwork.service.modules.AdviceService;

/**
 * 获取整个项目组的代码或者个人的完成情况service
 * @author lijiantong
 */
@Service
public class AdviceServiceImpl implements AdviceService {

    @Autowired
    private AdviceMapper adviceMapper;

    @Override
    public void saveUserAdvice(Advice advice, String chinesename) {
        AdvicePo po = new AdvicePo();
        po.setChineseName(chinesename);
        po.setComment(advice.getComment());
        po.setTime(advice.getTime());
        String picUrl = "";
        for (String str : advice.getPicUrl()) {
            picUrl += str;
            picUrl += ";";
        }
        po.setPicUrl(picUrl);
        adviceMapper.saveAdvice(po);
    }

    @Override
    public List<Advice> getUserAdvice(String chinesename) {
        List<AdvicePo> list = adviceMapper.getAdviceByName(chinesename);
        List<Advice> res = new ArrayList<Advice>();
        for (AdvicePo po : list) {
            Advice advice = new Advice();
            advice.setComment(po.getComment());
            advice.setTime(po.getTime());
            String picUrl = po.getPicUrl();
            if (picUrl == null || "".equals(picUrl.trim())) {
                // do nothing
            } else {
                String[] pics = picUrl.split(";");
                List<String> url = new ArrayList<String>();
                for (String s : pics) {
                    url.add(s);
                }
                advice.setPicUrl(url);
            }
            res.add(advice);
        }
        return res;
    }
}

package com.sogou.bizwork.dao.modules;

import java.util.List;

import com.sogou.bizwork.bo.modules.AdvicePo;

public interface AdviceMapper {

    void saveAdvice(AdvicePo po);

    List<AdvicePo> getAdviceByName(String chinesename);
}

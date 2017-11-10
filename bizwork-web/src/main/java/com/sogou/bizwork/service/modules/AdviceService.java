package com.sogou.bizwork.service.modules;

import java.util.List;

import com.sogou.bizwork.bo.modules.Advice;

public interface AdviceService {

    void saveUserAdvice(Advice advice, String chinesename);

    List<Advice> getUserAdvice(String chinesename);

}

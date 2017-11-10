package com.sogou.bizwork.msg.service.api.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sogou.bizwork.api.exception.ApiTException;
import com.sogou.bizwork.api.message.BriefTypeMessage;
import com.sogou.bizwork.api.message.MessageResult;
import com.sogou.bizwork.api.message.service.BizworkMessageTService;
import com.sogou.bizwork.bo.msg.MessageBrief;
import com.sogou.bizwork.dao.msg.MessageBriefDao;

@Service("bizworkMessageTService")
public class BizworkMessageTServiceImpl implements BizworkMessageTService.Iface {
    private final Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private MessageBriefDao messageBriefDao;
    
    // MessageBriefDao messageBriefDao;
    @Override
    public MessageResult insertBriefMessage(
            List<BriefTypeMessage> briefTypeMessages) throws ApiTException,
            TException {
        MessageResult result = new MessageResult();
        if (briefTypeMessages == null) {
            result.setErrorMsg( "brief messages cannot be null");
            result.setErrorCode(402);
            return result;
        }
//        messageBriefDao.deleteBriefMessages(briefTypeMessages);
        messageBriefDao.deleteAllBriefMessages();
        int count = messageBriefDao.addBriefMessages(briefTypeMessages);
        if (count <= 0) {
            result.setErrorMsg("update failed please, try again later!");
            result.setErrorCode(502);
        }
        return result;
    }

}
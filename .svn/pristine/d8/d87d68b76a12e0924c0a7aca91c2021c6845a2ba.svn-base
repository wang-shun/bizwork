package com.sogou.bizwork.service.msg.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sogou.bizwork.dao.msg.MessageReceiverDao;
import com.sogou.bizwork.service.msg.MessageReceiverService;


/**
 * @description 主要处理message_receiver表相关的业务逻辑
 * @author linxionghui
 * @date 2016-11-14
 */
@Service
public class MessageReceiverImpl implements MessageReceiverService{
    
    @Autowired
    private MessageReceiverDao messageReceiverDao;
    
    @Override
    public void setToRead(int employeeId, int mesTypeId) {
        Map<String, Integer> params = new HashMap<String, Integer>();
        params.put("employeeId", employeeId);
        params.put("mesTypeId", mesTypeId);
        messageReceiverDao.setStatusToRead(params);
    }
    
    
    public MessageReceiverDao getMessageReceiverDao() {
        return messageReceiverDao;
    }
    public void setMessageReceiverDao(MessageReceiverDao messageReceiverDao) {
        this.messageReceiverDao = messageReceiverDao;
    }
}

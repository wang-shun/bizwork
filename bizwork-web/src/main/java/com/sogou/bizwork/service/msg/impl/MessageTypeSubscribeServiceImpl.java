package com.sogou.bizwork.service.msg.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sogou.bizwork.bo.msg.MessageTypeSubscribe;
import com.sogou.bizwork.dao.msg.MessageTypeSubscribeDao;
import com.sogou.bizwork.service.msg.MessageTypeSubscribeService;
import com.sogou.bizwork.util.MessageUtils;

/**
 * @description 主要处理message_type_subscribe表相关的业务逻辑
 * @author linxionghui
 * @date 2016-11-14
 */
@Service
public class MessageTypeSubscribeServiceImpl implements MessageTypeSubscribeService{
    private static final Logger logger = LoggerFactory.getLogger(MessageTypeSubscribeServiceImpl.class);
    
    @Autowired
    private MessageTypeSubscribeDao messageTypeSubscribeDao;
    
    @Override
    public int subscribeOrCancel(List<MessageTypeSubscribe> mts) {
        int retCode = 0;  //0 代表订阅消息，1代表取消订阅
        if (CollectionUtils.isEmpty(mts)) {
            logger.error("message type subscribe cannot be null", MessageUtils.getTraceInfo());
            return 0;
        }
        messageTypeSubscribeDao.deleteSubscribes(mts);
        retCode = messageTypeSubscribeDao.insertSubscribes(mts);
        return retCode;
    }
    
    
    public MessageTypeSubscribeDao getMessageTypeSubscribeDao() {
        return messageTypeSubscribeDao;
    }
    public void setMessageTypeSubscribeDao(
            MessageTypeSubscribeDao messageTypeSubscribeDao) {
        this.messageTypeSubscribeDao = messageTypeSubscribeDao;
    }
}

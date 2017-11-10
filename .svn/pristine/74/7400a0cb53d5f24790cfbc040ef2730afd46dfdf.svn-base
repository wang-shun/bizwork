package com.sogou.bizwork.service.msg.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.sogou.bizwork.bo.msg.BriefMessageDto;
import com.sogou.bizwork.bo.msg.ComplexMessageTypeDto;
import com.sogou.bizwork.bo.msg.MessageBrief;
import com.sogou.bizwork.bo.msg.MessageBriefPojo;
import com.sogou.bizwork.bo.msg.MessageTypeRelation;
import com.sogou.bizwork.bo.msg.MsgSubTypePojo;
import com.sogou.bizwork.bo.msg.MsgTypeRelationDto;
import com.sogou.bizwork.bo.msg.Receiver;
import com.sogou.bizwork.bo.msg.SubscribeMessageType;
import com.sogou.bizwork.cas.user.service.UserService;
import com.sogou.bizwork.dao.msg.MessageBriefDao;
import com.sogou.bizwork.dao.msg.MessageTypeDao;
import com.sogou.bizwork.service.msg.MessageTypeService;
import com.sogou.bizwork.util.MessageUtils;

/**
 * @description 主要处理message_type表相关的业务逻辑
 * @author linxionghui
 *
 */
@Service
public class MessageTypeServiceImpl implements MessageTypeService {
    private static final Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);
    
    @Autowired
    private MessageTypeDao messageTypeDao;
    @Autowired
    private MessageBriefDao messageBriefDao;
    @Autowired
    private UserService userService;
    
    @Override
    public List<MsgTypeRelationDto> getMessageTypeRelation() {
        Set<Integer> mesTypeIdSet = new HashSet<Integer>();
        List<MsgTypeRelationDto> result = new ArrayList<MsgTypeRelationDto>();
        
        List<MessageTypeRelation> messageTypeRelations = messageTypeDao.getMessageTypeRelation();
        if (CollectionUtils.isNotEmpty(messageTypeRelations)) {
            for (MessageTypeRelation relation : messageTypeRelations) {
                if (mesTypeIdSet.contains(relation.getMesTypeId())) {  //子类属于同一父类
                    List<MsgSubTypePojo> msgSubTypePojos = 
                            result.get(result.size() - 1).getSubMesTypeList();
                    msgSubTypePojos.add(this.getMsgSubTypePojo(relation));
                } else {  //出现新的父类型
                    mesTypeIdSet.add(relation.getMesTypeId());
                    MsgTypeRelationDto msgTypeRelationDto = new MsgTypeRelationDto();
                    msgTypeRelationDto.setMesTypeId(relation.getMesTypeId());
                    msgTypeRelationDto.setMesTypeName(relation.getMesTypeName());
                    
                    List<MsgSubTypePojo> msgSubTypePojos = new ArrayList<MsgSubTypePojo>();
                    msgSubTypePojos.add(this.getMsgSubTypePojo(relation));
                    
                    msgTypeRelationDto.setSubMesTypeList(msgSubTypePojos);
                    result.add(msgTypeRelationDto);
                }
            }
        } else {
            logger.warn("message type relation is null", MessageUtils.getTraceInfo());
        }
        return result;
    }
    
    
    private MsgSubTypePojo getMsgSubTypePojo(MessageTypeRelation relation) {
        MsgSubTypePojo msgSubTypePojo = new MsgSubTypePojo();
        msgSubTypePojo.setMesTypeId(relation.getSubMesTypeId());
        msgSubTypePojo.setMesTypeName(relation.getSubMesTypeName());
        return msgSubTypePojo;
    }

    public MessageTypeDao getMessageTypeDao() {
        return messageTypeDao;
    }
    public void setMessageTypeDao(MessageTypeDao messageTypeDao) {
        this.messageTypeDao = messageTypeDao;
    }
    
    
    @Override
    public int setBriefMesTypeToRead(Integer employeeId, Integer mesTypeId) {
        // TODO Auto-generated method stub
        if (employeeId == null || mesTypeId == null) {
            logger.error("employeeId or mesTypeId cannot be null", MessageUtils.getTraceInfo());
            return 0;
        }
        Map<String, Integer> params = new HashMap<String, Integer>();
        params.put("employeeId", employeeId);
        params.put("mesTypeId", mesTypeId);
        return messageTypeDao.setBriefMesTypeToRead(params);
    }

    @Override
    public int setBriefMesToRead(Integer employeeId, Integer mesTypeId) {
        if (employeeId == null || mesTypeId == null) {
            logger.error("employeeId or mesTypeId cannot be null", MessageUtils.getTraceInfo());
            return 0;
        }
        Map<String, Integer> params = new HashMap<String, Integer>();
        params.put("employeeId", employeeId);
        params.put("mesTypeId", mesTypeId);
        messageTypeDao.setBriefMesToRead(params);
        return 0;
    }
    
    @Override
    public int setComplexMesTypeToRead(Integer employeeId, Integer mesTypeId) {
        // TODO Auto-generated method stub
        if (employeeId == null || mesTypeId == null) {
            logger.error("employeeId or mesTypeId cannot be null", MessageUtils.getTraceInfo());
            return 0;
        }
        Map<String, Integer> params = new HashMap<String, Integer>();
        params.put("employeeId", employeeId);
        params.put("mesTypeId", mesTypeId);
        return messageTypeDao.setComplexMesTypeToRead(params);
    }
    
    @Override
    public Integer getParentTypeId(Integer mesTypeId) {
        // TODO Auto-generated method stub
        if (mesTypeId == null) {
            logger.error("mesTypeId cannot be null", MessageUtils.getTraceInfo());
            return 0;
        }
        return messageTypeDao.getParentTypeId(mesTypeId);
    }


    @Override
    public List<ComplexMessageTypeDto> getComplexMessageTypeDtos() {
        // TODO Auto-generated method stub
        return messageTypeDao.getComplexMessageTypeDtos();
    }


    @Override
    public Integer checkBriefMsg(Integer mesTypeId) {
        if (mesTypeId == null) {
            logger.error("mesTypeId cannot be null", MessageUtils.getTraceInfo());
            return 0;
        }
        return messageBriefDao.checkBriefMsg(mesTypeId);
    }


    @Override
    public Integer addBriefMsg(BriefMessageDto briefMessageDto) {
        if (briefMessageDto == null) {
            logger.error("brief message cannot be null", MessageUtils.getTraceInfo());
            return 0;
        }
        //将employeeid和groupid拆分为每个接收人
        List<Receiver> receivers = JSONArray.parseArray(briefMessageDto.getReceivers(), Receiver.class);
//        List<MsgReceiver> mrs = new ArrayList<MsgReceiver>();
        List<MessageBrief> mbs = new ArrayList<MessageBrief>();
        Set<Integer> set = new HashSet<Integer>();  //set用于排除重复接收人
        if (CollectionUtils.isNotEmpty(receivers)) {
            for (Receiver r : receivers) {
                if (r.getType() == 0) {
                    if (set.add(r.getId())) {
                        mbs.add(this.getBriefMsgReceiver(r.getId(), briefMessageDto));
                    }
                } else {
                    List<Integer> employeeIds = userService.getEmployeeIdsByGroupId(r.getId());
                    for (Integer employeeId : employeeIds) {
                        if (set.add(employeeId)) {
                            mbs.add(this.getBriefMsgReceiver(employeeId, briefMessageDto));
                        }
                    }
                }
            }
        } else {
            logger.warn("message receivers is null", MessageUtils.getTraceInfo());
        }
        MessageBriefPojo mbp = new MessageBriefPojo();
        mbp.setMesTypeId(briefMessageDto.getMesTypeId());
        mbp.setMessageBriefs(mbs);
        messageBriefDao.deleteBriefMessage(mbp);
        return messageBriefDao.addBriefMessage(mbs);
    }
    private MessageBrief getBriefMsgReceiver(Integer employeeId, BriefMessageDto briefMessageDto) {
        if (employeeId == null || briefMessageDto == null) {
            logger.error("employeeI or brief message is null", MessageUtils.getTraceInfo());
            return new MessageBrief();
        }
        MessageBrief messageBrief = new MessageBrief();
        messageBrief.setMesTypeId(briefMessageDto.getMesTypeId());
        messageBrief.setNum(briefMessageDto.getNum());
        messageBrief.setEmployeeId(employeeId);
        return messageBrief;
    }


    @Override
    public List<SubscribeMessageType> getSubscribeMessageType(Integer employeeId) {
        // TODO Auto-generated method stub
        if (employeeId == null) {
            logger.error("employeeI is null", MessageUtils.getTraceInfo());
            return new ArrayList<SubscribeMessageType>();
        }
        return messageTypeDao.getSubscribeMessageType(employeeId);
    }

}

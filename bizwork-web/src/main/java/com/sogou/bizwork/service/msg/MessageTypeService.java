package com.sogou.bizwork.service.msg;

import java.util.List;

import com.sogou.bizwork.bo.msg.BriefMessageDto;
import com.sogou.bizwork.bo.msg.ComplexMessageTypeDto;
import com.sogou.bizwork.bo.msg.MsgTypeRelationDto;
import com.sogou.bizwork.bo.msg.SubscribeMessageType;

public interface MessageTypeService {
    
    public Integer checkBriefMsg(Integer mesTypeId);
    public Integer addBriefMsg(BriefMessageDto briefMessageDto);
    public List<SubscribeMessageType> getSubscribeMessageType(Integer employeeId);
    
    public Integer getParentTypeId(Integer mesTypeId);
    public List<MsgTypeRelationDto> getMessageTypeRelation(); 
    public List<ComplexMessageTypeDto> getComplexMessageTypeDtos();
    public int setBriefMesTypeToRead(Integer employeeId, Integer mesTypeId);
    public int setBriefMesToRead(Integer employeeId, Integer mesTypeId);
    public int setComplexMesTypeToRead(Integer employeeId, Integer mesTypeId);
    
}

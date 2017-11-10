package com.sogou.bizwork.dao.msg;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.sogou.bizwork.bo.msg.ComplexMessageTypeDto;
import com.sogou.bizwork.bo.msg.MessageType;
import com.sogou.bizwork.bo.msg.MessageTypeExample;
import com.sogou.bizwork.bo.msg.MessageTypeRelation;
import com.sogou.bizwork.bo.msg.SubscribeMessageType;

@Repository
public interface MessageTypeDao {
    List<MessageTypeRelation> getMessageTypeRelation();
    List<ComplexMessageTypeDto> getComplexMessageTypeDtos();
    int setBriefMesTypeToRead(Map<String, Integer> params);
    int setBriefMesToRead(Map<String, Integer> params);
    int setComplexMesTypeToRead(Map<String, Integer> params);
    List<SubscribeMessageType> getSubscribeMessageType(Integer employeeId);
    
    Integer getParentTypeId(Integer employeeId);
    
    long countByExample(MessageTypeExample example);

    int deleteByExample(MessageTypeExample example);

    int deleteByPrimaryKey(Integer mesTypeId);

    int insert(MessageType record);

    int insertSelective(MessageType record);

    List<MessageType> selectByExample(MessageTypeExample example);

    MessageType selectByPrimaryKey(Integer mesTypeId);

    int updateByExampleSelective(@Param("record") MessageType record, @Param("example") MessageTypeExample example);

    int updateByExample(@Param("record") MessageType record, @Param("example") MessageTypeExample example);

    int updateByPrimaryKeySelective(MessageType record);

    int updateByPrimaryKey(MessageType record);
}
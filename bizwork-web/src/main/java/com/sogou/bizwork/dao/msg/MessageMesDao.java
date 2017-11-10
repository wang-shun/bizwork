package com.sogou.bizwork.dao.msg;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.sogou.bizwork.bo.msg.BriefMessage;
import com.sogou.bizwork.bo.msg.BriefSystemMessage;
import com.sogou.bizwork.bo.msg.ComplexMessage;
import com.sogou.bizwork.bo.msg.ComplexSystemMessage;
import com.sogou.bizwork.bo.msg.DepartmentActivityDto;
import com.sogou.bizwork.bo.msg.MessageDetail;
import com.sogou.bizwork.bo.msg.MessageMes;
import com.sogou.bizwork.bo.msg.MessageMesExample;
import com.sogou.bizwork.bo.msg.MsgReceiverPojo;
import com.sogou.bizwork.bo.msg.ScrollMessageDto;
import com.sogou.bizwork.bo.msg.SysMsgDto;
import com.sogou.bizwork.bo.msg.SystemMessageDto;

@Repository
public interface MessageMesDao {
    List<ComplexMessage> getComplexMessages(long employeeId);
    List<BriefMessage> getBriefMessages(long employeeId);
    List<ScrollMessageDto> getScrollMessages(Integer employeeId);
    List<ComplexSystemMessage> getComplexSystemMessages(SysMsgDto sysMsgDto);
    Integer getSystemMessageAmount(SysMsgDto sysMsgDto);
    List<BriefSystemMessage> getBriefSystemMessages(SysMsgDto sysMsgDto);
    List<DepartmentActivityDto> getDepartmentActivities(Integer employeeId);
    int setMessageToReadByComplexMesId(MsgReceiverPojo msgReceiverPojo);
    MessageDetail getMessageDetail(Long employeeId);
    void addSystemMessage(SystemMessageDto systemMsgDto);
    int abandonMessage(Long mesId);
    
    long countByExample(MessageMesExample example);

    int deleteByExample(MessageMesExample example);

    int deleteByPrimaryKey(Long mesId);

    int insert(MessageMes record);

    int insertSelective(MessageMes record);

    List<MessageMes> selectByExample(MessageMesExample example);

    MessageMes selectByPrimaryKey(Long mesId);

    int updateByExampleSelective(@Param("record") MessageMes record, @Param("example") MessageMesExample example);

    int updateByExample(@Param("record") MessageMes record, @Param("example") MessageMesExample example);

    int updateByPrimaryKeySelective(MessageMes record);

    int updateByPrimaryKey(MessageMes record);
}
package com.sogou.bizwork.dao.msg;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.sogou.bizwork.api.message.BriefTypeMessage;
import com.sogou.bizwork.bo.msg.BriefMessageDto;
import com.sogou.bizwork.bo.msg.BriefMessagePojo;
import com.sogou.bizwork.bo.msg.MessageBrief;
import com.sogou.bizwork.bo.msg.MessageBriefExample;
import com.sogou.bizwork.bo.msg.MessageBriefKey;
import com.sogou.bizwork.bo.msg.MessageBriefPojo;

@Repository
public interface MessageBriefDao {
    Integer checkBriefMsg(Integer mesTypeId);
    int addBriefMessage(List<MessageBrief> messageBriefs);
    int addBriefMessages(List<BriefTypeMessage> briefTypeMessages);
    long countByExample(MessageBriefExample example);
    int updateBriefMessage(BriefMessageDto briefMessageDto);
    int deleteBriefMessages(List<BriefTypeMessage> briefTypeMessages);

    int deleteByExample(MessageBriefExample example);

    int deleteByPrimaryKey(MessageBriefKey key);

    int insert(MessageBrief record);

    int insertSelective(MessageBrief record);

    List<MessageBrief> selectByExample(MessageBriefExample example);

    MessageBrief selectByPrimaryKey(MessageBriefKey key);

    int updateByExampleSelective(@Param("record") MessageBrief record, @Param("example") MessageBriefExample example);

    int updateByExample(@Param("record") MessageBrief record, @Param("example") MessageBriefExample example);

    int updateByPrimaryKeySelective(MessageBrief record);

    int updateByPrimaryKey(MessageBrief record);
    int deleteBriefMessage(MessageBriefPojo messageBriefPojo);
    int deleteBriefMessages(BriefMessagePojo briefMessagePojo);
    void deleteAllBriefMessages();
}
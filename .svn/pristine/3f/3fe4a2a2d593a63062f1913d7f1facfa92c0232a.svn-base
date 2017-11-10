package com.sogou.bizwork.dao.msg;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.sogou.bizwork.bo.msg.MessageReceiver;
import com.sogou.bizwork.bo.msg.MessageReceiverExample;
import com.sogou.bizwork.bo.msg.MsgReceiver;

@Repository
public interface MessageReceiverDao {
    void setStatusToRead(Map<String, Integer> params);
    
    long countByExample(MessageReceiverExample example);

    int deleteByExample(MessageReceiverExample example);

    int deleteByPrimaryKey(Long id);

    int insert(MessageReceiver record);

    int insertSelective(MessageReceiver record);
    
    int insertReceivers(List<MsgReceiver> receivers);

    List<MessageReceiver> selectByExample(MessageReceiverExample example);

    MessageReceiver selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") MessageReceiver record, @Param("example") MessageReceiverExample example);

    int updateByExample(@Param("record") MessageReceiver record, @Param("example") MessageReceiverExample example);

    int updateByPrimaryKeySelective(MessageReceiver record);

    int updateByPrimaryKey(MessageReceiver record);
}
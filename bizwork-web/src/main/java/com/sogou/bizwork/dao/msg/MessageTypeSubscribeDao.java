package com.sogou.bizwork.dao.msg;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.sogou.bizwork.bo.msg.MessageTypeSubscribe;
import com.sogou.bizwork.bo.msg.MessageTypeSubscribeExample;
import com.sogou.bizwork.bo.msg.MessageTypeSubscribeKey;

@Repository
public interface MessageTypeSubscribeDao {
    long countByExample(MessageTypeSubscribeExample example);

    int deleteByExample(MessageTypeSubscribeExample example);

    int deleteByPrimaryKey(MessageTypeSubscribeKey key);

    int insert(MessageTypeSubscribe record);

    int insertSelective(MessageTypeSubscribe record);

    List<MessageTypeSubscribe> selectByExample(MessageTypeSubscribeExample example);

    MessageTypeSubscribe selectByPrimaryKey(MessageTypeSubscribeKey key);

    int updateByExampleSelective(@Param("record") MessageTypeSubscribe record, @Param("example") MessageTypeSubscribeExample example);

    int updateByExample(@Param("record") MessageTypeSubscribe record, @Param("example") MessageTypeSubscribeExample example);

    int updateByPrimaryKeySelective(MessageTypeSubscribe record);

    int updateByPrimaryKey(MessageTypeSubscribe record);
    
    int deleteSubscribes(List<MessageTypeSubscribe> messageTypeSubscribes);
    int insertSubscribes(List<MessageTypeSubscribe> messageTypeSubscribes);
}
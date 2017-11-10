package com.sogou.bizwork.service.msg;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.sogou.bizwork.bo.msg.DepartmentActivityDto;
import com.sogou.bizwork.bo.msg.MessageDetail;
import com.sogou.bizwork.bo.msg.MsgReceiver;
import com.sogou.bizwork.bo.msg.MsgReceiverPojo;
import com.sogou.bizwork.bo.msg.ScrollMessageDto;
import com.sogou.bizwork.bo.msg.SubscribeMsgDto;
import com.sogou.bizwork.bo.msg.SysMsgDto;
import com.sogou.bizwork.bo.msg.SysMsgsDto;
import com.sogou.bizwork.bo.msg.SystemMessageDto;

public interface MessageService {

    public SubscribeMsgDto getSubscribeMessages(int employeeId);

    public int setMessageToReadByMesId(MsgReceiverPojo msgReceiverPojo);

    public MessageDetail getMessageDetail(Long mesId);

    public List<ScrollMessageDto> getScrollMessages(Integer employeeId);

    public SysMsgsDto getSystemMessages(SysMsgDto sysMsgDto);

    public List<DepartmentActivityDto> getDepartmentActivities(Integer employeeId);

    public int recallMsg(Long mesId);

    public void addSysMsg(SystemMessageDto systemMsgDto);

    public void updateSysMsg(SystemMessageDto systemMsgDto);

    public String getTS();// 获取系统当前时间戳

    public String getToken(String pubid, String APIToken, String ts);// 获取服务号的token

    List<MsgReceiver> convertReceiverStrToList(String receiverStr, Long mesId);

    public List<String> getUserNameByEmployeeIds(List<MsgReceiver> mrs);

    public String sendPostRequest(String sendURL, Map<String, Object> params);

    String uploadFileToCDN(File picDocFile);

}

package com.sogou.bizwork.service.msg.impl;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.sogou.bizdev.cdn.service.CdnService;
import com.sogou.bizwork.bo.msg.BriefMessage;
import com.sogou.bizwork.bo.msg.ComplexMessage;
import com.sogou.bizwork.bo.msg.ComplexSystemMessage;
import com.sogou.bizwork.bo.msg.DepartmentActivityDto;
import com.sogou.bizwork.bo.msg.MessageDetail;
import com.sogou.bizwork.bo.msg.MessageMes;
import com.sogou.bizwork.bo.msg.MsgReceiver;
import com.sogou.bizwork.bo.msg.MsgReceiverPojo;
import com.sogou.bizwork.bo.msg.Receiver;
import com.sogou.bizwork.bo.msg.ReceiverPojo;
import com.sogou.bizwork.bo.msg.ScrollMessageDto;
import com.sogou.bizwork.bo.msg.SubscribeMsgDto;
import com.sogou.bizwork.bo.msg.SubscribeMsgPojo;
import com.sogou.bizwork.bo.msg.SubscribeSubMsgPojo;
import com.sogou.bizwork.bo.msg.SysMessagePojo;
import com.sogou.bizwork.bo.msg.SysMsgDto;
import com.sogou.bizwork.bo.msg.SysMsgsDto;
import com.sogou.bizwork.bo.msg.SystemMessageDto;
import com.sogou.bizwork.cas.user.model.ReceiverDto;
import com.sogou.bizwork.cas.user.service.UserService;
import com.sogou.bizwork.dao.msg.MessageMesDao;
import com.sogou.bizwork.dao.msg.MessageReceiverDao;
import com.sogou.bizwork.dao.msg.MessageTypeDao;
import com.sogou.bizwork.service.msg.MessageService;
import com.sogou.bizwork.service.msg.MessageTypeService;
import com.sogou.bizwork.util.MessageUtils;

/**
 * @description 主要处理message_mes表相关的业务逻辑
 * @author linxionghui
 * @date 2016-11-14
 */
@Service
public class MessageServiceImpl implements MessageService {

    private static final Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);

    @Autowired
    private MessageMesDao messageMesDao;
    @Autowired
    private MessageTypeDao messageTypeDao;
    @Autowired
    private MessageReceiverDao messageReceiverDao;
    @Autowired
    private MessageTypeService messageTypeService;

    @Resource
    private UserService userService;

    @Resource(name = "certCdnService")
    public CdnService cdnService;

    @Override
    public SubscribeMsgDto getSubscribeMessages(int employeeId) {
        SubscribeMsgDto subscribeMessageDto = new SubscribeMsgDto();
        List<SubscribeMsgPojo> subscribeMsgList = new ArrayList<SubscribeMsgPojo>();

        Set<Integer> parentTypeIdSet = new HashSet<Integer>();

        // 向subMsgList中组装简单消息
        List<BriefMessage> briefMessages = messageMesDao.getBriefMessages(employeeId);
        int briefCount = 0;
        if (CollectionUtils.isNotEmpty(briefMessages)) {
            for (BriefMessage msg : briefMessages) {
                Integer parentMesTypeId = msg.getParentMesTypeId();
                if (parentTypeIdSet.contains(parentMesTypeId)) {
                    SubscribeMsgPojo msgPojo = subscribeMsgList.get(subscribeMsgList.size() - 1);
                    List<SubscribeSubMsgPojo> subMsgs = msgPojo.getSubMsgList();
                    subMsgs.add(this.getBriefSubMsg(msg));
                    msgPojo.setCount(msgPojo.getCount() + msg.getNum());
                    briefCount += msg.getNum();
                } else { // 出现新的父类

                    parentTypeIdSet.add(parentMesTypeId);

                    SubscribeMsgPojo msgPojo = new SubscribeMsgPojo();
                    msgPojo.setMesTypeName(msg.getParentMesTypeName());
                    msgPojo.setMesTypeId(msg.getParentMesTypeId());
                    msgPojo.setCount(msg.getNum());
                    briefCount += msg.getNum();

                    List<SubscribeSubMsgPojo> subMsgs = new ArrayList<SubscribeSubMsgPojo>();
                    subMsgs.add(this.getBriefSubMsg(msg));
                    msgPojo.setSubMsgList(subMsgs);
                    subscribeMsgList.add(msgPojo);
                }
            }
        } else {
            logger.warn("Get empty brief messages! ", MessageUtils.getTraceInfo());
        }

        // 向subMsgList中组装复杂消息
        List<ComplexMessage> complexMessages = messageMesDao.getComplexMessages(employeeId);

        if (CollectionUtils.isNotEmpty(complexMessages)) {
            for (ComplexMessage msg : complexMessages) {
                Integer parentMesTypeId = msg.getParentMesTypeId();
                if (parentTypeIdSet.contains(parentMesTypeId)) {
                    SubscribeMsgPojo msgPojo = subscribeMsgList.get(subscribeMsgList.size() - 1);
                    msgPojo.setCount(msgPojo.getCount() + 1);
                    List<SubscribeSubMsgPojo> subMsgs = msgPojo.getSubMsgList();
                    subMsgs.add(this.getComplexSubMsg(msg));
                } else { // 出现新的父类
                    parentTypeIdSet.add(parentMesTypeId);

                    SubscribeMsgPojo msgPojo = new SubscribeMsgPojo();
                    msgPojo.setMesTypeName(msg.getParentMesTypeName());
                    msgPojo.setMesTypeId(parentMesTypeId);
                    msgPojo.setCount(msgPojo.getCount() + 1);

                    List<SubscribeSubMsgPojo> subMsgs = new ArrayList<SubscribeSubMsgPojo>();
                    subMsgs.add(this.getComplexSubMsg(msg));
                    msgPojo.setSubMsgList(subMsgs);
                    subscribeMsgList.add(msgPojo);
                }
            }
        }

        if (CollectionUtils.isNotEmpty(complexMessages)) {
            subscribeMessageDto.setTotalCount(briefCount + complexMessages.size());
        } else {
            subscribeMessageDto.setTotalCount(briefCount);
        }
        subscribeMessageDto.setMesList(subscribeMsgList);

        return subscribeMessageDto;
    }

    private SubscribeSubMsgPojo getComplexSubMsg(ComplexMessage msg) {
        SubscribeSubMsgPojo subMsg = new SubscribeSubMsgPojo();
        if (msg == null) {
            logger.warn("complex message cannot be null", MessageUtils.getTraceInfo());
            return subMsg;
        }
        subMsg.setMesId(msg.getMesId());
        subMsg.setMesTypeId(msg.getMesTypeId());
        subMsg.setUrl(msg.getUrl());
        subMsg.setTitle(msg.getTitle());
        return subMsg;
    }

    private SubscribeSubMsgPojo getBriefSubMsg(BriefMessage msg) {
        SubscribeSubMsgPojo subMsg = new SubscribeSubMsgPojo();
        subMsg.setMesTypeId(msg.getMesTypeId());
        subMsg.setTitle(msg.getMesTypeName());
        subMsg.setUrl(msg.getUrl());
        subMsg.setCount(msg.getNum());
        subMsg.setBriefMesType(true);
        return subMsg;
    }

    @Override
    public List<DepartmentActivityDto> getDepartmentActivities(Integer employeeId) {

        List<DepartmentActivityDto> depts = messageMesDao.getDepartmentActivities(employeeId);
//        if (CollectionUtils.isNotEmpty(depts)) {  //upload image to dfs!
//            for (DepartmentActivityDto d : depts) {
//                String pic = d.getPic();
//                if (pic != null) {
//                    int index = pic.indexOf("?filePath=");
//                    if (index > 0) {
//                        pic = "/message/getPhoto.do" + pic.substring(pic.indexOf("?filePath="));
//                    }
//                    d.setPic(pic);
//                }
//            }
//        } else {
//            logger.info("Get empty depts! ", MessageUtils.getTraceInfo());
//        }
        return depts;
    }

    @Override
    public int setMessageToReadByMesId(MsgReceiverPojo msgReceiverPojo) {
        // TODO Auto-generated method stub
        if (msgReceiverPojo == null) {
            logger.warn("message receiver cannot be null", MessageUtils.getTraceInfo());
            return 0;
        }
        return messageMesDao.setMessageToReadByComplexMesId(msgReceiverPojo);
    }

    @Override
    public List<ScrollMessageDto> getScrollMessages(Integer employeeId) {
        // TODO Auto-generated method stub
        if (employeeId == null) {
            logger.warn("employeeId cannot be null", MessageUtils.getTraceInfo());
            return new ArrayList<ScrollMessageDto>();
        }
        return messageMesDao.getScrollMessages(employeeId);
    }

    @Override
    public SysMsgsDto getSystemMessages(SysMsgDto sysMsgDto) {
        // TODO Auto-generated method stub
        SysMsgsDto result = new SysMsgsDto();
        if (sysMsgDto == null) {
            logger.warn("system message cannot be null", MessageUtils.getTraceInfo());
            return result;
        }
        List<SysMessagePojo> sysList = new ArrayList<SysMessagePojo>();
        List<ComplexSystemMessage> complexs = messageMesDao.getComplexSystemMessages(sysMsgDto);

        result.setTotalCount(messageMesDao.getSystemMessageAmount(sysMsgDto));
        if (CollectionUtils.isNotEmpty(complexs)) {
            for (ComplexSystemMessage c : complexs) {
                if (!c.getCreateId().equals(sysMsgDto.getEmployeeId()) && c.getIfRecall() != 0) {// 如果该消息不是本人创建，并且被撤回了，那么消息将不显示
                    result.setTotalCount(result.getTotalCount() - 1); // 消息总数减一
                    continue;
                }
                SysMessagePojo sysMessageDto = new SysMessagePojo();
                sysMessageDto.setMesId(c.getMesId());
                sysMessageDto.setMesTypeName(c.getMesTypeName());
                sysMessageDto.setPlatform(c.getPlatform());
                sysMessageDto.setTitle(c.getTitle());
                sysMessageDto.setUrl(c.getUrl());
                sysMessageDto.setStartDate(c.getStartDate());
                sysMessageDto.setEndDate(c.getEndDate());
                sysMessageDto.setCreateName(userService.getNameByEmployeeId(c.getCreateId()));
                if (c.getCreateId().equals(sysMsgDto.getEmployeeId())) { // 创建者和接收者是同一个人时可以编辑/撤回消息
                    if (c.getIfRecall() == 0) { // 如果是未撤回，那么可以撤回
                        sysMessageDto.setCanRecall(true);
                    } else { // 如果撤回了，就可编辑
                        sysMessageDto.setCanEdit(true);
                    }
                }
                sysList.add(sysMessageDto);
            }
        } else {
            logger.info("Get empty system messages! ", MessageUtils.getTraceInfo());
        }
        result.setMesList(sysList);
        return result;
    }

    @Override
    public MessageDetail getMessageDetail(Long mesId) {
        // TODO Auto-generated method stub
        MessageDetail messageDetail = new MessageDetail();
        MessageMes mes = messageMesDao.selectByPrimaryKey(mesId);
        if (mesId == null) {
            logger.warn("message id cannot be null", MessageUtils.getTraceInfo());
            return new MessageDetail();
        }
        messageDetail.setMesId(mes.getMesId());
        messageDetail.setMesType(messageTypeDao.selectByPrimaryKey(mes.getMesTypeId()).getMesTypeName());
        messageDetail.setMesTypeId(mes.getMesTypeId());
        messageDetail.setTitle(mes.getTitle());
        messageDetail.setContent(mes.getContent());
        messageDetail.setUrl(mes.getUrl());
        messageDetail.setPic(mes.getPic());
        String url = mes.getPic();
        if (url != null) {
            int index = url.indexOf("&fileName=") + 10;
            if (index > 10) {
                messageDetail.setPicName(url.substring(index));
            }
        }
        messageDetail.setPlatform(mes.getPlatForm());
        messageDetail.setStartDate(mes.getStartDate());
        messageDetail.setEndDate(mes.getEndDate());
        List<ReceiverPojo> receiverPojos = new ArrayList<ReceiverPojo>();
        List<Receiver> receivers = JSONArray.parseArray(mes.getReceivers(), Receiver.class);
        List<Integer> employeeIds = new ArrayList<Integer>();
        List<Integer> groupIds = new ArrayList<Integer>();
        for (Receiver r : receivers) {
            if (r.getType() == 0) { // 人
                employeeIds.add(r.getId());
            } else { // 组
                groupIds.add(r.getId());
            }
        }
        if (employeeIds != null && employeeIds.size() > 0) {
            List<ReceiverDto> eRecs = userService.getUsersByIds(employeeIds);
            for (ReceiverDto e : eRecs) {
                receiverPojos.add(converToPojo(e));
            }
        }
        if (groupIds != null && groupIds.size() > 0) {
            List<ReceiverDto> gRecs = userService.getGroupsByIds(groupIds);
            for (ReceiverDto g : gRecs) {
                receiverPojos.add(converToPojo(g));
            }
        }
        messageDetail.setReceivers(receiverPojos);
        return messageDetail;
    }

    private ReceiverPojo converToPojo(ReceiverDto receiverDto) {
        ReceiverPojo receiverPojo = new ReceiverPojo();
        receiverPojo.setId(receiverDto.getId());
        receiverPojo.setName(receiverDto.getName());
        receiverPojo.setType(receiverDto.getType());
        return receiverPojo;
    }

    @Override
    public void addSysMsg(SystemMessageDto systemMsgDto) {
        if (systemMsgDto.getMesId() != null) { // 编辑以后，将原先的消息废弃
            messageMesDao.abandonMessage(systemMsgDto.getMesId());
        }
        messageMesDao.addSystemMessage(systemMsgDto);

        // 将employeeid和groupid拆分为每个接收人
        List<MsgReceiver> mrs = convertReceiverStrToList(systemMsgDto.getReceivers(), systemMsgDto.getMesId());
        if (CollectionUtils.isNotEmpty(mrs)) {
            messageReceiverDao.insertReceivers(mrs);
        }
    }

    private MsgReceiver getReceiver(Integer employeeId, Long mesId) {
        MsgReceiver mr = new MsgReceiver();
        mr.setEmployeeId(employeeId);
        mr.setMesId(mesId);
        return mr;
    }

    @Override
    public List<MsgReceiver> convertReceiverStrToList(String receiverStr, Long mesId) {
        List<Receiver> receivers = JSONArray.parseArray(receiverStr, Receiver.class);

        List<MsgReceiver> mrs = new ArrayList<MsgReceiver>();

        Set<Integer> set = new HashSet<Integer>(); // set用于排除重复接收人

        Set<Integer> groupIdSet = new HashSet<Integer>();
        if (CollectionUtils.isNotEmpty(receivers)) {
            for (Receiver r : receivers) {
                if (r.getType() == 0) { // 接收人
                    if (set.add(r.getId())) {
                        mrs.add(this.getReceiver(r.getId(), mesId));
                    }
                } else if (r.getType() == 1) { // 接收组
                    groupIdSet.add(r.getId());
                }
            }
        }

        if (CollectionUtils.isNotEmpty(groupIdSet)) {
            List<Integer> groupIds = new ArrayList<Integer>(groupIdSet);
            List<Integer> eis = userService.getEmployeeIdsByGroupIds(groupIds);
            if (eis != null) {
                for (Integer e : eis) {
                    if (set.add(e)) {
                        mrs.add(this.getReceiver(e, mesId));
                    }
                }
            }
        } else {
            logger.warn("group set is null", MessageUtils.getTraceInfo());
        }
        return mrs;
    }

    @Override
    public void updateSysMsg(SystemMessageDto systemMsgDto) {
        // TODO Auto-generated method stub
        if (systemMsgDto == null) {
            logger.error("update system message failed for empty system message!", MessageUtils.getTraceInfo());
            return;
        }
        MessageMes messageMes = new MessageMes();
        messageMes.setMesId(systemMsgDto.getMesId());
        messageMes.setMesTypeId(systemMsgDto.getMesTypeId());
        messageMes.setTitle(systemMsgDto.getTitle());
        messageMes.setContent(systemMsgDto.getContent());
        messageMes.setUrl(systemMsgDto.getUrl());
        messageMes.setPic(systemMsgDto.getPic());
        messageMes.setStartDate(systemMsgDto.getStartDate());
        messageMes.setEndDate(messageMes.getStartDate());
        messageMes.setPlatForm(systemMsgDto.getPlatform());
        messageMes.setReceivers(systemMsgDto.getReceivers().toString());
        messageMesDao.updateByPrimaryKeySelective(messageMes);
    }

    @Override
    public int recallMsg(Long mesId) {
        if (mesId == null) {
            logger.error("recall message failed for empty messageId: " + mesId, MessageUtils.getTraceInfo());
            return 0;
        }
        MessageMes record = new MessageMes();
        record.setMesId(mesId);
        record.setIfRecall(1);
        return messageMesDao.updateByPrimaryKeySelective(record);
    }

    @Override
    public String getTS() {
        Date d = new Date();
        String s = String.valueOf(d.getTime());
        return s;
    }

    @Override
    public String getToken(String pubid, String APIToken, String ts) {
        String str = pubid + ":" + APIToken + ":" + ts;
        String res = null;
        try {
            res = MD5(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    private String MD5(String str) throws Exception {
        try {
            // 生成一个MD5加密计算摘要
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 计算md5函数
            md.update(str.getBytes());
            // digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
            // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
            return new BigInteger(1, md.digest()).toString(16);
        } catch (Exception e) {
            throw new Exception("MD5加密出现错误");
        }
    }

    @Override
    public String sendPostRequest(String url, Map<String, Object> params) {
        HttpClient client = new HttpClient();
        // 链接超时
        client.getHttpConnectionManager().getParams().setConnectionTimeout(40000);
        // 读取超时
        client.getHttpConnectionManager().getParams().setSoTimeout(40000);
        PostMethod post = new PostMethod(url);
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            // System.out.println(entry.getKey() + "::::" + entry.getValue());
            post.setParameter(entry.getKey(), (String) entry.getValue());
        }
        String response = "";
        try {
            client.executeMethod(post);
            if (post.getStatusCode() == HttpStatus.SC_OK) {
                response = "发送成功";
            } else {
                response = "发送失败";
            }
        } catch (IOException e) {
            response = "发送失败";
            e.printStackTrace();
        } finally {
            post.releaseConnection();
        }
        return response;
    }

    @Override
    public List<String> getUserNameByEmployeeIds(List<MsgReceiver> mrs) {
        List<Integer> employeeIds = new ArrayList<Integer>();
        for (MsgReceiver msg : mrs) {
            employeeIds.add(msg.getEmployeeId());
        }
        List<String> res = userService.getNameByEmployeeIds(employeeIds);
        return res;
    }

    // private final int PIC_DOC_MAX_SIZE = 1024 * 1024 * 3; // 上传资质文件最大大小 暂定3M
    // private final String PIC_DOC_TMP_PATH = "/data/actOpt/picDoc";

    // @Override
    // public String uploadPicture(ByteBuffer write_buffer) throws Exception {
    // // 1、创建临时文件
    // File picDocFile = null;
    // try {
    // // 校验内容，是否为空
    // if (write_buffer == null) {
    // throw new Exception("文件内容不能为空");
    // }
    // byte[] fileContent = write_buffer.array();
    // if (fileContent.length == 0) {
    // throw new Exception("文件大小不能为0B");
    // }
    // // 校验长度
    // if (fileContent.length >= PIC_DOC_MAX_SIZE) {
    // throw new Exception("文件大小不能超过3M");
    // }
    //
    // picDocFile = createTmpFile(fileContent);
    //
    // // 2、上传资质到cdn，成功后返回上传后的url
    // String certUrl = uploadFileToCDN(picDocFile);
    // if (certUrl == null || "".equals(certUrl)) {
    // throw new Exception("资质文件上传失败");
    // }
    // return certUrl;
    // } catch (Exception e) {
    // // 业务异常转化为 Api异常
    // throw new Exception("资质文件上传失败");
    // } finally {
    // // 删除临时文件
    // if (picDocFile != null && picDocFile.exists())
    // picDocFile.delete();
    // }
    // }

    @Override
    public String uploadFileToCDN(File picDocFile) {
        logger.info("begin uploadFileToCDN ...");
        String certUrl = null;
        try {
            certUrl = cdnService.uploadImage(picDocFile);
        } catch (Exception e) {
            logger.error("Load file to CDN failure! Exception msg:[" + e.getMessage() + "]", e);
            return null;
        }
        // 返回地址
        if (certUrl == null || "".equals(certUrl)) {
            logger.error("Load file to CDN failure! Exception msg:[CDN返回地址为空]");
            return null;
        }
        logger.info("End of uploadFileToCDN cdnurl: [" + certUrl + "]");
        return certUrl;
    }

    // private File createTmpFile(byte[] fileContent) throws Exception {
    // File picDocFile = null;
    // try {
    // String fileName = java.util.UUID.randomUUID().toString() + ".tmp";
    // File dir = new File(PIC_DOC_TMP_PATH);
    // if (!dir.exists()) {
    // dir.mkdirs();
    // }
    // picDocFile = new File(PIC_DOC_TMP_PATH + fileName);
    //
    // FileCopyUtils.copy(fileContent, picDocFile);
    //
    // } catch (Exception e) {
    // logger.error("Create temp file error! error msg:[" + e.getMessage() +
    // "]", e);
    // throw new Exception("临时文件创建失败");
    // }
    // return picDocFile;
    // }

}

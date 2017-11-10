package com.sogou.bizwork.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import com.sogou.bizwork.util.DFSUtils;

import org.apache.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.sogou.bizwork.api.exception.ApiTException;
import com.sogou.bizwork.api.xiaop.message.XiaoPMessage;
import com.sogou.bizwork.api.xiaop.message.service.XiaoPMessageTService;
import com.sogou.bizwork.bo.Result;
import com.sogou.bizwork.bo.User;
import com.sogou.bizwork.bo.msg.BriefMessageDto;
import com.sogou.bizwork.bo.msg.MessageTypeSubscribe;
import com.sogou.bizwork.bo.msg.MsgReceiver;
import com.sogou.bizwork.bo.msg.MsgReceiverPojo;
import com.sogou.bizwork.bo.msg.SysMsgDto;
import com.sogou.bizwork.bo.msg.SystemMessageDto;
import com.sogou.bizwork.bo.msg.req.AgentUserPhoto;
import com.sogou.bizwork.bo.msg.req.ClickToReadPojo;
import com.sogou.bizwork.bo.msg.req.ComplexMsgPojo;
import com.sogou.bizwork.bo.msg.req.MsgDetailPojo;
import com.sogou.bizwork.bo.msg.req.NewMsgPojo;
import com.sogou.bizwork.bo.msg.req.PublishMsgPojo;
import com.sogou.bizwork.bo.msg.req.RecallMsgPojo;
import com.sogou.bizwork.bo.msg.req.ReceiverPojo;
import com.sogou.bizwork.bo.msg.req.SetToReadPojo;
import com.sogou.bizwork.bo.msg.req.SubscribeMsgPojo;
import com.sogou.bizwork.bo.msg.req.SysMsgPojo;
import com.sogou.bizwork.cas.user.service.UserService;
import com.sogou.bizwork.constant.Constant;
import com.sogou.bizwork.service.msg.MessageService;
import com.sogou.bizwork.service.msg.MessageTypeService;
import com.sogou.bizwork.service.msg.MessageTypeSubscribeService;

/**
 * @description message任务提醒控制器
 * @author linxionghui
 * @date 2016-11-14
 *
 */
@Controller
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private MessageService messageService;
    @Autowired
    private MessageTypeService messageTypeService;
    @Autowired
    private UserService userService;
    @Autowired
    private MessageTypeSubscribeService messageTypeSubscribeService;
    @Autowired
    private XiaoPMessageTService.Iface xiaoPMessageTService;

    @Value("${pubid}")
    private String pubid;// 服务号ID
    @Value("${APIToken}")
    private String APIToken;// 服务号的APIToken
    @Value("${sendURL}")
    private String sendURL;// 发送接口url

    /**
     * @description 小闹钟的订阅消息列表
     * @param request
     * @return 订阅的消息列表与消息总数
     */
    @RequestMapping(value = "/getSubscribeMsgs.do")
    @ResponseBody
    public Result getSubscribeMessages(HttpServletRequest request) {
        Result result = new Result();
        User user = (User) request.getSession().getAttribute(Constant.SESSION_USER_KEY);
        if (user.getEmployeeid() == null) {
            result.setError(421, "employee cannot be null");
            return result;
        }
        result.setData(messageService.getSubscribeMessages(user.getEmployeeid()));
        return result;
    }

    /**
     * @description 个人中心订阅或取消订阅某些类型的消息
     * @param request
     * @return
     */
    @RequestMapping(value = "/subscribeOrCancel.do")
    @ResponseBody
    public Result subscribeOrCancel(HttpServletRequest request) {
        Result result = new Result();
        User user = (User) request.getSession().getAttribute(Constant.SESSION_USER_KEY);
        if (user.getEmployeeid() == null) {
            result.setError(421, "employee cannot be null");
            return result;
        }

        StringBuffer sb = this.getRequestStr(request);
        SubscribeMsgPojo smp = JSONObject.parseObject(sb.toString(), SubscribeMsgPojo.class);
        List<MessageTypeSubscribe> mts = new ArrayList<MessageTypeSubscribe>();
        for (ReceiverPojo rp : smp.getMessage()) {
            MessageTypeSubscribe mt = new MessageTypeSubscribe();
            if (rp.isSubscribe()) {
                mt.setIfSubscribe(0);
            } else {
                mt.setIfSubscribe(1);
            }
            mt.setEmployeeId(user.getEmployeeid());
            mt.setMesTypeId(rp.getMesTypeId());
            mts.add(mt);
        }
        messageTypeSubscribeService.subscribeOrCancel(mts);
        return result;
    }

    /**
     * @description 消息置为已读——按类型typeId
     * @param request
     * @return
     */
    @RequestMapping(value = "/setToRead.do")
    @ResponseBody
    public Result setMsgTypeToRead(HttpServletRequest request) {
        Result result = new Result();
        User user = (User) request.getSession().getAttribute(Constant.SESSION_USER_KEY);
        if (user.getEmployeeid() == null) {
            result.setError(421, "employee cannot be null");
            return result;
        }
        Integer employeeId = user.getEmployeeid();
        StringBuffer sb = getRequestStr(request);
        Integer mesTypeId = JSONObject.parseObject(sb.toString(), SetToReadPojo.class).getMesTypeId();
        Integer parentTypeId = messageTypeService.getParentTypeId(mesTypeId);

        if (parentTypeId == 0) { // 清除该类型下所有简单消息（多个）
            messageTypeService.setBriefMesTypeToRead(employeeId, mesTypeId);
        } else if (parentTypeId != mesTypeId) { // 该类型的简单消息（某个）
            messageTypeService.setBriefMesToRead(employeeId, mesTypeId);
        } else { // 该类型为复杂消息（多个）
            messageTypeService.setComplexMesTypeToRead(employeeId, mesTypeId);
        }
        return result;
    }

    /**
     * @description 消息置为已读——按某条消息id
     * @param request
     * @return
     */
    @RequestMapping(value = "/setToReadByMesId.do")
    @ResponseBody
    public Result setToReadTyMesId(HttpServletRequest request) {
        Result result = new Result();
        User user = (User) request.getSession().getAttribute(Constant.SESSION_USER_KEY);
        if (user.getEmployeeid() == null) {
            result.setError(421, "employee cannot be null");
            return result;
        }
        Integer employeeId = user.getEmployeeid();
        StringBuffer sb = this.getRequestStr(request);
        Long mesId = JSONObject.parseObject(sb.toString(), ClickToReadPojo.class).getMesId();
        MsgReceiverPojo msgReceiverPojo = new MsgReceiverPojo();
        msgReceiverPojo.setEmployeeId(employeeId);
        msgReceiverPojo.setMesId(mesId);
        result.setData(messageService.setMessageToReadByMesId(msgReceiverPojo));
        return result;
    }

    /**
     * @description 获取复杂消息类型
     * @return
     */
    @RequestMapping(value = "/getComplexMsgTypes.do")
    @ResponseBody
    public Result getComplexMsgTypes() {
        Result result = new Result();
        result.setData(messageTypeService.getComplexMessageTypeDtos());
        return result;
    }

    /**
     * @description 获取滚动消息列表
     * @param request
     * @return
     */
    @RequestMapping(value = "/getScrollMsgs.do")
    @ResponseBody
    public Result getScrollMsgs(HttpServletRequest request) {
        Result result = new Result();
        User user = (User) request.getSession().getAttribute(Constant.SESSION_USER_KEY);
        if (user.getEmployeeid() == null) {
            result.setError(421, "employee cannot be null");
            return result;
        }
        result.setData(messageService.getScrollMessages(user.getEmployeeid()));
        return result;
    }

    /**
     * @description 获取系统消息列表——所有订阅与非订阅的复杂消息
     * @param request
     * @return
     */
    @RequestMapping(value = "/getSysMsgs.do", method = RequestMethod.POST)
    @ResponseBody
    public Result getSysMsgs(HttpServletRequest request) {
        Result result = new Result();
        User user = (User) request.getSession().getAttribute(Constant.SESSION_USER_KEY);
        if (user.getEmployeeid() == null) {
            result.setError(421, "employee cannot be null");
            return result;
        }
        StringBuffer sb = this.getRequestStr(request);
        SysMsgPojo sysMsgPojo = JSONObject.parseObject(sb.toString(), ComplexMsgPojo.class).getData();
        SysMsgDto sysMsgDto = new SysMsgDto();
        sysMsgDto.setEmployeeId(user.getEmployeeid());
        if (sysMsgPojo.getStartDate() != null && !sysMsgPojo.getStartDate().trim().equals("")) {
            sysMsgDto.setStartDate(Date.valueOf(sysMsgPojo.getStartDate()));
        }
        if (sysMsgPojo.getEndDate() != null && !sysMsgPojo.getEndDate().trim().equals("")) {
            sysMsgDto.setEndDate(Date.valueOf(sysMsgPojo.getEndDate()));
        }
        sysMsgDto.setMesTypeId(sysMsgPojo.getMesTypeId());
        sysMsgDto.setEmployeeId(user.getEmployeeid());
        sysMsgDto.setFirstOne((sysMsgPojo.getPageNo() - 1) * sysMsgPojo.getPageSize());
        sysMsgDto.setPageSize(sysMsgPojo.getPageSize());
        sysMsgDto.setOnlyMySend(sysMsgPojo.isOnlyMySend());
        result.setData(messageService.getSystemMessages(sysMsgDto));
        return result;
    }

    /**
     * @description 获取部门消息列表
     * @param request
     * @return
     */
    @RequestMapping(value = "/getDeptActivities.do")
    @ResponseBody
    public Result getDeptActivities(HttpServletRequest request) {
        Result result = new Result();
        User user = (User) request.getSession().getAttribute(Constant.SESSION_USER_KEY);
        if (user.getEmployeeid() == null) {
            result.setError(421, "employee cannot be null");
            return result;
        }
        result.setData(messageService.getDepartmentActivities(user.getEmployeeid()));
        return result;
    }

    /**
     * @description 获取单条复杂消息详情
     * @param request
     * @return
     */
    @RequestMapping(value = "/getMsgDetail.do")
    @ResponseBody
    public Result getMesDetail(HttpServletRequest request) {
        Result result = new Result();
        StringBuffer sb = this.getRequestStr(request);
        Long mesId = JSONObject.parseObject(sb.toString(), MsgDetailPojo.class).getMesId();
        result.setData(messageService.getMessageDetail(mesId));

        return result;
    }

    /**
     * @description 新发布消息或者再次编辑消息——根据是否存在mesId区分
     * @param request
     * @return
     * @throws Exception 
     */
    @RequestMapping(value = "/publishMsg.do", method = RequestMethod.POST)
    @ResponseBody
    public Result publishMsg(HttpServletRequest request) throws Exception {
        Result result = new Result();
        User user = (User) request.getSession().getAttribute(Constant.SESSION_USER_KEY);
        if (user.getEmployeeid() == null) {
            result.setError(421, "employee cannot be null");
            return result;
        }
        StringBuffer sb = this.getRequestStr(request);

        PublishMsgPojo publishMsgPojo = JSONObject.parseObject(sb.toString(), PublishMsgPojo.class);

        NewMsgPojo newMsgPojo = publishMsgPojo.getMes();

        // 发送消息至小P
        if (newMsgPojo.getPlatform() == 4) {
            return sendMsgToXiaoP(result, newMsgPojo);
        }

        SystemMessageDto systemMsgDto = new SystemMessageDto();
        systemMsgDto.setMesId(newMsgPojo.getMesId());
        systemMsgDto.setCreateId(user.getEmployeeid());
        systemMsgDto.setMesTypeId(newMsgPojo.getMesTypeId());
        systemMsgDto.setPlatform(newMsgPojo.getPlatform());
        systemMsgDto.setTitle(newMsgPojo.getTitle());
        systemMsgDto.setContent(newMsgPojo.getContent());
        systemMsgDto.setUrl(newMsgPojo.getUrl());
        systemMsgDto.setPic(newMsgPojo.getPic());
        // convert ids to employeeids
        if (newMsgPojo.getReceivers() != null && !newMsgPojo.getReceivers().equals("")) {
            systemMsgDto.setReceivers(newMsgPojo.getReceivers());
            // systemMsgDto.setReceivers(this.converidsToEmployeIds(newMsgPojo.getReceivers()).toString());
        }
        if (newMsgPojo.getStartDate() != null && !newMsgPojo.getStartDate().trim().equals("")) {
            systemMsgDto.setStartDate(Date.valueOf(newMsgPojo.getStartDate()));
        }
        if (newMsgPojo.getEndDate() != null && !newMsgPojo.getEndDate().trim().equals("")) {
            systemMsgDto.setEndDate(Date.valueOf(newMsgPojo.getEndDate()));
        }

        messageService.addSysMsg(systemMsgDto);
        return result;
    }

    /**
     * @description 获取消息类型关系
     * @return
     */
    @RequestMapping(value = "/getMsgTypeRelation.do")
    @ResponseBody
    public Result getMessageTypeRelation() {
        Result result = new Result();
        result.setData(messageTypeService.getMessageTypeRelation());
        return result;
    }

    /**
     * @description 获取个人中心订阅消息类型
     * @param request
     * @return
     */
    @RequestMapping(value = "/getSubscribeMsgType.do")
    @ResponseBody
    public Result getSubscribeMsgType(HttpServletRequest request) {
        Result result = new Result();
        User user = (User) request.getSession().getAttribute(Constant.SESSION_USER_KEY);
        if (user.getEmployeeid() == null) {
            result.setError(421, "employee cannot be null");
            return result;
        }
        result.setData(messageTypeService.getSubscribeMessageType(user.getEmployeeid()));
        return result;
    }

    /**
     * @description 录入简单消息
     * @param request
     * @return
     */
    @RequestMapping(value = "/addBriefMsg.do")
    @ResponseBody
    public Result addBriefMsg(HttpServletRequest request) {
        Result result = new Result();
        StringBuffer sb = this.getRequestStr(request);
        BriefMessageDto briefMessageDto = JSONObject.parseObject(sb.toString(), BriefMessageDto.class);
        int count = messageTypeService.checkBriefMsg(briefMessageDto.getMesTypeId());
        if (count > 0) {
            messageTypeService.addBriefMsg(briefMessageDto);
        } else {
            result.setErrorMsg("消息类型错误，请检查");
        }

        return result;
    }

    /**
     * @description 消息撤回
     * @param request
     * @return
     */
    @RequestMapping(value = "/deleteMsg.do")
    @ResponseBody
    public Result deleteMsg(HttpServletRequest request) {
        Result result = new Result();
        StringBuffer sb = this.getRequestStr(request);
        Long mesId = JSONObject.parseObject(sb.toString(), RecallMsgPojo.class).getMesId();
        result.setData(messageService.recallMsg(mesId));

        return result;
    }

    private StringBuffer getRequestStr(HttpServletRequest request) {
        StringBuffer sb = new StringBuffer();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(request.getInputStream(), "utf-8"));
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                    br = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb;
    }

    /**
     * @description 将dfs上的下载图片地址转换为二进制字节数组
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/getPhoto.do")
    public void getTempPhoto(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            String dfsId = request.getParameter("filePath");
            String fileName = request.getParameter("fileName");
            if (dfsId == null || fileName == null) {
                throw new Exception("pic链接非法");
            }
            System.out.println(dfsId);
            System.out.println(fileName);
            AgentUserPhoto userPhoto = this.getTempAgentUserPhoto(dfsId);

            // 提取图片类型
            response.setContentType("image/" + fileName.substring(fileName.length() - 3));
            OutputStream out = response.getOutputStream();
            if (userPhoto != null && userPhoto.getData() != null) {
            	out.write(userPhoto.getData());
            }
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private AgentUserPhoto getTempAgentUserPhoto(String dfsId) {

        AgentUserPhoto userPhoto = new AgentUserPhoto();
        
        InputStream inputStream;
        byte[] data = null;
		try {
			inputStream = DFSUtils.readFromDFS(dfsId);
			
			 data =IOUtils.toByteArray(inputStream) ;
		} catch (Exception e) {
			e.printStackTrace();
		} 
        userPhoto.setData(data);

        return userPhoto;
    }
    
    @RequestMapping(value = "sendToXiaoP.do")
    @ResponseBody
    public Result sentToXiaoP() {
    	Result result = new Result();
    	List<XiaoPMessage> xiaoPMessages = new ArrayList<XiaoPMessage>();
    	XiaoPMessage x1 = new XiaoPMessage();
    	x1.setReceivers("linxionghui");
    	x1.setType("0");
    	x1.setContent("[bizwork]任务提醒:您有未完成的任务");
    	xiaoPMessages.add(x1);
    	try {
			xiaoPMessageTService.sendMessageToXiaoP(xiaoPMessages);
		} catch (ApiTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return result;
    }

    // @RequestMapping(value = "/getEidsByGroupIds")
    // @ResponseBody
    // public String getEmployeeIdsByGroupIds(HttpServletRequest request) {
    // List<Integer> groupIds = JSONArray.parseArray(
    // request.getParameter("groupIds"), Integer.class);
    // return userService.getEmployeeIdsByGroupIds(groupIds).toString();
    // }

    @RequestMapping(value = "uploadImageToCDN.do", method = RequestMethod.POST)
    @ResponseBody
    public Result uploadImageToCDN(HttpServletRequest request) {
        Result result = new Result();
        MultipartHttpServletRequest mutipartHttpServletRequest = (MultipartHttpServletRequest) request;
        // 获得文件：
        MultipartFile file = mutipartHttpServletRequest.getFile("file");

        if (file == null || file.isEmpty()) {
            result.setErrorMsg("上传文件为空");
            return result;
        }
        try {
            // 将MultipartFile转化为File
            CommonsMultipartFile cf = (CommonsMultipartFile) file;
            DiskFileItem fi = (DiskFileItem) cf.getFileItem();
            File picFile = fi.getStoreLocation();
            String url = messageService.uploadFileToCDN(picFile);
            if (StringUtils.isNotEmpty(url)) {
                result.setData(url);
            } else {
                result.setErrorMsg("上传文件失败");
            }
        } catch (Exception e) {
            result.setErrorMsg("上传文件出错");
        }
        return result;
    }

    private Result sendMsgToXiaoP(Result result, NewMsgPojo newMsgPojo) throws Exception {
        // 想要推送消息至小P
        // 获取到想要发送的所有人的employeeId，现在查询数据库，得到所有的username作为to的内容
        List<MsgReceiver> mrs = messageService.convertReceiverStrToList(newMsgPojo.getReceivers(),
                newMsgPojo.getMesId());
        List<String> usernames = messageService.getUserNameByEmployeeIds(mrs);
        if (usernames == null || usernames.size() == 0) {
            result.setErrorMsg("发送人不能为空");
            return result;
        }
        String to = "";// 发送的对象
        for (String s : usernames) {
            to += s + ",";// 最后多剩下一个,不要紧
        }
        String content = new String(newMsgPojo.getContent().getBytes(), "iso8859-1");
        String ts = messageService.getTS();// 时间戳
        String token = messageService.getToken(pubid, APIToken, ts);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("pubid", pubid);
        params.put("ts", ts);
        params.put("token", token);
        params.put("to", to);// 前端带过来
        params.put("content", content);// 前端带过来

        if (newMsgPojo.getpType() == 0) {
            // 给关注者推送普通文本
            if (to == null || "".equals(to) || content == null || "".equals(content)) {
                result.setErrorMsg("参数发送人或者内容不能为空");
                return result;
            } else {
                String response = messageService.sendPostRequest(sendURL, params);
                result.setData(response);
            }
        } else if (newMsgPojo.getpType() == 1) {
            // 给关注者推送图文消息
            String title = new String(newMsgPojo.getTitle().getBytes(), "iso8859-1");
            String summary = new String(newMsgPojo.getSummary().getBytes(), "iso8859-1");
            String pic = newMsgPojo.getPic();

            params.put("tp", "1");
            params.put("title", title);// 前端带过来
            params.put("summary", summary);// 前端带过来
            params.put("cover", pic);// CDN服务器返回这张图片的URL
            if (to == null || "".equals(to) || content == null || "".equals(content) || title == null
                    || "".equals(title) || summary == null || "".equals(summary) || pic == null || "".equals(pic)) {
                result.setErrorMsg("推送为图文的时候有参数为空");
                return result;
            } else {
                String response = messageService.sendPostRequest(sendURL, params);
                result.setData(response);
            }
        } else {
            result.setErrorMsg("参数错误");
            return result;
        }
        return result;
    }
}

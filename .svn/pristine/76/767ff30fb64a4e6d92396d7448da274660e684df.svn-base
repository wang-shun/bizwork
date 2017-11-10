package com.sogou.bizwork.api.xiaop.msg.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.sogou.bizwork.api.exception.ApiTException;
import com.sogou.bizwork.api.message.MessageResult;
import com.sogou.bizwork.api.xiaop.message.XiaoPMessage;
import com.sogou.bizwork.api.xiaop.message.service.XiaoPMessageTService;
import com.sogou.bizwork.cas.utils.StringUtils;
import com.sogou.bizwork.util.MessageUtils;
import com.sogou.bizwork.util.XiaoPMessageUtils;

@Service("xiaoPMessageTService")
@Component
public class XiaoPMessageTServiceImpl implements XiaoPMessageTService.Iface {

	private static final Logger logger = LoggerFactory.getLogger(XiaoPMessageTService.Iface.class);

	@Value("${pubid}")
	private String pubid;// 服务号ID
	@Value("${APIToken}")
	private String APIToken;// 服务号的APIToken
	@Value("${sendURL}")
	private String sendURL;// 发送接口url

	@Override
	public MessageResult sendMessageToXiaoP(List<XiaoPMessage> xiaoPMessages) throws ApiTException, TException {
		MessageResult result = new MessageResult();
		if (CollectionUtils.isEmpty(xiaoPMessages)) {
			result.setErrorMsg( "message sended to xiao p cannot be null!");
			result.setErrorCode(421);
			return result;
		}
		// List<String> encodings = new ArrayList<String>();
		// encodings.add("utf-8");
		// encodings.add("GBK");
		// encodings.add("utf8");
		// encodings.add("iso-8859-1");
		// encodings.add("GB2312");
		// encodings.add("Unicode");
		// encodings.add("GB18030");
		// encodings.add("ASCII");
		// for (XiaoPMessage xpm : xiaoPMessages) {
		// if (xpm == null || StringUtils.isBlank(xpm.getContent()) ||
		// StringUtils.isBlank(xpm.getReceivers())) {
		// continue;
		// }
		// String ts = XiaoPMessageUtils.getTimeStamp();
		// Map<String, Object> params = new HashMap<String, Object>();
		// String pubid = "bizwork";
		// String APIToken = "e61a4232183c372ff12d609b6ddb9b89";
		// String sendURL =
		// "https://puboa.sogou-inc.com/moa/sylla/mapi/pns/send";
		//
		// params.put("pubid", pubid);
		// params.put("token", XiaoPMessageUtils.getToken(pubid, APIToken, ts));
		// params.put("ts", ts);
		// params.put("to", xpm.getReceivers());
		// params.put("tp", xpm.getType());
		// for (String encoding : encodings) {
		// try {
		// // String characterSet = "GB2312";
		// params.put("content", encoding + "_1" + xpm.getContent());
		//
		// if (xpm.getType() == null || xpm.getType().equals("0")) {
		// if (StringUtils.isNotBlank(xpm.getTitle())) {
		// params.put("title", new String(xpm.getTitle().getBytes(), encoding));
		// }
		// } else if (xpm.getType().equals("1")) {
		// if (StringUtils.isBlank(xpm.getTitle()))
		// continue;
		// params.put("title", xpm.getTitle());
		// if (StringUtils.isNotBlank(xpm.getSummary())) {
		// params.put("summary", new String(xpm.getSummary().getBytes(),
		// encoding));
		// }
		// if (StringUtils.isNotBlank(xpm.getCover())) {
		// params.put("cover", new String(xpm.getCover().getBytes(), encoding));
		// }
		// } else if (xpm.getType().equals("2")) {
		// if (StringUtils.isBlank(xpm.getTitle()))
		// continue;
		// params.put("title", new String(xpm.getTitle().getBytes(), encoding));
		// } else if (xpm.getType().equals("3")) {
		// if (StringUtils.isBlank(xpm.getTitle()))
		// continue;
		// params.put("title", new String(xpm.getTitle().getBytes(), encoding));
		// if (StringUtils.isNotBlank(xpm.getUrl())) {
		// params.put("url", new String(xpm.getUrl().getBytes(), encoding));
		// }
		// }
		// } catch (Exception e) {
		// // TODO: handle exception
		// logger.error(e.getMessage());
		// MessageUtils.printStackTrace(e);
		// }
		// XiaoPMessageUtils.sendPostRequest(sendURL, params);
		// }
		// }

		for (XiaoPMessage xpm : xiaoPMessages) {
			if (xpm == null || StringUtils.isBlank(xpm.getContent()) || StringUtils.isBlank(xpm.getReceivers())) {
				continue;
			}
			String ts = XiaoPMessageUtils.getTimeStamp();
			Map<String, Object> params = new HashMap<String, Object>();
			String pubid = "bizwork";
			String APIToken = "e61a4232183c372ff12d609b6ddb9b89";
			String sendURL = "https://puboa.sogou-inc.com/moa/sylla/mapi/pns/send";

			params.put("pubid", pubid);
			params.put("token", XiaoPMessageUtils.getToken(pubid, APIToken, ts));
			params.put("ts", ts);
			params.put("to", xpm.getReceivers());
			params.put("tp", xpm.getType());
			try {
				// String characterSet = "GB2312";

				params.put("content", xpm.getContent());

				if (xpm.getType() == null || xpm.getType().equals("0")) {
					if (StringUtils.isNotBlank(xpm.getTitle())) {
						params.put("title", xpm.getTitle());
					}
				} else if (xpm.getType().equals("1")) {
					if (StringUtils.isBlank(xpm.getTitle()))
						continue;
					params.put("title", xpm.getTitle());
					if (StringUtils.isNotBlank(xpm.getSummary())) {
						params.put("summary", xpm.getSummary());
					}
					if (StringUtils.isNotBlank(xpm.getCover())) {
						params.put("cover", xpm.getCover());
					}
				} else if (xpm.getType().equals("2")) {
					if (StringUtils.isBlank(xpm.getTitle()))
						continue;
					params.put("title", xpm.getTitle());
				} else if (xpm.getType().equals("3")) {
					if (StringUtils.isBlank(xpm.getTitle()))
						continue;
					params.put("title", xpm.getTitle());
					if (StringUtils.isNotBlank(xpm.getUrl())) {
						params.put("url", xpm.getUrl());
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
				logger.error(e.getMessage());
				MessageUtils.printStackTrace(e);
			}
			XiaoPMessageUtils.sendPostRequest(sendURL, params, "utf-8");
		}
		return result;
	}

}

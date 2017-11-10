include "../result/MessageResult.thrift"
include "../type/XiaoPMessage.thrift"
include "../../common/exception/ApiExceptions.thrift"
namespace java com.sogou.bizwork.api.xiaop.message.service

service XiaoPMessageTService {
	MessageResult.MessageResult sendMessageToXiaoP (1:list<XiaoPMessage.XiaoPMessage> xiaoPMessages) throws (1:ApiExceptions.ApiTException e);
}
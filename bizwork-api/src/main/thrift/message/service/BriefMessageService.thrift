include "../result/MessageResult.thrift"
include "../type/BriefTypeMessages.thrift"
include "../../common/exception/ApiExceptions.thrift"
namespace java com.sogou.bizwork.api.message.service

//bizwork消息Service

service BizworkMessageTService {
	
	//新增Bizwork消息
	MessageResult.MessageResult insertBriefMessage(1:list<BriefTypeMessages.BriefTypeMessage> briefTypeMessages) throws (1:ApiExceptions.ApiTException e),
	
}


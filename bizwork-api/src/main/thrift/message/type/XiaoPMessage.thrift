namespace java com.sogou.bizwork.api.xiaop.message

struct XiaoPMessage{
1: required string receivers,
2: required string content,
3: required string type,
4: optional string title,
5: optional string summary,
6: optional string cover,
7: optional string url
}

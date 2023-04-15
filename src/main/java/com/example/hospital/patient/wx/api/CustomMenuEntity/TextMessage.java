package com.example.hospital.patient.wx.api.CustomMenuEntity;
//文本消息实体类
public class TextMessage {
    private String ToUserName;
    private String FromUserName;
    private Long CreateTime;
    private String MsgType;
    private Integer FuncFlag;
    private String Content;


    public String getToUserName() {
        return ToUserName;
    }

    public void setToUserName(String toUserName) {
        ToUserName = toUserName;
    }

    public String getFromUserName() {
        return FromUserName;
    }

    public void setFromUserName(String fromUserName) {
        FromUserName = fromUserName;
    }

    public Long getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(Long createTime) {
        CreateTime = createTime;
    }

    public String getMsgType() {
        return MsgType;
    }

    public void setMsgType(String msgType) {
        MsgType = msgType;
    }

    public Integer getFuncFlag() {
        return FuncFlag;
    }

    public void setFuncFlag(Integer funcFlag) {
        FuncFlag = funcFlag;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }
}

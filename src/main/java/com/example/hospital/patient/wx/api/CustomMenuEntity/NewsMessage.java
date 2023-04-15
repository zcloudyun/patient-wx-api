package com.example.hospital.patient.wx.api.CustomMenuEntity;
import java.util.List;
//图文消息实体类
public class NewsMessage {
    private String ToUserName;
    private String FromUserName;
    private Long CreateTime;
    private String MsgType;
    private Integer FuncFlag;
    private Integer ArticleCount;
    private List<Article> Articles;


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

    public Integer getArticleCount() {
        return ArticleCount;
    }

    public void setArticleCount(Integer articleCount) {
        ArticleCount = articleCount;
    }

    public List<Article> getArticles() {
        return Articles;
    }

    public void setArticles(List<Article> articles) {
        Articles = articles;
    }
}

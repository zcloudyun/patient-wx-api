package com.example.hospital.patient.wx.api.service.impl;
import com.example.hospital.patient.wx.api.CustomMenuEntity.Article;
import com.example.hospital.patient.wx.api.CustomMenuEntity.NewsMessage;
import com.example.hospital.patient.wx.api.CustomMenuEntity.TextMessage;
import com.example.hospital.patient.wx.api.CustomMenuUtil.WeixinMessageUtil;
import com.example.hospital.patient.wx.api.service.CoreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 处理微信端发来的POST请求，进行事件响应
 * wz,2020.1.13
 */
@Service("coreService")
public class CoreServiceImpl implements CoreService {
    private static Logger log = LoggerFactory.getLogger(CoreServiceImpl.class);

    @Autowired
    private WeixinMessageUtil weixinMessageUtil;

    @Override
    public String processRequest(HttpServletRequest request) {
        String respMessage = null;
        try {
            // 默认返回的文本消息内容
            String respContent = "请求处理异常，请稍候尝试！";
            // xml请求解析
            Map<String, String> requestMap = weixinMessageUtil.parseXml(request);
            // 发送方帐号（open_id）
            String fromUserName = requestMap.get("FromUserName");
            log.info("open_id:"+fromUserName);
            // 公众帐号
            String toUserName = requestMap.get("ToUserName");
            // 消息类型
            String msgType = requestMap.get("MsgType");
            // 回复文本消息
            TextMessage textMessage = new TextMessage();
            textMessage.setToUserName(fromUserName);
            textMessage.setFromUserName(toUserName);
            textMessage.setCreateTime(new Date().getTime());
            textMessage.setMsgType(weixinMessageUtil.RESP_MESSAGE_TYPE_TEXT);
            textMessage.setFuncFlag(0);


            // 创建图文消息
            NewsMessage newsMessage = new NewsMessage();
            newsMessage.setToUserName(fromUserName);
            newsMessage.setFromUserName(toUserName);
            newsMessage.setCreateTime(new Date().getTime());
            newsMessage.setMsgType(weixinMessageUtil.RESP_MESSAGE_TYPE_NEWS);
            newsMessage.setFuncFlag(0);

            List<Article> articleList = new ArrayList<Article>();


            //点击菜单id
            String EventKey =requestMap.get("EventKey");
            // 接收文本消息内容
            String content = requestMap.get("Content");
            // 自动回复文本消息
            if (msgType.equals(weixinMessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
                //如果用户发送表情，则回复同样表情。
                if (isQqFace(content)) {
                    respContent = content;
                    textMessage.setContent(respContent);
                    // 将文本消息对象转换成xml字符串
                    respMessage = weixinMessageUtil.textMessageToXml(textMessage);
                }
              else {
                    //回复固定消息
                    switch (content) {
                        case "1": {
                            StringBuffer buffer = new StringBuffer();
                            buffer.append("您好，我是小嘉创，请回复数字选择服务:").append("\n\n");
                            buffer.append("11可查看测试单图文").append("\n");
                            buffer.append("12可测试多图文发送").append("\n");
                            buffer.append("13可测试网址").append("\n");
                            buffer.append("或者您可以尝试发送表情").append("\n\n");
                            buffer.append("回复“1”显示此帮助菜单").append("\n");
                            respContent = String.valueOf(buffer);
                            textMessage.setContent(respContent);
                            respMessage = weixinMessageUtil.textMessageToXml(textMessage);
                            break;
                        }
                        case "11": {
                            //测试单图文回复
                            Article article = new Article();
                            article.setTitle("微信公众帐号开发教程Java版");
                            // 图文消息中可以使用QQ表情、符号表情
                            article.setDescription("这是测试有没有换行\n\n如果有空行就代表换行成功\n\n点击图文可以跳转到百度首页");
                            // 将图片置为空
                            article.setPicUrl("http://www.sinaimg.cn/dy/slidenews/31_img/2016_38/28380_733695_698372.jpg");
                            article.setUrl("http://www.baidu.com");
                            articleList.add(article);
                            newsMessage.setArticleCount(articleList.size());
                            newsMessage.setArticles(articleList);
                            respMessage = weixinMessageUtil.newsMessageToXml(newsMessage);
                            break;
                        }
                        case "12": {
                            //多图文发送
                            Article article1 = new Article();
                            article1.setTitle("紧急通知，不要捡这种钱！湛江都已经传疯了！\n");
                            article1.setDescription("");
                            article1.setPicUrl("http://www.sinaimg.cn/dy/slidenews/31_img/2016_38/28380_733695_698372.jpg");
                            article1.setUrl("http://mp.weixin.qq.com/s?__biz=MjM5Njc2OTI4NQ==&mid=2650924309&idx=1&sn=8bb6ae54d6396c1faa9182a96f30b225&chksm=bd117e7f8a66f769dc886d38ca2d4e4e675c55e6a5e01e768b383f5859e09384e485da7bed98&scene=4#wechat_redirect");

                            Article article2 = new Article();
                            article2.setTitle("湛江谁有这种女儿，请给我来一打！");
                            article2.setDescription("");
                            article2.setPicUrl("http://www.sinaimg.cn/dy/slidenews/31_img/2016_38/28380_733695_698372.jpg");
                            article2.setUrl("http://mp.weixin.qq.com/s?__biz=MjM5Njc2OTI4NQ==&mid=2650924309&idx=2&sn=d7ffc840c7e6d91b0a1c886b16797ee9&chksm=bd117e7f8a66f7698d094c2771a1114853b97dab9c172897c3f9f982eacb6619fba5e6675ea3&scene=4#wechat_redirect");

                            Article article3 = new Article();
                            article3.setTitle("以上图片我就随意放了");
                            article3.setDescription("");
                            article3.setPicUrl("http://www.sinaimg.cn/dy/slidenews/31_img/2016_38/28380_733695_698372.jpg");
                            article3.setUrl("http://mp.weixin.qq.com/s?__biz=MjM5Njc2OTI4NQ==&mid=2650924309&idx=3&sn=63e13fe558ff0d564c0da313b7bdfce0&chksm=bd117e7f8a66f7693a26853dc65c3e9ef9495235ef6ed6c7796f1b63abf1df599aaf9b33aafa&scene=4#wechat_redirect");

                            articleList.add(article1);
                            articleList.add(article2);
                            articleList.add(article3);
                            newsMessage.setArticleCount(articleList.size());
                            newsMessage.setArticles(articleList);
                            respMessage = weixinMessageUtil.newsMessageToXml(newsMessage);
                            break;
                        }
                        case "13": {
                            //测试网址回复
                            respContent = "<a href=\"http://www.baidu.com\">百度主页</a>";
                            textMessage.setContent(respContent);
                            // 将文本消息对象转换成xml字符串
                            respMessage = weixinMessageUtil.textMessageToXml(textMessage);
                            break;
                        }
                        default: {
                            respContent = "（这是里面的）很抱歉，现在小8暂时无法提供此功能给您使用。\n\n回复“1”显示帮助信息";
                            textMessage.setContent(respContent);
                            // 将文本消息对象转换成xml字符串
                            respMessage = weixinMessageUtil.textMessageToXml(textMessage);
                        }
                    }
                }
            }
            // 图片消息
            else if (msgType.equals(weixinMessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {
                respContent = "您发送的是图片消息！";
                textMessage.setContent(respContent);
                // 将文本消息对象转换成xml字符串
                respMessage = weixinMessageUtil.textMessageToXml(textMessage);
            }
            // 地理位置消息
            else if (msgType.equals(weixinMessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {
                respContent = "您发送的是地理位置消息！";
                textMessage.setContent(respContent);
                // 将文本消息对象转换成xml字符串
                respMessage = weixinMessageUtil.textMessageToXml(textMessage);
            }
            // 链接消息
            else if (msgType.equals(weixinMessageUtil.REQ_MESSAGE_TYPE_LINK)) {
                respContent = "您发送的是链接消息！";textMessage.setContent(respContent);
                // 将文本消息对象转换成xml字符串
                respMessage = weixinMessageUtil.textMessageToXml(textMessage);
            }
            // 音频消息
            else if (msgType.equals(weixinMessageUtil.REQ_MESSAGE_TYPE_VOICE)) {
                respContent = "您发送的是音频消息！";
                textMessage.setContent(respContent);
                // 将文本消息对象转换成xml字符串
                respMessage = weixinMessageUtil.textMessageToXml(textMessage);
            }
            // 事件推送
            else if (msgType.equals(weixinMessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
                // 事件类型
                String eventType =requestMap.get("Event");
                // 自定义菜单点击事件
                if (eventType.equals(weixinMessageUtil.EVENT_TYPE_CLICK)) {
                    switch (EventKey){
                        case "11":{
                            Article article = new Article();
                            article.setTitle("嘉创软件平台！");
                            // 图文消息中可以使用QQ表情、符号表情
                            article.setDescription("点击前往个人中心");
                            // 将图片置为空
                            article.setPicUrl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1578996280210&di=748792df7f36755189d8924fd2d0ca1d&imgtype=0&src=http%3A%2F%2Fimg1.gtimg.com%2Frushidao%2Fpics%2Fhv1%2F20%2F108%2F1744%2F113431160.jpg");
                            article.setUrl("http://www.xiao001.fun/saas/webcat/index.html?url=11&openId="+fromUserName);
                            articleList.add(article);
                            newsMessage.setArticleCount(articleList.size());
                            newsMessage.setArticles(articleList);
                            respMessage = weixinMessageUtil.newsMessageToXml(newsMessage);
                            respContent="<a href=\"http://192.168.0.106:8080/saas/webcat?url=11&openId="+fromUserName+"\">"+"点击前往个人中心"+"</a>";
                            break;
                        }
                        case "12":{
                            Article article = new Article();
                            article.setTitle("嘉创软件平台！");
                            // 图文消息中可以使用QQ表情、符号表情
                            article.setDescription("点击前往支部信息");
                            // 将图片置为空
                            article.setPicUrl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1578996280210&di=748792df7f36755189d8924fd2d0ca1d&imgtype=0&src=http%3A%2F%2Fimg1.gtimg.com%2Frushidao%2Fpics%2Fhv1%2F20%2F108%2F1744%2F113431160.jpg");
                            article.setUrl("http://www.xiao001.fun/saas/webcat/index.html?url=12&openId="+fromUserName);
                            articleList.add(article);
                            newsMessage.setArticleCount(articleList.size());
                            newsMessage.setArticles(articleList);
                            respMessage = weixinMessageUtil.newsMessageToXml(newsMessage);
                            respContent="<a href=\"http://192.168.0.106:8080/saas/webcat?url=12&openId="+fromUserName+"\">"+"点击前往支部信息"+"</a>";
                            break;
                        }
                        case "13":{
                            Article article = new Article();
                            article.setTitle("嘉创软件平台！");
                            // 图文消息中可以使用QQ表情、符号表情
                            article.setDescription("点击前往党费缴纳");
                            // 将图片置为空
                            article.setPicUrl("");
                            article.setUrl("");
                            articleList.add(article);
                            newsMessage.setArticleCount(articleList.size());
                            newsMessage.setArticles(articleList);
                            respContent = "党费缴纳";
                            break;
                        }
                        case "14":{
                            Article article = new Article();
                            article.setTitle("嘉创软件平台！");
                            // 图文消息中可以使用QQ表情、符号表情
                            article.setDescription("点击前往材料报送");
                            // 将图片置为空
                            article.setPicUrl("");
                            article.setUrl("");
                            articleList.add(article);
                            newsMessage.setArticleCount(articleList.size());
                            newsMessage.setArticles(articleList);
                            respContent = "材料报送";
                            break;
                        }
                        case "15":{
                            Article article = new Article();
                            article.setTitle("嘉创软件平台！");
                            // 图文消息中可以使用QQ表情、符号表情
                            article.setDescription("点击前往入党详情");
                            // 将图片置为空
                            article.setPicUrl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1578996280210&di=748792df7f36755189d8924fd2d0ca1d&imgtype=0&src=http%3A%2F%2Fimg1.gtimg.com%2Frushidao%2Fpics%2Fhv1%2F20%2F108%2F1744%2F113431160.jpg");
                            article.setUrl("http://www.xiao001.fun/saas/webcat/index.html?url=15&openId="+fromUserName);
                            articleList.add(article);
                            newsMessage.setArticleCount(articleList.size());
                            newsMessage.setArticles(articleList);
                            respMessage = weixinMessageUtil.newsMessageToXml(newsMessage);
                            respContent="<a href=\"http://192.168.0.106:8080/saas/webcat?url=15&openId="+fromUserName+"\">"+"点击前往入党详情"+"</a>";
                            break;
                        }
                        case "22":{
                            Article article = new Article();
                            article.setTitle("嘉创软件平台！");
                            // 图文消息中可以使用QQ表情、符号表情
                            article.setDescription("点击前往会议活动");
                            // 将图片置为空
                            article.setPicUrl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1578996280210&di=748792df7f36755189d8924fd2d0ca1d&imgtype=0&src=http%3A%2F%2Fimg1.gtimg.com%2Frushidao%2Fpics%2Fhv1%2F20%2F108%2F1744%2F113431160.jpg");
                            article.setUrl("http://www.xiao001.fun/saas/webcat/index.html?url=22&openId="+fromUserName);
                            articleList.add(article);
                            newsMessage.setArticleCount(articleList.size());
                            newsMessage.setArticles(articleList);
                            respMessage = weixinMessageUtil.newsMessageToXml(newsMessage);
                            respContent="<a href=\"http://192.168.0.106:8080/saas/webcat?url=22&openId="+fromUserName+"\">"+"点击前往会议活动"+"</a>";
                            break;
                        }
                        /**
                         * 这里在自定义菜单时，改为view事件类型，直接跳转
                         */
                        case "23":{
                            Article article = new Article();
                            article.setTitle("嘉创软件平台！");
                            // 图文消息中可以使用QQ表情、符号表情
                            article.setDescription("点击前往百度一下");
                            // 将图片置为空
                            article.setPicUrl("");
                            article.setUrl("http://www.baidu.com");
                            articleList.add(article);
                            newsMessage.setArticleCount(articleList.size());
                            newsMessage.setArticles(articleList);
                            respContent = "<a href=\"http://www.baidu.com\">百度一下</a>";
                            break;
                        }
                        default:{
                            log.error("开发者反馈：EventKey值没找到，它是:"+EventKey);
                            Article article = new Article();
                            article.setTitle("嘉创软件平台！");
                            // 图文消息中可以使用QQ表情、符号表情
                            article.setDescription("很抱歉，此按键功能正在升级无法使用");
                            // 将图片置为空
                            article.setPicUrl("");
                            article.setUrl("");
                            articleList.add(article);
                            newsMessage.setArticleCount(articleList.size());
                            newsMessage.setArticles(articleList);
                            respContent= "很抱歉，此按键功能正在升级无法使用";
                        }
                    }
                    textMessage.setContent(respContent);
                    // 将文本消息对象转换成xml字符串
                    respMessage = weixinMessageUtil.textMessageToXml(textMessage);
                    /**
                     * 对图文消息对象进行转换成xml字符串
                     */
                    respMessage = weixinMessageUtil.newsMessageToXml(newsMessage);
                }
                else if(eventType.equals(weixinMessageUtil.EVENT_TYPE_VIEW)){
                    // 对于点击菜单转网页暂时不做推送
                }
                // 订阅
                else if (eventType.equals(weixinMessageUtil.EVENT_TYPE_SUBSCRIBE)) {
                    //测试单图文回复
                    Article article = new Article();
                    article.setTitle("谢谢您的关注！");
                    // 图文消息中可以使用QQ表情、符号表情
                    article.setDescription("点击图文可以跳转到百度首页");
                    // 将图片置为空
                    article.setPicUrl("http://www.sinaimg.cn/dy/slidenews/31_img/2016_38/28380_733695_698372.jpg");
                    article.setUrl("http://www.baidu.com");
                    articleList.add(article);
                    newsMessage.setArticleCount(articleList.size());
                    newsMessage.setArticles(articleList);
                    respMessage = weixinMessageUtil.newsMessageToXml(newsMessage);
                    /**
                     * 我新加的
                     */
                    respContent="谢谢您的关注！";
                    textMessage.setContent(respContent);
                    respMessage=weixinMessageUtil.textMessageToXml(textMessage);
                }
                else if(eventType.equals(weixinMessageUtil.EVENT_TYPE_SCAN)){
                    //测试单图文回复
                    Article article = new Article();
                    article.setTitle("这是已关注用户扫描二维码弹到的界面");
                    // 图文消息中可以使用QQ表情、符号表情
                    article.setDescription("点击图文可以跳转到百度首页");
                    // 将图片置为空
                    article.setPicUrl("http://www.sinaimg.cn/dy/slidenews/31_img/2016_38/28380_733695_698372.jpg");
                    article.setUrl("http://www.baidu.com");
                    articleList.add(article);
                    newsMessage.setArticleCount(articleList.size());
                    newsMessage.setArticles(articleList);
                    respMessage = weixinMessageUtil.newsMessageToXml(newsMessage);
                }
                // 取消订阅
                else if (eventType.equals(weixinMessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
                    log.info("取消订阅");
                    // TODO 取消订阅后用户再收不到公众号发送的消息，因此不需要回复消息
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("respMessage:"+respMessage);
        return respMessage;
    }
    /**
     * 判断是否是QQ表情
     *
     * @param content
     * @return
     */
    public static boolean isQqFace(String content) {
        boolean result = false;
        // 判断QQ表情的正则表达式
        String qqfaceRegex = "/::\\)|/::~|/::B|/::\\||/:8-\\)|/::<|/::$|/::X|/::Z|/::'\\(|/::-\\||/::@|/::P|/::D|/::O|/::\\(|/::\\+|/:--b|/::Q|/::T|/:,@P|/:,@-D|/::d|/:,@o|/::g|/:\\|-\\)|/::!|/::L|/::>|/::,@|/:,@f|/::-S|/:\\?|/:,@x|/:,@@|/::8|/:,@!|/:!!!|/:xx|/:bye|/:wipe|/:dig|/:handclap|/:&-\\(|/:B-\\)|/:<@|/:@>|/::-O|/:>-\\||/:P-\\(|/::'\\||/:X-\\)|/::\\*|/:@x|/:8\\*|/:pd|/:<W>|/:beer|/:basketb|/:oo|/:coffee|/:eat|/:pig|/:rose|/:fade|/:showlove|/:heart|/:break|/:cake|/:li|/:bome|/:kn|/:footb|/:ladybug|/:shit|/:moon|/:sun|/:gift|/:hug|/:strong|/:weak|/:share|/:v|/:@\\)|/:jj|/:@@|/:bad|/:lvu|/:no|/:ok|/:love|/:<L>|/:jump|/:shake|/:<O>|/:circle|/:kotow|/:turn|/:skip|/:oY|/:#-0|/:hiphot|/:kiss|/:<&|/:&>";
        Pattern p = Pattern.compile(qqfaceRegex);
        Matcher m = p.matcher(content);
        if (m.matches()) {
            result = true;
        }
        return result;
    }
}

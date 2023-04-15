package com.example.hospital.patient.wx.api.CustomMenuUtil;

import com.example.hospital.patient.wx.api.CustomMenuEntity.Article;
import com.example.hospital.patient.wx.api.CustomMenuEntity.NewsMessage;
import com.example.hospital.patient.wx.api.CustomMenuEntity.TextMessage;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//封装微信消息类型的工具类，（其种还有一个解析xml格式的工具类，用于事件响应，微信公众号接收的消息类型是xml方式）
// 消息类型分为：请求消息类型，返回消息类型，事件类型
@Component
public class WeixinMessageUtil {

    private static Logger log = LoggerFactory.getLogger(WeixinMessageUtil.class);
    /**
     * 请求消息类型：文本
     */
    public final String REQ_MESSAGE_TYPE_TEXT = "text";
    /**
     * 请求消息类型：图片
     */
    public final String REQ_MESSAGE_TYPE_IMAGE="image";
    /**
     * 请求消息类型：语音
     */
    public final String REQ_MESSAGE_TYPE_VOICE="voice";
    /**
     * 请求消息类型：视频
     */
    public final String REQ_MESSAGE_TYPE_VIDEO="video";
    /**
     * 请求消息类型：链接
     */
    public final String REQ_MESSAGE_TYPE_LINK = "link";
    /**
     * 请求消息类型：地理位置
     */
    public  final String REQ_MESSAGE_TYPE_LOCATION="location";
    /**
     * 请求消息类型：小视频
     */
    public final String REQ_MESSAGE_TYPE_SHORTVIDEO="shortvideo";
    /**
     *请求消息类型：事件推送
     */
    public final String REQ_MESSAGE_TYPE_EVENT = "event";
    /**
     * 返回消息类型：文本
     */
    public final String RESP_MESSAGE_TYPE_TEXT = "text";
    /**
     * 消息返回类型：图片
     */
    public final String RESP_MESSAGE_TYPE_IMAGE="image";
    /**
     * 消息返回类型:语音
     */
    public final String RESP_MESSAGE_TYPE_VOICE = "voice";
    /**
     * 消息返回类型：音乐
     */
    public final String RESP_MESSAGE_TYPE_MUSIC = "music";
    /**
     * 消息返回类型：图文
     */
    public final  String RESP_MESSAGE_TYPE_NEWS = "news";
    /**
     * 消息返回类型：视频
     */
    public final String RESP_MESSAGE_TYPE_VIDEO="video";
    /**
     * 事件类型:订阅
     */
    public final String EVENT_TYPE_SUBSCRIBE = "subscribe";
    /**
     * 事件类型：取消订阅
     */
    public final String EVENT_TYPE_UNSUBSCRIBE = "unsubscribe";
    /**
     * 事件类型：scan(关注用户扫描带参二维码)
     */
    public final String EVENT_TYPE_SCAN = "scan";
    /**
     * 事件类型：location(上报地理位置)
     */
    public final String EVENT_TYPE_LOCATION = "location";
    /**
     * 事件类型：CLICK(点击菜单拉取消息)
     */
    public final String EVENT_TYPE_CLICK ="CLICK";
    /**
     * 事件类型：VIEW(自定义菜单URl视图)
     */
    public final String EVENT_TYPE_VIEW ="VIEW";
    /**
     * 事件类型：TEMPLATESENDJOBFINISH(模板消息送达情况提醒)
     */
    public final String EVENT_TYPE_TEMPLATESENDJOBFINISH="TEMPLATESENDJOBFINISH";
    /**
     * @Description: 解析微信服务器发过来的xml格式的消息将其转换为map
     * @Parameters: WeixinMessageUtil
     * @Return: Map<String, String>
     * wz,2020.1.13
     */
    public Map<String, String> parseXml(HttpServletRequest request)throws Exception{
        // 将解析结果存储在HashMap中
        Map<String, String>map =new HashMap<String, String>();
        // 从request中得到输入流
        InputStream  inputStream=request.getInputStream();
        // 读取输入流
        SAXReader reader = new SAXReader();
        Document document = reader.read(inputStream);
        // 得到XML的根元素
        Element root = document.getRootElement();
        // 得到根元素的所有子节点
        @SuppressWarnings("unchecked")
        List<Element> elementList = root.elements();
        // 判断又没有子元素列表
        if (elementList.size()==0){
            map.put(root.getName(), root.getText());
        }else {
            for (Element e : elementList)
                map.put(e.getName(), e.getText());
        }
        // 释放资源
        inputStream.close();
        inputStream = null;
        log.info("---------xml转换为map-----:"+map);
        return map;
    }
    /**
     * @Description: 文本消息对象转换成xml
     * @param  textMessage
     * @date   2016-12-01
     * @return  xml
     */
    public String textMessageToXml(TextMessage textMessage) {
        xstream.alias("xml", textMessage.getClass());
        return xstream.toXML(textMessage);
    }
    /**
     * @Description: 图文消息对象转换成xml
     * @param  newsMessage
     * @date   2016-12-01
     * @return  xml
     */
    public String newsMessageToXml(NewsMessage newsMessage) {
        xstream.alias("xml", newsMessage.getClass());
        xstream.alias("item", new Article().getClass());
        return xstream.toXML(newsMessage);
    }
    /**
     * 对象到xml的处理
     * 扩展xstream，使其支持CDATA块
     */
    private XStream xstream = new XStream(new XppDriver() {
        @Override
        public HierarchicalStreamWriter createWriter(Writer out) {
            return new PrettyPrintWriter(out) {
                // 对所有xml节点的转换都增加CDATA标记
                boolean cdata = true;

                @Override
                @SuppressWarnings("rawtypes")
                public void startNode(String name, Class clazz) {
                    super.startNode(name, clazz);
                }
                @Override
                protected void writeText(QuickWriter writer, String text) {
                    if (cdata) {
                        writer.write("<![CDATA[");
                        writer.write(text);
                        writer.write("]]>");
                    } else {
                        writer.write(text);
                    }
                }
            };
        }
    });
}

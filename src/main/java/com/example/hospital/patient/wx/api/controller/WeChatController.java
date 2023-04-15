package com.example.hospital.patient.wx.api.controller;
import com.example.hospital.patient.wx.api.CustomMenuUtil.SignUtil;
import com.example.hospital.patient.wx.api.service.CoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 跟微信端进行连接的验证
 * 微信公众号的事件响应
 * wz,2020.1.13
 */
@RestController
@RequestMapping(value = "/api/wx")
public class WeChatController {

    @Autowired
    private CoreService coreService;
    /**
     * 处理微信服务器发来的get请求，进行签名的验证
     *
     * signature 微信端发来的签名
     * timestamp 微信端发来的时间戳
     * nonce     微信端发来的随机字符串
     * echostr   微信端发来的验证字符串
     */
    @RequestMapping(value = "/wechat",method = RequestMethod.GET,produces = "text/plain;charset=utf-8")
    public String validate(@RequestParam(value = "signature", required = false) String signature,
                           @RequestParam(value = "timestamp", required = false) String timestamp,
                           @RequestParam(value = "nonce", required = false) String nonce,
                           @RequestParam(value = "echostr",required=false) String echostr) {
        System.out.println("echostr:"+echostr);
        return SignUtil.checkSignature(signature, timestamp, nonce) ? echostr : null;
    }

    /**
     * 处理微信端发来的POST请求，进行事件响应
     * @param req
     * @return
     */
    @RequestMapping(value = "/wechat",method = RequestMethod.POST, produces = "application/xml; charset=UTF-8")
    public  String post(HttpServletRequest req){
        // 调用核心业务类接收消息、处理消息跟推送消息
        String respMessage = coreService.processRequest(req);
        return respMessage;
    }

}

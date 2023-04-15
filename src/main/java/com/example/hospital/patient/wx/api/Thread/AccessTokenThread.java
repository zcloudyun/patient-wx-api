package com.example.hospital.patient.wx.api.Thread;
import com.example.hospital.patient.wx.api.CustomMenuEntity.AccessToken;
import com.example.hospital.patient.wx.api.CustomMenuUtil.WeixinUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

//三线程类
//@EnableScheduling，在程序启动时就开启定时任务
@Component
public class AccessTokenThread {
    private static Logger log = LoggerFactory.getLogger(AccessTokenThread.class);

    // 第三方用户唯一凭证(微信公众号的appid )
    public static String appid = "wx9d10622612e17575";

    // 第三方用户唯一凭证密钥(微信公众号的appsecret )
    public static String appsecret = "43b41fcae69a262d01a939b2761d5fef";
    // 第三方用户唯一凭证
    public static AccessToken accessToken = null;

    @Scheduled(fixedDelay = 2*3600*1000)
    //7200秒执行一次
    public void gettoken(){
        accessToken= WeixinUtil.getAccessToken(appid,appsecret);
        if(null!=accessToken){
            log.info("获取成功，accessToken:"+accessToken.getToken());
        }else {
            log.error("获取token失败");
        }
    }
}

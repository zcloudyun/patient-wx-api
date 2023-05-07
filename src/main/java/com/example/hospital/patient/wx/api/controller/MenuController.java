package com.example.hospital.patient.wx.api.controller;
import com.example.hospital.patient.wx.api.CustomMenuEntity.Button;
import com.example.hospital.patient.wx.api.CustomMenuEntity.CommonButton;
import com.example.hospital.patient.wx.api.CustomMenuEntity.ComplexButton;
import com.example.hospital.patient.wx.api.CustomMenuEntity.Menu;
import com.example.hospital.patient.wx.api.Thread.AccessTokenThread;
import com.example.hospital.patient.wx.api.service.MenuService;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 对订阅号的菜单的操作
 * wz,2020.1.13
 * 单独的接口进行调用，或者执行main函数进行运行
 */
@RestController
@RequestMapping("/api/menu")

public class MenuController {
    @Autowired
    private MenuService menuService;

    private static Logger log = LoggerFactory.getLogger(MenuController.class);
    //查询全部菜单
    @RequestMapping(value = "/get",method = RequestMethod.GET)
    public String getMenu() {
        // 调用接口获取access_token
        String at = AccessTokenThread.accessToken.getToken();
        JSONObject jsonObject =null;
        if (at != null) {
            // 调用接口查询菜单
            jsonObject = menuService.getMenu(at);
            // 判断菜单创建结果
            return String.valueOf(jsonObject);
        }
        log.info("token为"+at);
        return "无数据";
    }

    //创建菜单
    @RequestMapping(value = "/create",method = RequestMethod.POST)
    public int createMenu() {
        // 调用接口获取access_token
        String at = AccessTokenThread.accessToken.getToken();
        int result=0;
        if (at != null) {

            // 调用接口创建菜单
            result = menuService.createMenu(getFirstMenu(),at);
            // 判断菜单创建结果
            if (0 == result) {
                log.info("菜单创建成功！");
            } else {
                log.info("菜单创建失败，错误码：" + result);
            }
        }
        return result ;
    }

    //删除菜单
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public int deleteMenu() {
        // 调用接口获取access_token
        String at = AccessTokenThread.accessToken.getToken();
        int result=0;
        if (at != null) {
            // 删除菜单
            result = menuService.deleteMenu(at);
            // 判断菜单删除结果
            if (0 == result) {
                log.info("菜单删除成功！");
            } else {
                log.info("菜单删除失败，错误码：" + result);
            }
        }
        return  result;
    }

    /**
     * 组装菜单数据
     *
     * @return
     */
    private static Menu getFirstMenu() {
        /**
         * view事件类型不需要key值
         */
        CommonButton btn11 = new CommonButton();
        btn11.setName("智能导诊");
        btn11.setType("view");
        btn11.setUrl("http://zcloudyun.asia:8096/#/guide");

        CommonButton btn12 = new CommonButton();
        btn12.setName("在线挂号");
        btn12.setType("view");
        btn12.setUrl("http://zcloudyun.asia:8096/#/notice");

        CommonButton btn13 = new CommonButton();
        btn13.setName("在线询医");
        btn13.setType("view");
        btn13.setUrl("http://zcloudyun.asia:8096/#/order_list");

        CommonButton btn14 = new CommonButton();
        btn14.setName("健康测评");
        btn14.setType("view");
        btn14.setUrl("http://zcloudyun.asia:8096/#/home");

        CommonButton btn15 = new CommonButton();
        btn15.setName("疾病百科");
        btn15.setType("view");
        btn15.setUrl("http://www.baidu.com");

        CommonButton btn21 = new CommonButton();
        btn21.setName("我的挂号");
        btn21.setType("view");
        btn21.setUrl("http://zcloudyun.asia:8096/#/order");

        CommonButton btn22 = new CommonButton();
        btn22.setName("我的病历");
        btn22.setType("view");
        btn22.setUrl("http://zcloudyun.asia:8096/#/record_list");

        CommonButton btn23 = new CommonButton();
        btn23.setName("我的处方");
        btn23.setType("view");
        btn23.setUrl("http://zcloudyun.asia:8096/#/prescription_list");

        CommonButton btn24 = new CommonButton();
        btn24.setName("检查报告");
        btn24.setType("view");
        btn24.setUrl("http://zcloudyun.asia:8096/#/inspect_list");

        CommonButton btn25 = new CommonButton();
        btn25.setName("评价列表");
        btn25.setType("view");
        btn25.setUrl("http://zcloudyun.asia:8096/#/evaluate_list");

        CommonButton btn31 = new CommonButton();
        btn31.setName("个人主页");
        btn31.setType("view");
        btn31.setUrl("http://zcloudyun.asia:8096/#/");

        CommonButton btn32 = new CommonButton();
        btn32.setName("实名绑卡");
        btn32.setType("view");
        btn32.setUrl("http://zcloudyun.asia:8096/#/user_info");

        ComplexButton mainBtn1 = new ComplexButton();
        mainBtn1.setName("智慧医疗");
        mainBtn1.setSub_button(new CommonButton[] { btn11, btn12, btn13, btn14, btn15 });

        ComplexButton mainBtn2 = new ComplexButton();
        mainBtn2.setName("查询业务");
        mainBtn2.setSub_button(new CommonButton[] { btn21,btn22,btn23,btn24,btn25});

        ComplexButton mainBtn3 = new ComplexButton();
        mainBtn3.setName("个人中心");
        mainBtn3.setSub_button(new CommonButton[] { btn31,btn32 });

        /**
         * 这是公众号目前的菜单结构，每个一级菜单都有二级菜单项<br>
         *
         * 在某个一级菜单下没有二级菜单的情况，menu该如何定义呢？<br>
         * 比如，第三个一级菜单项不是"更多体验"，而直接是"幽默笑话"，那么menu应该这样定义：<br>
         * menu.setButton(new Button[] { mainBtn1, mainBtn2, btn33 });
         */
        Menu menu = new Menu();
        menu.setButton(new Button[] { mainBtn1, mainBtn2, mainBtn3 });

        return menu;
    }
    private static Menu getFirstMenu1() {
        /**
         * view事件类型不需要key值
         */
        CommonButton btn11 = new CommonButton();
        btn11.setName("智能导诊");
        btn11.setType("view");
//        btn11.setKey("11");
        btn11.setUrl("http://www.baidu.com");

        CommonButton btn12 = new CommonButton();
        btn12.setName("在线挂号");
        btn12.setType("view");
//        btn12.setKey("12");
        btn12.setUrl("http://www.baidu.com");

        CommonButton btn13 = new CommonButton();
        btn13.setName("在线缴费");
        btn13.setType("view");
//        btn13.setKey("13");
        btn13.setUrl("http://www.baidu.com");

        CommonButton btn14 = new CommonButton();
        btn14.setName("在线就诊预约");
        btn14.setType("view");
//        btn14.setKey("14");
        btn14.setUrl("http://www.baidu.com");

        CommonButton btn15 = new CommonButton();
        btn15.setName("疾病百科");
        btn15.setType("view");
//        btn15.setKey("15");
        btn15.setUrl("http://www.baidu.com");

        CommonButton btn21 = new CommonButton();
        btn21.setName("预约记录");
        btn21.setType("view");
//        btn21.setKey("21");
        btn21.setUrl("http://www.baidu.com");

        CommonButton btn22= new CommonButton();
        btn22.setName("检查报告");
        btn22.setType("view");
//        btn22.setKey("22");
        btn22.setUrl("http://www.baidu.com");

        CommonButton btn23 = new CommonButton();
        btn23.setName("订单查询");
        btn23.setType("view");
//        btn23.setKey("23");
        btn21.setUrl("http://www.baidu.com");

        CommonButton btn24 = new CommonButton();
        btn24.setName("电子处方");
        btn24.setType("view");
//        btn21.setKey("24");
        btn21.setUrl("http://www.baidu.com");

        CommonButton btn25 = new CommonButton();
        btn25.setName("问诊记录");
        btn25.setType("view");
//        btn25.setKey("25");
        btn25.setUrl("http://www.baidu.com");

        CommonButton btn31 = new CommonButton();
        btn31.setName("个人主页");
        btn31.setType("view");
//        btn31.setKey("31");
        btn31.setUrl("http://www.baidu.com");

        CommonButton btn32 = new CommonButton();
        btn32.setName("实名绑卡");
        btn32.setType("view");
//        btn32.setKey("32");
        btn32.setUrl("http://www.baidu.com");

        CommonButton btn33= new CommonButton();
        btn33.setName("电子票据");
        btn33.setType("view");
//        btn33.setKey("33");
        btn33.setUrl("http://www.baidu.com");

        ComplexButton mainBtn1 = new ComplexButton();
        mainBtn1.setName("医疗业务");
        mainBtn1.setSub_button(new CommonButton[] { btn11, btn12, btn13, btn14,btn15});

        ComplexButton mainBtn2 = new ComplexButton();
        mainBtn2.setName("查询业务");
        mainBtn2.setSub_button(new CommonButton[] { btn21,btn22,btn23,btn24,btn25});

        ComplexButton mainBtn3 = new ComplexButton();
        mainBtn3.setName("个人中心");
        mainBtn3.setSub_button(new CommonButton[] { btn31,btn32 ,btn33});

        /**
         * 这是公众号目前的菜单结构，每个一级菜单都有二级菜单项<br>
         *
         * 在某个一级菜单下没有二级菜单的情况，menu该如何定义呢？<br>
         * 比如，第三个一级菜单项不是"更多体验"，而直接是"幽默笑话"，那么menu应该这样定义：<br>
         * menu.setButton(new Button[] { mainBtn1, mainBtn2, btn33 });
         */
        Menu menu = new Menu();
        menu.setButton(new Button[] { mainBtn1, mainBtn2, mainBtn3 });
        return menu;
    }
}

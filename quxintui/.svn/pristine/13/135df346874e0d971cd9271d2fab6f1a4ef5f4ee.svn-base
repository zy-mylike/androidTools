package com.enetic.push;

import com.enetic.push.bean.UserBean;

/**
 * Created by json on 2016/5/31.
 */
public class Constants {


    /**
     * 微信配置信息
     */
    public static final class WXConstant {
        public static final String WXAPP_ID = "wxdec35acd910ded10";
        public static final String WXAPP_SELECT = "6fe5e4acf57330080c6377a3b7115bd7";
        public static final String WXPartnerId = "1384598002";//正式
        public static final String WXAPIKey = "587b8189a75a475a8b6f9f0cc0c7f034";//正式
    }

    public static UserBean CURRENT_USER;
    public static final String APP_KRY = "wxdec35acd910ded10";

    public static final String HOST = "http://hivedrp.com/api/v1/";
//    public static final String HOST = "http://192.168.1.138:8080/hivedrp-api/api/v1/";


    public static final String URL_LOGOUT = HOST + "user/logout?token=";
    public static final String URL_LOGIN_WX = HOST + "user/weixin/login";
    public static final String USER_TOKEN = HOST + "user/token/";
    public static final String URL_USERINFO = HOST + "user/profile?userId=";

    //商家 查看商品列表
    public static final String URL_SHOP_LIST = HOST + "business/listProduct";
    //商家 下发任务
    public static final String URL_ADDTASK = HOST + "business/addTask?businessId=";
    //商家 账户
    public static final String URL_BUSINESS_ACCONT = HOST + "business/account?businessId=";
    //编辑商品品
    public static final String URL_PRODUCT_EDIT = HOST + "product/editProduct";
    //查看自己的代理
    public static final String URL_AGENT_LIST = HOST + "business/listAgent?userId=";
    //查看新的 代理
    public static final String URL_NEWAGENT_LIST = HOST + "business/listNewAgent?userId=";
    //商家添加代理商
    public static final String URL_ADD_AGENT = HOST + "business/addAgent?agentId=";
    //删除代理
    public static final String URL_DELETE_AGENT = HOST + "business/deleteAgent?agentId=";

    //删除代理
    public static final String URL_BLACK_AGENT = HOST + "business/blacklist?agentId=";


    //查看所有 代理
    public static final String URL_ALLAGENT_LIST = HOST + "business/listAllAgent";
    //商家 　查看所有的发件(任务)
    public static final String URL_TASK_LIST = HOST + "business/listTask?userId=";
    //商家 　删除任务（发件箱）
    public static final String URL_TASK_DELETE = HOST + "business/deleteTask?businessId=";
    //商家 增加发件箱
    public static final String URL_TASK_ADD = HOST + "product/addTask?productId=";
    //获取商品详情
    public static final String URL_DETAIL_PRODUCT = HOST + "product/get?id=";
    //商品图片
    public static final String URL_DETAIL_IMG_PRODUCT = HOST + "product/image?productId=";
    //删除商品
    public static final String URL_PRODUCT_DELETE = HOST + "product/deleteProduct?id=";
    //标记 新的商家未0
    public final static String URL_BUSINESS_READ = HOST + "business/readNewsAgent?businessId=";
    //记录商家分享产品 +1
    public final static String URL_BUSINESS_SHARE_ADD = HOST + "business/addShare?businessId=";

    // 搜索
    public final static String URL_SEARCH = HOST + "search?keyword=";

    /*****************************/

    //代理 任务列表
    public final static String URL_AGENT_TASK_LIST = HOST + "agent/listTask?userId=";
    // 推广记录
    public final static String URL_AGENT_SHARE_LIST = HOST + "agent/listShare?userId=";

    //代理 推广记录 删除（多条：包含单条）
    public static final String URL_SHARE_RECOR_DELETE_AGENT = HOST + "agent/deleteAgentShareList?";

    //    http://123.56.105.87:10040/api/v1/agent/listShare
    //删除任务
    public final static String URL_AGENT_TASK_DELETE = HOST + "agent/deleteTask?agentId=";
    //查看自己的商家
    public final static String URL_AGENT_BUSINESS_LIST = HOST + "agent/listBusiness?userId=";
    //查看新的商家
    public final static String URL_AGENT_BUSINESS_NEW = HOST + "agent/listNewBusiness?userId=";
    //查看所有的的商家
    public final static String URL_AGENT_BUSINESS_ALL = HOST + "business/listAllAgent";

    //添加 商家
    public static final String URL_AGENT_BUSINESS_ADD = HOST + "agent/addBusiness?agentId=";
    //删除 商家
    public static final String URL_AGENT_BUSINESS_DELETE = HOST + "agent/deleteBusiness?agentId=";
    //删除 商家http://123.56.105.87:10040/api/v1/agent/blacklist
    public static final String URL_AGENT_BUSINESS_BLACK = HOST + "agent/blacklist?agentId=";

    //标记 新的商家为0
    public final static String URL_AGENT_BUSINESS_READ = HOST + "agent/readNewsBusiness?agentId=";

    // 我的账户
    public final static String URL_AGENT_ACCOUNT = HOST + "agent/account?agentId=";
    // 查看商品列表
    public final static String URL_AGENT_PRODUCT_LIST = HOST + "agent/listProduct?userId=";

    //  新商家数量
    public final static String URL_AGENT_BUSINESS_COUNT = HOST + "agent/listNewBusinessCount?userId=";
    public final static String URL_AGENT_ADDSHARE = HOST + "agent/addShare?agentId=";


    public static String getProductShareUrl(String agentId, String businessId, String productId) {
        String URL = "http://hivedrp.com%2F%23%2Ftab%2Fdetails%2F" + productId + "_" + businessId + "_" + agentId;
        final String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + Constants.WXConstant.WXAPP_ID + "&redirect_uri=" + URL + "&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect";
        return url;
    }

    //意见反馈
    public static final String URL_FEEDBACK = HOST + "app/feedback";
    //检查更新
    public static String APP_UPGRADE_URL = HOST + "app/upgrade";
    //修改用户头像
    public static String APP_HEADER_URL = HOST + "user/uploadPortrait/";
    //修改用户头像
    public static String APP_USERINFO_URL = HOST + "user/editProfile";
    //优惠卷列表 http://hivedrp.com/api/v1/coupon?userId=170&page=1&size=20
    public static String APP_COUPON_URL = HOST + "coupon?userId=";
    // 我的交易 shopOrder/product?userId
    public static String APP_shopOrder_URL = HOST + "shopOrder/product?userId=";
    // 交易记录 shopOrder/order?userId
    public static String APP_shopOrders_URL = HOST + "shopOrder?userId=";
    //发货
    public static String APP_Send_URL = HOST + "shopOrder/order?orderId=";
    // 保存充值记录
    public static String SAVE_RECHARGE_URL = HOST + "userPaycheck/save";
    // 获取订单号（充值）
    public static String GET_ORDERUNMBRT_URL = HOST + "userPaycheck/getOrderNumber?userId=";


}

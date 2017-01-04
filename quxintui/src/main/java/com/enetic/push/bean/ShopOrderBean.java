package com.enetic.push.bean;

/**
 * Created by json on 2016/10/08.
 */

public class ShopOrderBean {

    /**
     * id : 14
     * orderSn : ORDER187837959651328
     * orderStatus : 1
     * openid : 663
     * businessId : 63
     * productId : 641
     * agentId : 0
     * amount : 0.0
     * couponCode : null
     * addressId : null
     * address : 光华路汉威大厦
     * mobile : 18610188899
     * consignee : PenCho
     * memo : 我要最大号的！
     * paymentStatus : 0
     * shippingMethodName : 快递
     * shippingStatus : 0
     * reviewStatus : 0
     * freight : 1000.0
     * expressSn : 0
     * createTime : 1463042309000
     * payTime : 1463042309000
     * icon : http://hivedrp.com/static//images/20160923/236/1474607749436879527.png
     */

    public int id;
    public String orderSn;
    public int orderStatus;
    public String openid;
    public int businessId;
    public int productId;
    public int agentId;
    public double amount;
    public Object couponCode;
    public Object addressId;
    public String address;
    public String mobile;
    public String consignee;
    public String memo;
    public int paymentStatus;
    public String shippingMethodName;
    public int shippingStatus;
    public int reviewStatus;
    public double freight;
    public String expressSn;
    public long createTime;
    public long payTime;
    public String icon;
    public int goodsCount;
}

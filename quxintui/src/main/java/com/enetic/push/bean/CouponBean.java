package com.enetic.push.bean;

/**
 * Created by json on 2016/10/08.
 */

public class CouponBean {

    /**
     * id : 1
     * openId : null
     * businessId : 1
     * productId : null
     * amount : 10.0
     * enough : 80
     * limitPhone : 8765
     * type : 1
     * status : 1
     * createTime : 1461745104000
     * startTime : 1461745104000
     * endTime : 1461745350000
     */

    public int id;
    public Object openId;
    public int businessId;
    public Object productId;
    public double amount;
    public String enough;
    public String limitPhone;
    public int type;
    public int status; //0不可以使用  1可以用 ,未用    2 可以用，已使用  ',
    public long createTime;
    public long startTime;
    public long endTime;
}

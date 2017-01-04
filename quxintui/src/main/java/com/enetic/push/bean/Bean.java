package com.enetic.push.bean;

import java.util.List;

/**
 * Created by json on 2016/10/08.
 */

public class Bean {


    /**
     * date : 2016-10
     * paychecks : [{"id":25,"orderSn":"31807015","orderStatus":0,"amount":null,"memo":null,"createTime":1475906632000,"createMonth":"2016-10","payType":null},{"id":26,"orderSn":"77650783","orderStatus":0,"amount":null,"memo":null,"createTime":1475907966000,"createMonth":"2016-10","payType":null}]
     */

    public String date;
    /**
     * id : 25
     * orderSn : 31807015
     * orderStatus : 0
     * amount : null
     * memo : null
     * createTime : 1475906632000
     * createMonth : 2016-10
     * payType : null
     */

    public List<PaychecksBean> paychecks;

    public static class PaychecksBean {
        public int id;
        public String orderSn;
        public int orderStatus;
        public Double amount;
        public Object memo;
        public long createTime;
        public String createMonth;
        public String payType;
    }
}

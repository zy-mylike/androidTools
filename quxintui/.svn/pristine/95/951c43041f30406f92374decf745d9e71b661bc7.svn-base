package com.enetic.push.bean;

import android.text.TextUtils;

import com.enetic.push.utils.AppTools;

import java.io.Serializable;

/**
 * Created by json on 2016/6/16.
 */
public class ProductBean implements Serializable {

//    name	名称
//    price	价格
//    spec	规格
//    icon	图片
//    repertory	库存
//    createTime	创建时间
//    desp	简介
//    replyCount	评论数
//    shareCount	分享数
//    sellCount	销售数
//    shareReward	分享奖励
//    orderReward	订单奖励
//    shelves	上下架	上下架  1上架   2下架
//    businessId	商家用户id

    private String id; //商品id
    private String name;
    private String price;
    private String spec; //规格
    private String icon;
    private String repertory; //库存
    private String createTime;
    private String desp; //简介
    private String replyCount; //评论数
    private String sellCount; //销售数
    private String shareCount;
    private String shareReward;
    private String orderReward; //订单奖励
    private String totalReward; //全部奖励？
    private String shelves; //上下架	上下架  1上架   2下架
    private String businessId;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        if (TextUtils.isEmpty(price) || price.equals("null")) {
            return "0.00";
        }
        return AppTools.moneyFormat(Double.valueOf(price));
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getRepertory() {
        if (TextUtils.isEmpty(repertory) || repertory.equals("null")) {
            return "0";
        }
        return repertory;
    }

    public void setRepertory(String repertory) {
        this.repertory = repertory;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getDesp() {
        return desp;
    }

    public void setDesp(String desp) {
        this.desp = desp;
    }

    public String getReplyCount() {
        if (TextUtils.isEmpty(replyCount) || replyCount.equals("null")) {
            return "0";
        }
        return replyCount;
    }

    public void setReplyCount(String replyCount) {
        this.replyCount = replyCount;
    }

    public String getSellCount() {
        if (TextUtils.isEmpty(sellCount) || sellCount.equals("null")) {
            return "0";
        }
        return sellCount;
    }

    public void setSellCount(String sellCount) {
        this.sellCount = sellCount;
    }

    public String getShareCount() {
        return shareCount;
    }

    public void setShareCount(String shareCount) {
        this.shareCount = shareCount;
    }

    public String getShareReward() {
        return shareReward;
    }

    public void setShareReward(String shareReward) {
        this.shareReward = shareReward;
    }

    public String getOrderReward() {
        return orderReward;
    }

    public void setOrderReward(String orderReward) {
        this.orderReward = orderReward;
    }

    public String getTotalReward() {
        return totalReward;
    }

    public void setTotalReward(String totalReward) {
        this.totalReward = totalReward;
    }

    public String getShelves() {
        return shelves;
    }

    public void setShelves(String shelves) {
        this.shelves = shelves;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }
}

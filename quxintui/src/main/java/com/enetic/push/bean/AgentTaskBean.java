package com.enetic.push.bean;

import android.text.TextUtils;

import com.enetic.push.utils.AppTools;

import java.io.Serializable;

/**
 * Created by json on 2016/6/20.
 */
public class AgentTaskBean implements Serializable {
    private int id = -1; //商品id
    private int shelves = -2; //上下架	上下架  1上架   2下架
    private int businessId = -2;
    private int repertory = 0; //库存
    private String name = "";
    private String spec = ""; //规格
    private String desp = ""; //简介
    private int replyCount = 0; //评论数
    private int sellCount = 0; //销售数

    private int shareCount = 0;
    private String icon = "";
    private String price = "";
    private float shareReward = 0.00f;
    private float orderReward = 0.00f; //订单奖励
    private float totalReward = 0.00f; //全部奖励？
    private long createTime = 0l;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getShelves() {
        return shelves;
    }

    public void setShelves(int shelves) {
        this.shelves = shelves;
    }

    public int getBusinessId() {
        return businessId;
    }

    public void setBusinessId(int businessId) {
        this.businessId = businessId;
    }

    public int getRepertory() {
        return repertory;
    }

    public void setRepertory(int repertory) {
        this.repertory = repertory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public String getDesp() {
        return desp;
    }

    public void setDesp(String desp) {
        this.desp = desp;
    }

    public int getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(int replyCount) {
        this.replyCount = replyCount;
    }

    public int getSellCount() {
        return sellCount;
    }

    public void setSellCount(int sellCount) {
        this.sellCount = sellCount;
    }

    public int getShareCount() {
        return shareCount;
    }

    public void setShareCount(int shareCount) {
        this.shareCount = shareCount;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
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

    public float getShareReward() {
        return shareReward;
    }

    public void setShareReward(float shareReward) {
        this.shareReward = shareReward;
    }

    public float getOrderReward() {
        return orderReward;
    }

    public void setOrderReward(float orderReward) {
        this.orderReward = orderReward;
    }

    public float getTotalReward() {
        return totalReward;
    }

    public void setTotalReward(float totalReward) {
        this.totalReward = totalReward;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }
}

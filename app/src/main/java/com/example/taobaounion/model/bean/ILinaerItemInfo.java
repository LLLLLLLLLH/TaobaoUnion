package com.example.taobaounion.model.bean;

public interface ILinaerItemInfo extends IBaseInfo{

    //获取原价
    String getFinalPrice();

    //获取优惠价
    String getCouponAmount();

    //获取销量

    long getVolume();

    //获取卖家类型
    int getType();

    boolean getHasCoupon();
}

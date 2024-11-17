package com.example.hxds.rule.bean;

import lombok.Data;

@Data
public class Voucher {
    private String name;
    private String remark = "订单取消系统补偿的代金券";
    private String tag = "补偿券";
    private Integer totalQuota = 1;
    private Integer takeCount = 1;
    private Integer usedCount = 0;
    private String discount;
    private String withAmount = "0";
    private Integer type = 1;
    private Integer limitQuota = 1;
    private Integer status = 1;
    private Integer timeType;
    private Integer days;
    private String startTime;
    private String endTime;

    public Voucher(String name, String discount) {
        this.name = name;
        this.discount = discount;
    }

}

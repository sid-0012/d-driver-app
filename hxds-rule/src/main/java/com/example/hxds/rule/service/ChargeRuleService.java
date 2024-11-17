package com.example.hxds.rule.service;

import com.example.hxds.rule.db.pojo.ChargeRuleEntity;

import java.util.HashMap;

public interface ChargeRuleService {
    public HashMap calculateOrderCharge(String mileage, String time, int minute,String key);

    public int insert(ChargeRuleEntity entity);

    public HashMap searchChargeRuleById(long ruleId);
}

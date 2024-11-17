package com.example.hxds.rule.service;

import java.util.HashMap;

public interface CancelRuleService {
    public HashMap calculateDriverCancelOrder(String status, int cancelNum, int acceptMinute, int waitingMinute,String key);

    public HashMap searchCancelRuleById(long ruleId);
}

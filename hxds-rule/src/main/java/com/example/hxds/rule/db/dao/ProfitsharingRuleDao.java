package com.example.hxds.rule.db.dao;


import com.example.hxds.rule.db.pojo.ProfitsharingRuleEntity;

import java.util.HashMap;

public interface ProfitsharingRuleDao {
    public ProfitsharingRuleEntity searchCurrentRule(String key);

    public HashMap searchProfitsharingRuleById(long ruleId);
}




